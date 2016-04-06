package com.zangcun.store.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.zangcun.store.R;


/**
 * Created by Administrator on 2016/1/21 0021.
 */
public class CustomProgressDialog extends Dialog {
    private  ContentLoadingProgressBar progressBar;
    public CustomProgressDialog(Context context){
        this(context, R.style.customDialog);
    }

    public CustomProgressDialog(Context context,int theme){
        super(context, theme);
        progressBar = new ContentLoadingProgressBar(context);
        this.setContentView(new ContentLoadingProgressBar(context));
        this.getWindow().getAttributes().gravity= Gravity.CENTER;
        setCancelable(false);
    }
    public void setProgressBarVisible(boolean isVisible){
        if (isVisible){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }


}
