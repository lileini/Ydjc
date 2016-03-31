package com.zangcun.store.utils;

public class ClickUtil {
    private final static int DOUBLE_CLICK = 1500;
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < DOUBLE_CLICK) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}