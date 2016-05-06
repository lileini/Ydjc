package com.zangcun.store.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.squareup.picasso.Picasso;
import com.zangcun.store.R;
import com.zangcun.store.activity.OrderActivity;
import com.zangcun.store.model.FxModel;
import com.zangcun.store.net.CommandBase;
import com.zangcun.store.net.Net;
import com.zangcun.store.other.Const;
import com.zangcun.store.utils.DictionaryTool;
import com.zangcun.store.utils.HttpUtils;
import com.zangcun.store.utils.ToastUtils;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 待付款
 */
public class LinPayAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mDataList;
    private int order_id;

    public LinPayAdapter(Context mContext, List<String> mDataList) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lindo, null);
            holder = new ViewHolder();
            holder.tv_lin_time = (TextView) convertView.findViewById(R.id.tv_lin_time);
            holder.tv_lin_zt = (TextView) convertView.findViewById(R.id.tv_lin_zt);
            holder.lin_img = (ImageView) convertView.findViewById(R.id.lin_img);
            holder.lin_number = (TextView) convertView.findViewById(R.id.lin_number);
            holder.money = (TextView) convertView.findViewById(R.id.money);
            holder.btn_lin_cancle = (Button) convertView.findViewById(R.id.btn_lin_cancle);
            holder.btn_go_pay = (Button) convertView.findViewById(R.id.btn_go_pay);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        holder.tv_lin_time.setText(mDataList.get(position).getGoods_name());//时间
//        holder.tv_lin_zt.setText(mDataList.get(position).getGoods_name());//状态
        //图片
//        Picasso.with(mContext).load(Net.DOMAIN + mDataList.get(position)
//                .getDefault_image()).placeholder(R.drawable.sp_icon_zw).error(R.drawable.sp_icon_zw).into((ImageView) holder.lin_img);
//        holder.lin_number.setText(mDataList.get(position).getGoods_name());//数量
//        holder.money.setText("¥" + mDataList.get(position).getPrice());//金额
//        holder.btn_lin_cancle.setOnClickListener(new MyDelLister(position));//取消订单
        //去支付
//        holder.btn_go_pay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        holder.btn_lin_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCancelOrder();
            }
        });
        return convertView;
    }

    /**
     * 请求取消订单
     */
    private void requestCancelOrder() {
        RequestParams params = new RequestParams(Net.HOST + "orders/" + order_id + "/cancel.json ");
        params.addHeader("Authorization", DictionaryTool.getToken(mContext));
        HttpUtils.HttpPutMethod(new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String s) {
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                ToastUtils.show(mContext, "网络异常,\n取消订单失败");
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        }, params);
    }

    static class ViewHolder {
        private TextView tv_lin_time;//下单时间
        private TextView tv_lin_zt;//付款状态
        private ImageView lin_img;//商品图片
        private TextView lin_number;//商品数量
        private TextView money;//商品总价
        private Button btn_lin_cancle;//取消订单
        private Button btn_go_pay;//去支付
    }
}
