package com.zangcun.store.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

//对联网的简单封装
public class Http {
    private RequestQueue mQueue;//队列
    private String TAG = "Http~~~";

    public Http(Context ctx) {
       mQueue = Volley.newRequestQueue(ctx);//队列方法
    }

    //通过get联网
    public void get(String url, final INetWork PiNetWork, final int page, final int requestCode) {

//    	url = url+"?"+"page="+page+"&cate_ids="+1+"&size="+3+"&material="+"铜"+"&sort="+2;
    	url = url+"?"+"page="+page;
    	Log.i(TAG,"Url = "+url);
    	
        //联网成功
        StringRequest request = new StringRequest
                (url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //接口为空，不用通知
                        if (PiNetWork == null) return;

                        //联网成功，服务器返回了一个错误的信息
                        if (TextUtils.isEmpty(response)) {
                            PiNetWork.onNetError(new VolleyError("服务器连接失败",
                                    new Throwable("服务器连接失败")), requestCode);
                            return;
                        }
                        Log.d("debug","获取数据");
                        PiNetWork.onNetSuccess(response, requestCode);//把联网成功的数据传出去
                    }
                    //联网错误的请求吗
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (PiNetWork == null) return;
                        PiNetWork.onNetError(error, requestCode);

                    }
                }){
        	
        	//post才会从这里 传参。。。
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
            	//to-do
            	HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("cate_ids", "1");
                hashMap.put("size", "3");
                hashMap.put("material", "铜");
                Log.d("debug", "page:+"+page);
                hashMap.put("page", String.valueOf(page));
                hashMap.put("sort", "2");
                return hashMap;
            }
        };
        mQueue.add(request);
    }

    //通过get联网
    public void get(String url, final INetWork PiNetWork, final int requestCode) {
        //联网成功
        StringRequest request = new StringRequest
                (url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //接口为空，不用通知
                        if (PiNetWork == null) return;

                        //联网成功，服务器返回了一个错误的信息
                        if (TextUtils.isEmpty(response)) {
                            PiNetWork.onNetError(new VolleyError("服务器连接失败",
                                    new Throwable("服务器连接失败")), requestCode);
                            return;
                        }
                        Log.d("debug","获取数据");
                        PiNetWork.onNetSuccess(response, requestCode);//把联网成功的数据传出去
                    }
                    //联网错误的请求吗
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (PiNetWork == null) return;
                        PiNetWork.onNetError(error, requestCode);

                    }
                });
        mQueue.add(request);
    }
    
   
    
    
    public interface INetWork {//接口通知外面联网是否成功

        void onNetSuccess(String response, int requestCode);//联网成功     请求码

        void onNetError(VolleyError error, int requestCode);//联网失败的方法      请求码
    }
}