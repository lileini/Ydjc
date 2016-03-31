package com.zangcun.store.utils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2015/12/9.
 */
public class HttpUtils {

    public static  Callback.Cancelable HttpGetMethod(Callback.CommonCallback<String> requestCallBack,RequestParams params){

        Callback.Cancelable cancelable = x.http().get(params, requestCallBack);

        return  cancelable;
    }

    public static Callback.Cancelable HttpPostMethod(Callback.CommonCallback<String> requestCallBack,RequestParams params){

        Callback.Cancelable cancelable = x.http().post(params, requestCallBack);

        return  cancelable;
    }
}
