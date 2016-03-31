package com.zangcun.store.fragment;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.android.volley.VolleyError;
import com.zangcun.store.JavaScriptinterface;
import com.zangcun.store.R;
import com.zangcun.store.net.Http;
import com.zangcun.store.utils.ToastUtils;

//专题
public class SpecialFragment extends BaseFragment implements Http.INetWork {
    private WebView mWebView;
    protected FragmentActivity mThis;

    public static SpecialFragment getInstance() {
        SpecialFragment fragment = new SpecialFragment();

        return fragment;
    }

    @Override
    protected int contentViewId() {
        return R.layout.fragment_special;
    }


    @Override
    protected void onFirstPreLoading() {
        mWebView = (WebView) mView.findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JavaScriptinterface(mThis), "android");
        mWebView.getSettings().setAllowFileAccess(true);

        mWebView.loadUrl("http://211.149.231.116:3000/topic_web/topic_1/index-android.html");
        mWebView.requestFocus();
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });
    }

    public void scroll(float f) {
        Log.i("tag", "这是与js交互数据" + f);
    }

    public void product(String productId) {
        Log.i("tag", "这是与js交互数据" + productId);
    }

    public boolean onKeyDown(int keyCoder, KeyEvent event) {
        if (mWebView.canGoBack() && keyCoder == KeyEvent.KEYCODE_BACK) {
            mWebView.goBack();   //goBack()表示返回webView的上一页面

            return true;
        }
        return false;
    }

    @Override
    protected void onFirstLoadingData() {

    }

    @Override
    protected void onFirstLoadingFinish() {

    }

    @Override
    public void onNetSuccess(String response, int requestCode) {
    }

    @Override
    public void onNetError(VolleyError error, int requestCode) {
        if (mThis != null) {
            ToastUtils.show(mThis, "网络连接失败");
        }

    }
}
