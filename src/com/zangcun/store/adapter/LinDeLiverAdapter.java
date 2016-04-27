package com.zangcun.store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.zangcun.store.R;

import java.util.List;

/**
 * 待发货
 * */
public class LinDeLiverAdapter extends BaseAdapter {
    private List<String> mDataList;
    private Context mContext;

    public LinDeLiverAdapter(Context mContext, List<String> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lindo, null);
            holder = new ViewHolder();
            holder.tv_lin_time = (TextView) convertView.findViewById(R.id.tv_lin_time);
            holder.lin_img = (ImageView) convertView.findViewById(R.id.lin_img);
            holder.lin_number = (TextView) convertView.findViewById(R.id.lin_number);
            holder.money = (TextView) convertView.findViewById(R.id.money);
            holder.btn_send = (Button) convertView.findViewById(R.id.btn_go_pay);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        holder.tv_lin_time.setText(mDataList.get(position).getGoods_name());//时间
        //图片
//        Picasso.with(mContext).load(Net.DOMAIN + mDataList.get(position)
//                .getDefault_image()).placeholder(R.drawable.sp_icon_zw).error(R.drawable.sp_icon_zw).into((ImageView) holder.lin_img);
//        holder.lin_number.setText(mDataList.get(position).getGoods_name());//数量
//        holder.money.setText("¥" + mDataList.get(position).getPrice());//金额
        return convertView;
    }

    static class ViewHolder {
        private TextView tv_lin_time;//下单时间
        private ImageView lin_img;//商品图片
        private TextView lin_number;//商品数量
        private TextView money;//商品总价
        private Button btn_lin_cancle;//申请退款（不用管退款）
        private Button btn_send;//提醒发货
    }
}
