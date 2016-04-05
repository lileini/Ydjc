package com.zangcun.store.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class GsonUtil {
    public static <T> T getResult(String json,Class<T> classOfT){
        Gson gson = new Gson();
        return gson.fromJson(json,classOfT);
    }
    /*public static <T> ArrayList<T> getResult2(String json,Class<T> classOfT){
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<ArrayList<T>>(){}.getType());

    }*/
    public static String toJson(Object o){
        Gson gson = new Gson();
        return gson.toJson(o);
    }

}
