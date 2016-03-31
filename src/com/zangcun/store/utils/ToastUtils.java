package com.zangcun.store.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.zangcun.store.R;

//自定义Toast
public class ToastUtils {
    private static Toast mToast;
    private static TextView mTextView;
    //封装,参数少的调用参数多的
    public  static  void  show(Context ctx, String text){
        show(ctx,text,false);

    }

    public static void show(Context ctx, String text, boolean isLong) {
        if (mToast == null) {//判断是否为空，全局上下文
            mToast = new Toast(ctx.getApplicationContext());
            View view = LayoutInflater.from(ctx).inflate(R.layout.toast, null);
            mTextView = (TextView) view.findViewById(R.id.tv_content);
            mToast.setView(view);//自定义的toast布局到view上面,
        }
        //设置时间
        mToast.setDuration(isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        //设置文字
        mTextView.setText(text);
        //显示
        mToast.show();
    }
}

