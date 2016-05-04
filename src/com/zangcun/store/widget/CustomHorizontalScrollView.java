package com.zangcun.store.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by Administrator on 2016/5/4.
 */
public class CustomHorizontalScrollView extends HorizontalScrollView {
    private String TAG = "CustomHorizontalScrollView";

    public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    int tempAction = -1;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        Log.i(TAG,"action = "+action);
        Log.i(TAG,"tempAction = "+tempAction);
        if (tempAction == -1 && action == MotionEvent.ACTION_DOWN){
            tempAction = MotionEvent.ACTION_DOWN;
            return false;
        }
        if (tempAction == MotionEvent.ACTION_DOWN && action == MotionEvent.ACTION_MOVE){
            return super.onTouchEvent(ev);
        }
        if (action == MotionEvent.ACTION_UP  || action ==3){
            tempAction = -1;
        }
        return super.onTouchEvent(ev);
    }


}
