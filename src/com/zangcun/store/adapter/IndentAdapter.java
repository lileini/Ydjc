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
import com.zangcun.store.holder.ViewHolder;
import com.zangcun.store.model.IndentModel;

import java.util.List;

//全部订单适配器
public class IndentAdapter extends BaseAdapter {
    private Context mContext;
    private List<IndentModel> mDataList;

    public IndentAdapter(Context mContext, List<IndentModel> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public IndentModel getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lindo, null);
            holder = new ViewHolder();
            holder.tv_lin_time = (TextView) convertView.findViewById(R.id.tv_lin_time);
            holder.lin_img = (ImageView) convertView.findViewById(R.id.lin_img);
            holder.lin_number = (TextView) convertView.findViewById(R.id.lin_number);
            holder.money = (TextView) convertView.findViewById(R.id.money);
            holder.btn_gopay = (Button) convertView.findViewById(R.id.btn_go_pay);
            holder.btn_cancle = (Button) convertView.findViewById(R.id.btn_lin_cancle);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    static class ViewHolder {
        private TextView tv_lin_time;//下单时间
        private ImageView lin_img;//商品图片
        private TextView lin_number;//商品数量
        private TextView money;//总价
        private Button btn_cancle;//取消订单按钮
        private Button btn_gopay;//去支付按钮
    }
}
