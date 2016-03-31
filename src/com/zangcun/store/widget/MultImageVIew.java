package com.zangcun.store.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;


public class MultImageVIew extends ImageView {
    public static final int DEFAUT = 0;
    public static final int LOW_TO_HEIGHT = 1;
    public static final int HEIGHT_TO_LOW = 2;
    private int mCurrState;


    public MultImageVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setStateAndImg(int state, int res) {
        mCurrState = state;
        setImageResource(res);
    }

    public int getCurrState() {
        return mCurrState;
    }
}
