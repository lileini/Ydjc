package com.zangcun.store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.wx.wheelview.adapter.BaseWheelAdapter;
import com.zangcun.store.R;
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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.wheel_item, null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(mList.get(position).getName());
        return convertView;
    }

    static class ViewHolder {
        TextView textView;
    }
}
