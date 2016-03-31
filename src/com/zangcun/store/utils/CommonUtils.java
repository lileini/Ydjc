package com.zangcun.store.utils;

import android.content.Context;
import android.util.TypedValue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//dp转px,px转dp,判断手机号
public class CommonUtils {
    public static int dpToPx(Context ctx, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, ctx.getResources().getDisplayMetrics());
    }

    public static int pxToDp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    //判断手机号码
    public static boolean isMobileNO(String mobiles) {

        Pattern pattern = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(mobiles);

        return matcher.matches();

    }


}
