package com.zangcun.store.net;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zangcun.store.other.Const;
import com.zangcun.store.utils.DictionaryTool;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//请求方式
public class CommandBase {

    private static final String TAG = "CommandBase";

    /**
     * POST请求带参数（map）
     *
     * @param context
     * @param URL
     * @param handler
     * @param map
     */

    public static void requestDataMap(Context context, String URL, final Handler handler, Map<String, String> map) {
        final Message message = new Message();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        message.what = Const.SUCCESS;
                        message.obj = response;
                        handler.sendMessage(message);
                        Log.i(TAG, "response = " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                message.what = Const.ERROR;
                message.obj = error;
                handler.sendMessage(message);
                Log.i(TAG, error.toString());

            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //to-do
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    /**
     * PUT请求带参数
     *
     * @param context
     * @param URL
     * @param handler
     * @param map
     */

    public static void requestDataMapPut(Context context, String URL, final Handler handler, Map<String, String> map) {
        final Message message = new Message();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        message.what = Const.SUCCESS;
                        message.obj = response;
                        handler.sendMessage(message);
                        Log.i(TAG, "response = " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                message.what = Const.ERROR;
                message.obj = error;
                handler.sendMessage(message);
                Log.i(TAG, "error.toString() = " + error.toString());
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //to-do
                return map;
            }

        };
        requestQueue.add(stringRequest);
    }

    /**
     * POST JSON
     *
     * @param context
     * @param URL
     * @param handler
     * @param map
     */
    public static void requestDataMapJson(Context context, String URL, final Handler handler, Map<String, String> map) {
        final Message message = new Message();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Log.e("请求的JSON数据", new JSONObject(map).toString());
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(map), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.v("正确：", jsonObject.toString());
                message.what = Const.SUCCESS;
                message.obj = jsonObject;
                handler.sendMessage(message);
                android.util.Log.i("服务器返回数据", jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("错误：", volleyError.toString());
                message.what = Const.ERROR;
                message.obj = volleyError;
                handler.sendMessage(message);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "*/*");
                headers.put("Content-Type", "application/json");
                headers.put("AUTHORIZATION", DictionaryTool.get(context, "strToken", "").toString());
                Log.e("请求的header", headers.toString());
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }

    /**
     * 不带参数的POST 修改了heard
     *
     * @param context
     * @param URL
     * @param handler
     * @param map
     */
    public static void requestDataNoMap(Context context, String URL, final Handler handler, Map<String, String> map) {
        final Message message = new Message();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        message.what = Const.SUCCESS;
                        message.obj = response;
                        handler.sendMessage(message);
                        android.util.Log.i("服务器返回数据", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                message.what = Const.ERROR;
                message.obj = error;
                handler.sendMessage(message);
                android.util.Log.e("错误信息", error.toString());
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("AUTHORIZATION", DictionaryTool.get(context, "strToken", "").toString());
                Log.e("请求的header", headers.toString());
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }

    /**
     * 不带参数的GET
     *
     * @param context
     * @param URL
     * @param handler
     * @param map
     */
    public static void requestDataNoGet(Context context, String URL, final Handler handler, Map<String, String> map) {
        final Message message = new Message();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        message.what = Const.SUCCESS;
                        message.obj = response;
                        handler.sendMessage(message);
                        android.util.Log.i("服务器返回数据", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                message.what = Const.ERROR;
                message.obj = error;
                handler.sendMessage(message);
                android.util.Log.e("错误信息", error.toString());
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("AUTHORIZATION", DictionaryTool.get(context, "strToken", "").toString());
                Log.e("请求的header", headers.toString());
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }

    /**
     * POST请求带参数（map）,heared 为token
     *
     * @param context
     * @param URL
     * @param handler
     * @param map
     */

    public static void requestDataMapToken(Context context, String URL, final Handler handler, Map<String, String> map) {
        final Message message = new Message();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        message.what = Const.SUCCESS;
                        message.obj = response;
                        handler.sendMessage(message);
                        Log.i(TAG, "response = " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                message.what = Const.ERROR;
                message.obj = error;
                handler.sendMessage(message);
                Log.i(TAG, error.toString());

            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //to-do
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", DictionaryTool.getToken(context));
                Log.e("token", map.toString());
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }

    /**
     * DELETE请求带参数（map）,heared 为token
     *
     * @param context
     * @param URL
     * @param handler
     * @param map
     */

    public static void requestDataMapDel(Context context, String URL, final Handler handler, Map<String, String> map) {
        final Message message = new Message();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        message.what = Const.SUCCESS;
                        message.obj = response;
                        handler.sendMessage(message);
                        Log.i(TAG, "response = " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                message.what = Const.ERROR;
                message.obj = error;
                handler.sendMessage(message);
                Log.i(TAG, error.toString());
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //to-do
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", DictionaryTool.getToken(context));
                Log.e("token", map.toString());
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }

    /**
     * PUT请求带参数（map）,heared 为token
     *
     * @param context
     * @param URL
     * @param handler
     * @param map
     */

    public static void requestDataMapPass(Context context, String URL, final Handler handler, Map<String, String> map) {
        final Message message = new Message();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        message.what = Const.SUCCESS;
                        message.obj = response;
                        handler.sendMessage(message);
                        Log.i(TAG, "response = " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                message.what = Const.ERROR;
                message.obj = error;
                handler.sendMessage(message);
                Log.i(TAG, error.toString());

            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //to-do
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", DictionaryTool.getToken(context));
                Log.e("token", map.toString());
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }

}
