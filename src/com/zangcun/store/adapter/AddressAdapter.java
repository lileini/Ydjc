package com.zangcun.store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zangcun.store.R;
import com.zangcun.store.model.AddressModel;

import java.util.List;

//收货地址适配器
public class AddressAdapter extends BaseAdapter {
    private Context mContext;
    private List<AddressModel> mDataList;

    public AddressAdapter(Context mContext, List<AddressModel> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public AddressModel getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_address_add, null);
            holder = new ViewHolder();
            holder.address_name = (TextView) convertView.findViewById(R.id.gv_price);
            holder.address_mr = (TextView) convertView.findViewById(R.id.address_mr);
            holder.address_phone = (TextView) convertView.findViewById(R.id.address_phone);
            holder.address_city = (TextView) convertView.findViewById(R.id.address_city);
            holder.address_sz_mr = (TextView) convertView.findViewById(R.id.address_sz_mr);
            holder.address_bj = (LinearLayout) convertView.findViewById(R.id.address_bj);
            holder.address_del = (LinearLayout) convertView.findViewById(R.id.address_del);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    static class ViewHolder {
        private TextView address_name;//收货人姓名
        private TextView address_mr;//默认地址
        private TextView address_phone;//收货人电话
        private TextView address_city;//详细地址
        private TextView address_sz_mr;//设为默认
        private LinearLayout address_bj;//编辑
        private LinearLayout address_del;//删除

    }
}
