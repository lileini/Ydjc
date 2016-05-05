package com.zangcun.store.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.wx.wheelview.adapter.BaseWheelAdapter;
import com.wx.wheelview.widget.WheelItem;
import com.zangcun.store.model.CityModel;

/**
 * Created by Administrator on 2016/5/5.
 */
public class AddAddressWhellAdapter extends BaseWheelAdapter<CityModel>{
    private Context mContext;

    public AddAddressWhellAdapter(Context context) {
        this.mContext = context;
    }

    public View bindView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = new WheelItem(this.mContext);
        }

        WheelItem item = (WheelItem)convertView;
        item.setText(this.mList.get(position).getName());
        return (View)convertView;
    }
}
