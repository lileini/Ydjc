package com.zangcun.store.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.squareup.picasso.Picasso;
import com.zangcun.store.R;
import com.zangcun.store.activity.OrderActivity;
import com.zangcun.store.entity.OrderResultEntity;
import com.zangcun.store.entity.UpDateOrder;
import com.zangcun.store.net.Net;
import com.zangcun.store.person.IndentActivity;
import com.zangcun.store.utils.DialogUtil;
import com.zangcun.store.utils.DictionaryTool;
import com.zangcun.store.utils.HttpUtils;
import com.zangcun.store.utils.ToastUtils;
import de.greenrobot.event.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//全部订单适配器
public class IndentAdapter extends BaseAdapter {
    private Context mContext;
    private List<OrderResultEntity.OrderBean> mDataList;

    public IndentAdapter(Context mContext, List<OrderResultEntity.OrderBean> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
    }
    public void setDate(List<OrderResultEntity.OrderBean> mDataList){
        this.mDataList = mDataList;
        notifyDataSetChanged();
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
        OrderResultEntity.OrderBean orderBean = mDataList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lindo, null);
            holder = new ViewHolder();
            holder.tv_lin_time = (TextView) convertView.findViewById(R.id.tv_lin_time);
            holder.tv_lin_zt = (TextView) convertView.findViewById(R.id.tv_lin_zt);
            holder.lin_img = (ImageView) convertView.findViewById(R.id.lin_img);
            holder.lin_number = (TextView) convertView.findViewById(R.id.lin_number);
            holder.money = (TextView) convertView.findViewById(R.id.money);
            holder.btn_gopay = (Button) convertView.findViewById(R.id.btn_go_pay);
            holder.btn_cancle = (Button) convertView.findViewById(R.id.btn_lin_cancle);
            holder.llGoodsIncluded = (LinearLayout) convertView.findViewById(R.id.ll_goods_included);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        convertView
        holder.tv_lin_time.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(orderBean.getAdd_time()* 1000L)));//时间
//        holder.tv_lin_zt.setText(mDataList.get(position).getGoods_name());//状态
//        图片
        List<OrderResultEntity.OrderBean.OrderGoodsBean> order_goods = orderBean.getOrder_goods();
        for(int i= 1,size = holder.llGoodsIncluded.getChildCount(); i< size;i++){
            if (holder.llGoodsIncluded.getChildAt(i) != null)
                holder.llGoodsIncluded.removeViewAt(i);
        }
        for(int i= 0,size = order_goods.size(); i< size;i++){
            OrderResultEntity.OrderBean.OrderGoodsBean orderGoodsBean = order_goods.get(i);
            if (i == 0){
                Picasso.with(mContext).load(Net.DOMAIN + orderGoodsBean.getGood().getDefault_image())
                        .placeholder(R.drawable.sp_icon_zw).error(R.drawable.sp_icon_zw).into((ImageView) holder.lin_img);
                holder.lin_number.setText(orderGoodsBean.getGoods_numbers()+"");//数量
            }else {
                RelativeLayout rlGoods = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.item_goods, null);
                ImageView ivGoods = (ImageView) rlGoods.getChildAt(0);
                Picasso.with(mContext).load(Net.DOMAIN + orderGoodsBean.getGood().getDefault_image())
                        .placeholder(R.drawable.sp_icon_zw).error(R.drawable.sp_icon_zw).into(ivGoods);
                TextView tvNumber = (TextView) rlGoods.getChildAt(2);
                tvNumber.setText(orderGoodsBean.getGoods_numbers()+"");
                holder.llGoodsIncluded.addView(rlGoods);
            }
        }
        holder.money.setText("¥" + orderBean.getOrder_amount());//金额
//        holder.btn_lin_cancle.setOnClickListener(new MyDelLister(position));//取消订单
//        去支付
//        holder.btn_go_pay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        if (orderBean.getOrder_status() == 0){
            holder.btn_cancle.setVisibility(View.GONE);
            holder.btn_gopay.setVisibility(View.GONE);
        }else {
            holder.btn_cancle.setVisibility(View.VISIBLE);
            holder.btn_gopay.setVisibility(View.VISIBLE);
            holder.btn_gopay.setText("去支付");
            holder.btn_cancle.setText("取消订单");
            holder.btn_gopay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent  intent = new Intent(mContext,OrderActivity.class);
                    intent.putExtra("order_id",orderBean.getOrder_id());
                    ((IndentActivity)mContext).startActivityForResult(intent,100);
                }
            });
            holder.btn_cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestCancelOrder(orderBean.getOrder_id());
                }
            });
        }
        holder.tv_lin_zt.setText(getOrderStatus(orderBean.getOrder_status()));
        return convertView;
    }

    /**
     * 获得返回的状态
     * @param order_status
     * @return
     */
    private String getOrderStatus(int order_status) {
        switch (order_status){
            case 11:{
                return "等待付款";
            }
            case 40:{
                return "付款成功";
            }
            case 30:{
                return "等待收货";
            }
            case 20:{
                return "等待发货";
            }
            default:{
                return "已取消";
            }
        }

    }
    /**
     * 请求取消订单
     */
    private void requestCancelOrder(int order_id) {
        if (!HttpUtils.isHaveNetwork()) {
            DialogUtil.dialogUser(mContext,"请检查网络设置");
            return;
        }
        RequestParams params = new RequestParams(Net.HOST + "orders/" + order_id + "/cancel.json ");
        params.addHeader("Authorization", DictionaryTool.getToken(mContext));
        HttpUtils.HttpPutMethod(new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String s) {
                ToastUtils.show(mContext, "取消订单成功");
//                ((IndentActivity)mContext).initDate();
                EventBus.getDefault().post(new UpDateOrder());
                // TODO: 2016/5/2 通知界面更新数据

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
        private TextView tv_lin_zt;//状态
        private ImageView lin_img;//商品图片
        private TextView lin_number;//商品数量
        private TextView money;//商品总价
        private Button btn_cancle;//根据状态显示隐藏
        private Button btn_gopay;//根据状态显示或隐藏
        private LinearLayout llGoodsIncluded;//商品父容器
    }
}
