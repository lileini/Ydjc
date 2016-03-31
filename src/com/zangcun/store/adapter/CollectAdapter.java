package com.zangcun.store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zangcun.store.R;
import com.zangcun.store.model.CollectModel;

import java.util.List;

//我的收藏适配器
public class CollectAdapter extends BaseAdapter {
    private Context mContext;
    private List<CollectModel> mDataList;
    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public CollectModel getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gv_layout, null);
            holder = new ViewHolder();
            holder.price = (TextView) convertView.findViewById(R.id.gv_price);
            holder.lin_img = (ImageView) convertView.findViewById(R.id.gv_iv);
            holder.lin_miaosu = (TextView) convertView.findViewById(R.id.gv_info);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    static class ViewHolder {
        private ImageView lin_img;//商品图片
        private TextView price;//价格
        private TextView lin_miaosu;//商品描述

    }
}
