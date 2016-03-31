package com.zangcun.store.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedUtils {


    /**
     * java解析xml有两种方式：
     * dom
     * sax
     * <p>
     * dom是一次性把所有xml加载进内存然后按树形结构解析
     * sax是基于响应式的解析，一次只加载一部分xml
     */

    public static final String SHARE_NAME = "share";

    public static SharedPreferences getPreference(Context ctx) {
        return ctx.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
    }

    public static int getInt(Context ctx, String key, int defValue) {
        return getPreference(ctx).getInt(key, defValue);
    }

    public static void saveInt(Context ctx, String key, int value) {
        SharedPreferences.Editor editor = getPreference(ctx).edit();
        editor.putInt(key, value);
        editor.commit();//commit 兼容低版本但在主线程运行  apply高版本在子线程运行
    }

    public static boolean getBoolean(Context ctx, String key, boolean defValue) {
        return getPreference(ctx).getBoolean(key, defValue);
    }

    public static void saveBoolean(Context ctx, String key, boolean value) {
        SharedPreferences.Editor editor = getPreference(ctx).edit();
        editor.putBoolean(key, value);
        editor.commit();//commit 兼容低版本但在主线程运行  apply高版本在子线程运行
    }
}
