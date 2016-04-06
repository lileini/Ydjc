package com.zangcun.store.utils;

import android.content.Context;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.zangcun.store.R;
import com.zangcun.store.widget.CustomProgressDialog;

import java.io.DataInput;

/**
 * 动态添加progressBar
 */
public final class DialogUtil {
    private  static CustomProgressDialog dialog;
    public static  void showDialog(Context context){
        if (dialog == null ){
            dialog = new CustomProgressDialog(context);
        }
        dialog.show();
    }
    public static  void dissmissDialog(){
        dialog.dismiss();
    }
}
