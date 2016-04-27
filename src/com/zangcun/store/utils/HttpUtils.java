package com.zangcun.store.utils;

import com.zangcun.store.other.MyApplication;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;


public class HttpUtils {

    public static  Callback.Cancelable HttpGetMethod(Callback.CommonCallback<String> requestCallBack,RequestParams params){

        Callback.Cancelable cancelable = x.http().get(params, requestCallBack);

        return  cancelable;
    }

    public static Callback.Cancelable HttpPostMethod(Callback.CommonCallback<String> requestCallBack,RequestParams params){

        Callback.Cancelable cancelable = x.http().post(params, requestCallBack);

        return  cancelable;
    }
    public static Callback.Cancelable HttpDeleteMethod(Callback.CommonCallback<String> requestCallBack,RequestParams params){

        Callback.Cancelable cancelable = x.http().request(HttpMethod.DELETE,params,requestCallBack);

        return  cancelable;
    }
    public static Callback.Cancelable HttpPutMethod(Callback.CommonCallback<String> requestCallBack,RequestParams params){

        Callback.Cancelable cancelable = x.http().request(HttpMethod.PUT,params,requestCallBack);

        return  cancelable;
    }
    public static boolean isHaveNetwork(){
        boolean available = NetworkUtil.isInternetAvailable(MyApplication.instance);
        if (!available){
            ToastUtils.show(MyApplication.instance,"网络异常，请稍后再试");
        }
        return available;

    }
}
