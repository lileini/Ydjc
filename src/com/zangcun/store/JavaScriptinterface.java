package com.zangcun.store;

import android.app.Activity;
import android.content.Intent;

//专题页面中WebView与JS进行交互
public class JavaScriptinterface {

    Activity mActivity;

    public JavaScriptinterface(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void startActivity() {
        Intent intent = new Intent();
        intent.setClass(mActivity, MyActivity.class);
        mActivity.startActivity(intent);
        mActivity.finish();
    }
}
