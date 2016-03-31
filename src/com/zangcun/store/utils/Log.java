package com.zangcun.store.utils;

//Log打印
public class Log {
    public static final String TAG = "HIDTAG";

    public static void i(String tag,String text) {
        android.util.Log.i(tag, text);
    }
}
