package com.zangcun.store.utils;

import com.google.gson.Gson;

public class GsonUtil {
    public static <T> T getResult(String json,Class<T> classOfT){
        Gson gson = new Gson();
        return gson.fromJson(json,classOfT);
    }
    public static String toJson(Object o){
        Gson gson = new Gson();
        return gson.toJson(o);
    }
}
