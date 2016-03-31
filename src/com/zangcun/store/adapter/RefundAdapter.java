package com.zangcun.store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zangcun.store.R;
import com.zangcun.store.model.RefundModel;

import java.util.List;

//申请退款适配器
public class RefundAdapter extends BaseAdapter {
    private Context mContext;
    private List<RefundModel> mDataList;

    public RefundAdapter(Context mContext, List<RefundModel> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public RefundModel getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tuikuan_one_item, null);
            holder = new ViewHolder();
            holder.refund_img = (ImageView) convertView.findViewById(R.id.lin_img);
            holder.shop_name = (TextView) convertView.findViewById(R.id.shop_name);
            holder.shop_count = (TextView) convertView.findViewById(R.id.shop_count);
            holder.lin_number = (TextView) convertView.findViewById(R.id.lin_number);
            holder.refund_cale = (ImageView) convertView.findViewById(R.id.refund_cale);
            holder.refund_posit = (TextView) convertView.findViewById(R.id.refund_posit);
            holder.refund_add = (ImageView) convertView.findViewById(R.id.refund_add);
            holder.refund_money = (TextView) convertView.findViewById(R.id.refund_money);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    static class ViewHolder {
        private ImageView refund_img;//商品图片
        private TextView shop_name;//商品名称
        private TextView shop_count;//商品规格
        private TextView lin_number;//商品个数
        private ImageView refund_cale;//减少
        private TextView refund_posit;//退货数量
        private ImageView refund_add;//增加
        private TextView refund_money;//退款金额

    }

}
