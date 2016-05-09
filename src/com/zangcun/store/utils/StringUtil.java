package com.zangcun.store.utils;

/**
 * Created by Administrator on 2016/5/9.
 */
public class StringUtil {
    /**
     * 获取字符串 str 中地 number个 charstr的位置
     * @param str
     * @param charStr
     * @param number
     * @return
     */
    public static int indexofFrom(String str,String charStr,int number){
        int temp = 0;
        for (int i = 0;i<number;i++){
            temp = str.indexOf(charStr,temp) + 1;
        }
        return temp-1;
    }
}
