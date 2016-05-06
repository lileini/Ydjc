package com.zangcun.store.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.wx.wheelview.util.WheelUtils;
import com.wx.wheelview.widget.WheelView;

import java.util.List;

/**
 * Created by Administrator on 2016/5/6.
 */
public class MyWheelView<T> extends WheelView<T> {
    private onWheelDataChangeListener mWheelDataChangeListener;

    public MyWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void setWheelData(List<T> list) {
        super.setWheelData(list);
        if (!WheelUtils.isEmpty(list) && mWheelDataChangeListener != null ){
//            this.setSelection(0);
            mWheelDataChangeListener.onWheelDataChanged(list,0);
        }
    }
    public void setOnWheelDataChangeListener(onWheelDataChangeListener listener){
        this.mWheelDataChangeListener = listener;
    }
    public interface onWheelDataChangeListener<T>{
        void onWheelDataChanged(List<T> datas,int position);
    }
}
