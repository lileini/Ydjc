package com.zangcun.store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.zangcun.store.R;
import com.zangcun.store.entity.OrderResultEntity;
import com.zangcun.store.net.Net;
import com.zangcun.store.utils.DictionaryTool;
import com.zangcun.store.utils.HttpUtils;
import com.zangcun.store.utils.ToastUtils;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//全部订单适配器
public class IndentAdapter extends BaseAdapter {
    private Context mContext;
    private List<OrderResultEntity.OrderBean> mDataList;
    private int order_id;

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

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_lin_time.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(orderBean.getAdd_time())));//时间
//        holder.tv_lin_zt.setText(mDataList.get(position).getGoods_name());//状态
//        图片
        Picasso.with(mContext).load(Net.DOMAIN + orderBean.getOrder_goods().get(0).getGood().getDefault_image())
                .placeholder(R.drawable.sp_icon_zw).error(R.drawable.sp_icon_zw).into((ImageView) holder.lin_img);
        holder.lin_number.setText(orderBean.getOrder_goods().get(0).getGoods_numbers());//数量
        holder.money.setText("¥" + orderBean.getOrder_amount());//金额
//        holder.btn_lin_cancle.setOnClickListener(new MyDelLister(position));//取消订单
//        去支付
//        holder.btn_go_pay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        holder.btn_cancle.setOnClickListener(new View.OnClickListener() {
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
        HttpUtils.HttpPutMethod(new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String s) {
                return false;
            }

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
        private TextView tv_lin_zt;//状态
        private ImageView lin_img;//商品图片
        private TextView lin_number;//商品数量
        private TextView money;//商品总价
        private Button btn_cancle;//根据状态显示隐藏
        private Button btn_gopay;//根据状态显示或隐藏
    }
}
