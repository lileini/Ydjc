package com.android.volley;


import android.content.ContentResolver;
import android.content.Context;
import android.net.SSLSessionCache;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URI;
import java.security.KeyStore;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Implementation of the Apache {@link DefaultHttpClient} that is configured with
 * reasonable default settings and registered schemes for Android.
 * Don't create this directly, use the {@link #newInstance} factory method.
 * <p/>
 * <p>This client processes cookies but does not retain them by default.
 * To retain cookies, simply add a cookie store to the HttpContext:</p>
 * <p/>
 * <pre>context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);</pre>
 */
public final class AndroidHttpClient implements HttpClient {


    public final static String CONTENT_ENCODING = "content-encoding";
    public final static String CONTENT_TYPE = "content-type";

    // Gzip of data shorter than this probably won't be worthwhile
    public static long DEFAULT_SYNC_MIN_GZIP_BYTES = 256;

    // Default connection and socket timeout of 60 seconds.  Tweak to taste.
    private static final int SOCKET_OPERATION_TIMEOUT = 60 * 1000;

    private static final String TAG = "AndroidHttpClient";

    private static String[] textContentTypes = new String[]{
            "text/",
            "application/xml",
            "application/json"
    };

    /**
     * Interceptor throws an exception if the executing thread is blocked
     */
    private static final HttpRequestInterceptor sThreadCheckInterceptor =
            new HttpRequestInterceptor() {
                public void process(HttpRequest request, HttpContext context) {
                    // Prevent the HttpRequest from being sent on the main thread
                    if (Looper.myLooper() != null && Looper.myLooper() == Looper.getMainLooper()) {
                        throw new RuntimeException("This thread forbids HTTP requests");
                    }
                }
            };

    /**
     * Create a new HttpClient with reasonable defaults (which you can update).
     *
     * @param userAgent to report in your HTTP requests
     * @param context   to use for caching SSL sessions (may be null for no caching)
     * @return AndroidHttpClient for you to use for all your requests.
     */
    public static AndroidHttpClient newInstance(String userAgent, Context context) {
        HttpParams params = new BasicHttpParams();

        // Turn off stale checking.  Our connections break all the time anyway,
        // and it's not worth it to pay the penalty of checking every time.
        HttpConnectionParams.setStaleCheckingEnabled(params, false);

        HttpConnectionParams.setConnectionTimeout(params, SOCKET_OPERATION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(params, SOCKET_OPERATION_TIMEOUT);
        HttpConnectionParams.setSocketBufferSize(params, 8192);

        // Don't handle redirects -- return them to the caller.  Our code
        // often wants to re-POST after a redirect, which we must do ourselves.
        HttpClientParams.setRedirecting(params, false);

        // Use a session cache for SSL sockets
        SSLSessionCache sessionCache = context == null ? null : new SSLSessionCache(context);

        // Set the specified user agent and register standard protocols.
        HttpProtocolParams.setUserAgent(params, userAgent);
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http",
                PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https",getHttpSocketFactory(
                        SOCKET_OPERATION_TIMEOUT, sessionCache), 443));

        ClientConnectionManager manager =
                new ThreadSafeClientConnManager(params, schemeRegistry);

        // We use a factory method to modify superclass initialization
        // parameters without the funny call-a-static-method dance.
        return new AndroidHttpClient(manager, params);
    }

    /**
     * Create a new HttpClient with reasonable defaults (which you can update).
     *
     * @param userAgent to report in your HTTP requests.
     * @return AndroidHttpClient for you to use for all your requests.
     */
    public static AndroidHttpClient newInstance(String userAgent) {
        return newInstance(userAgent, null /* session cache */);
    }

    private final HttpClient delegate;

    private RuntimeException mLeakedException = new IllegalStateException(
            "AndroidHttpClient created and never closed");

    private AndroidHttpClient(ClientConnectionManager ccm, HttpParams params) {
        this.delegate = new DefaultHttpClient(ccm, params) {
            @Override
            protected BasicHttpProcessor createHttpProcessor() {
                // Add interceptor to prevent making requests from main thread.
                BasicHttpProcessor processor = super.createHttpProcessor();
                processor.addRequestInterceptor(sThreadCheckInterceptor);
                processor.addRequestInterceptor(new CurlLogger());

                return processor;
            }

            @Override
            protected HttpContext createHttpContext() {
                // Same as DefaultHttpClient.createHttpContext() minus the
                // cookie store.
                HttpContext context = new BasicHttpContext();
                context.setAttribute(
                        ClientContext.AUTHSCHEME_REGISTRY,
                        getAuthSchemes());
                context.setAttribute(
                        ClientContext.COOKIESPEC_REGISTRY,
                        getCookieSpecs());
                context.setAttribute(
                        ClientContext.CREDS_PROVIDER,
                        getCredentialsProvider());
                return context;
            }
        };
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (mLeakedException != null) {
            Log.e(TAG, "Leak found", mLeakedException);
            mLeakedException = null;
        }
    }

    /**
     * Modifies a request to indicate to the server that we would like a
     * gzipped response.  (Uses the "Accept-Encoding" HTTP header.)
     *
     * @param request the request to modify
     * @see #getUngzippedContent
     */
    public static void modifyRequestToAcceptGzipResponse(HttpRequest request) {
        request.addHeader("Accept-Encoding", "gzip");
    }

    /**
     * Gets the input stream from a response entity.  If the entity is gzipped
     * then this will get a stream over the uncompressed data.
     *
     * @param entity the entity whose content should be read
     * @return the input stream to read from
     * @throws IOException
     */
    public static InputStream getUngzippedContent(HttpEntity entity)
            throws IOException {
        InputStream responseStream = entity.getContent();
        if (responseStream == null) return responseStream;
        Header header = entity.getContentEncoding();
        if (header == null) return responseStream;
        String contentEncoding = header.getValue();
        if (contentEncoding == null) return responseStream;
        if (contentEncoding.contains("gzip")) responseStream
                = new GZIPInputStream(responseStream);
        return responseStream;
    }

    /**
     * Release resources associated with this client.  You must call this,
     * or significant resources (sockets and memory) may be leaked.
     */
    public void close() {
        if (mLeakedException != null) {
            getConnectionManager().shutdown();
            mLeakedException = null;
        }
    }

    public HttpParams getParams() {
        return delegate.getParams();
    }

    public ClientConnectionManager getConnectionManager() {
        return delegate.getConnectionManager();
    }

    public HttpResponse execute(HttpUriRequest request) throws IOException {
        return delegate.execute(request);
    }

    public HttpResponse execute(HttpUriRequest request, HttpContext context)
            throws IOException {
        return delegate.execute(request, context);
    }

    public HttpResponse execute(HttpHost target, HttpRequest request)
            throws IOException {
        return delegate.execute(target, request);
    }

    public HttpResponse execute(HttpHost target, HttpRequest request,
                                HttpContext context) throws IOException {
        return delegate.execute(target, request, context);
    }

    public <T> T execute(HttpUriRequest request,
                         ResponseHandler<? extends T> responseHandler)
            throws IOException, ClientProtocolException {
        return delegate.execute(request, responseHandler);
    }

    public <T> T execute(HttpUriRequest request,
                         ResponseHandler<? extends T> responseHandler, HttpContext context)
            throws IOException, ClientProtocolException {
        return delegate.execute(request, responseHandler, context);
    }

    public <T> T execute(HttpHost target, HttpRequest request,
                         ResponseHandler<? extends T> responseHandler) throws IOException,
            ClientProtocolException {
        return delegate.execute(target, request, responseHandler);
    }

    public <T> T execute(HttpHost target, HttpRequest request,
                         ResponseHandler<? extends T> responseHandler, HttpContext context)
            throws IOException, ClientProtocolException {
        return delegate.execute(target, request, responseHandler, context);
    }

    /**
     * Compress data to send to server.
     * Creates a Http Entity holding the gzipped data.
     * The data will not be compressed if it is too short.
     *
     * @param data The bytes to compress
     * @return Entity holding the data
     */
    public static AbstractHttpEntity getCompressedEntity(byte data[], ContentResolver resolver)
            throws IOException {
        AbstractHttpEntity entity;
        if (data.length < getMinGzipSize(resolver)) {
            entity = new ByteArrayEntity(data);
        } else {
            ByteArrayOutputStream arr = new ByteArrayOutputStream();
            OutputStream zipper = new GZIPOutputStream(arr);
            zipper.write(data);
            zipper.close();
            entity = new ByteArrayEntity(arr.toByteArray());
            entity.setContentEncoding("gzip");
        }
        return entity;
    }

    /**
     * Retrieves the minimum size for compressing data.
     * Shorter data will not be compressed.
     */
    public static long getMinGzipSize(ContentResolver resolver) {
        return DEFAULT_SYNC_MIN_GZIP_BYTES;  // For now, this is just a constant.
    }

    /* cURL logging support. */

    /**
     * Logging tag and level.
     */
    private static class LoggingConfiguration {

        private final String tag;
        private final int level;

        private LoggingConfiguration(String tag, int level) {
            this.tag = tag;
            this.level = level;
        }

        /**
         * Returns true if logging is turned on for this configuration.
         */
        private boolean isLoggable() {
            return Log.isLoggable(tag, level);
        }

        /**
         * Prints a message using this configuration.
         */
        private void println(String message) {
            Log.println(level, tag, message);
        }
    }

    /**
     * cURL logging configuration.
     */
    private volatile LoggingConfiguration curlConfiguration;

    /**
     * Enables cURL request logging for this client.
     *
     * @param name  to log messages with
     * @param level at which to log messages (see {@link Log})
     */
    public void enableCurlLogging(String name, int level) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        if (level < Log.VERBOSE || level > Log.ASSERT) {
            throw new IllegalArgumentException("Level is out of range ["
                    + Log.VERBOSE + ".." + Log.ASSERT + "]");
        }

        curlConfiguration = new LoggingConfiguration(name, level);
    }

    /**
     * Disables cURL logging for this client.
     */
    public void disableCurlLogging() {
        curlConfiguration = null;
    }

    /**
     * Logs cURL commands equivalent to requests.
     */
    private class CurlLogger implements HttpRequestInterceptor {
        public void process(HttpRequest request, HttpContext context)
                throws HttpException, IOException {
            LoggingConfiguration configuration = curlConfiguration;
            if (configuration != null
                    && configuration.isLoggable()
                    && request instanceof HttpUriRequest) {
                // Never print auth token -- we used to check ro.secure=0 to
                // enable that, but can't do that in unbundled code.
                configuration.println(toCurl((HttpUriRequest) request, false));
            }
        }
    }

    /**
     * Generates a cURL command equivalent to the given request.
     */
    private static String toCurl(HttpUriRequest request, boolean logAuthToken) throws IOException {
        StringBuilder builder = new StringBuilder();

        builder.append("curl ");

        // add in the method
        builder.append("-X ");
        builder.append(request.getMethod());
        builder.append(" ");

        for (Header header : request.getAllHeaders()) {
            if (!logAuthToken
                    && (header.getName().equals("Authorization") ||
                    header.getName().equals("Cookie"))) {
                continue;
            }
            builder.append("--header \"");
            builder.append(header.toString().trim());
            builder.append("\" ");
        }

        URI uri = request.getURI();

        // If this is a wrapped request, use the URI from the original
        // request instead. getURI() on the wrapper seems to return a
        // relative URI. We want an absolute URI.
        if (request instanceof RequestWrapper) {
            HttpRequest original = ((RequestWrapper) request).getOriginal();
            if (original instanceof HttpUriRequest) {
                uri = ((HttpUriRequest) original).getURI();
            }
        }

        builder.append("\"");
        builder.append(uri);
        builder.append("\"");

        if (request instanceof HttpEntityEnclosingRequest) {
            HttpEntityEnclosingRequest entityRequest =
                    (HttpEntityEnclosingRequest) request;
            HttpEntity entity = entityRequest.getEntity();
            if (entity != null && entity.isRepeatable()) {
                if (entity.getContentLength() < 1024) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    entity.writeTo(stream);

                    if (isBinaryContent(request)) {
                        String base64 = Base64.encodeToString(stream.toByteArray(), Base64.NO_WRAP);
                        builder.insert(0, "echo '" + base64 + "' | base64 -d > /tmp/$$.bin; ");
                        builder.append(" --data-binary @/tmp/$$.bin");
                    } else {
                        String entityString = stream.toString();
                        builder.append(" --data-ascii \"")
                                .append(entityString)
                                .append("\"");
                    }
                } else {
                    builder.append(" [TOO MUCH DATA TO INCLUDE]");
                }
            }
        }

        return builder.toString();
    }

    private static boolean isBinaryContent(HttpUriRequest request) {
        Header[] headers;
        headers = request.getHeaders(CONTENT_ENCODING);
        if (headers != null) {
            for (Header header : headers) {
                if ("gzip".equalsIgnoreCase(header.getValue())) {
                    return true;
                }
            }
        }

        headers = request.getHeaders(CONTENT_TYPE);
        if (headers != null) {
            for (Header header : headers) {
                for (String contentType : textContentTypes) {
                    if (header.getValue().startsWith(contentType)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Returns the date of the given HTTP date string. This method can identify
     * and parse the date formats emitted by common HTTP servers, such as
     * <a href="http://www.ietf.org/rfc/rfc0822.txt">RFC 822</a>,
     * <a href="http://www.ietf.org/rfc/rfc0850.txt">RFC 850</a>,
     * <a href="http://www.ietf.org/rfc/rfc1036.txt">RFC 1036</a>,
     * <a href="http://www.ietf.org/rfc/rfc1123.txt">RFC 1123</a> and
     * <a href="http://www.opengroup.org/onlinepubs/007908799/xsh/asctime.html">ANSI
     * C's asctime()</a>.
     *
     * @return the number of milliseconds since Jan. 1, 1970, midnight GMT.
     * @throws IllegalArgumentException if {@code dateString} is not a date or
     *                                  of an unsupported format.
     */
    public static long parseDate(String dateString) {
        long result = 0L;
        Class<?> threadClazz = null;
        try {
            threadClazz = Class.forName("com.android.internal.http.HttpDateTime");
            Method method = threadClazz.getMethod("parse", String.class);
            result = (long) method.invoke(null, dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static org.apache.http.conn.ssl.SSLSocketFactory getHttpSocketFactory(
            int handshakeTimeoutMillis, SSLSessionCache cache) {

        org.apache.http.conn.ssl.SSLSocketFactory result = null;
        try {
            Class c = Class.forName("android.net.SSLCertificateSocketFactory");
            Constructor c1 = c.getDeclaredConstructor(new Class[]{int.class, SSLSessionCache.class, boolean.class});
            c1.setAccessible(true);
            result = new org.apache.http.conn.ssl.SSLSocketFactory((KeyStore) c1.newInstance(new Object[]{handshakeTimeoutMillis, cache, true}));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}

