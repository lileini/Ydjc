package com.zangcun.store.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.google.gson.JsonObject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.zangcun.store.BaseActivity;
import com.zangcun.store.R;
import com.zangcun.store.adapter.ChooseShopCarAdapter;
import com.zangcun.store.adapter.OrderAdapter;
import com.zangcun.store.alipay.AliPayUtil;
import com.zangcun.store.alipay.PayResult;
import com.zangcun.store.entity.OrderResultEntity;
import com.zangcun.store.model.FxModel;
import com.zangcun.store.model.GetAddressResultModel;
import com.zangcun.store.model.ShopCarModel;
import com.zangcun.store.net.CommandBase;
import com.zangcun.store.net.Net;
import com.zangcun.store.other.Const;
import com.zangcun.store.utils.*;
import com.zangcun.store.widget.InnerListView;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

//订单中心
public class OrderActivity extends BaseActivity implements OnClickListener {

    private ImageView mBack;
    private TextView mTitle;
    private TextView mOrderPay;
    private TextView mOrderDel;
    private ImageView mPopdel;
    private Button mGoPay;
    private PopupWindow mPopWindow;

    private LinearLayout mZFB;
    private LinearLayout mWeixin;
    private boolean isCheck = true;
    private InnerListView mListView;
    //订单号
    private int order_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);

        init();
        initEvent();
        initData();

    }

    private TextView orderUsername;
    private TextView orderPhone;
    private TextView orderAddress;
    private TextView orderExpressTime;
    private ImageView orderGoodImg;
    private TextView orderGoodName;
    private TextView orderGoodDesc;
    private TextView orderCailiao;
    private TextView orderGoodCount;
    private TextView orderGoodPrice;
    private TextView orderPayWay;
    private TextView orderGfQQ;
    private TextView orderGfKf;
    private TextView orderPayMoney;
    private TextView orderNumber;
    private TextView orderTime;
    private TextView orderMoney;
    private TextView shoopingMoney;
    private TextView allMoney;
    private LinearLayout dindanBottom;
    private TextView orderDelete;
    private TextView orderToPay;

    private void assignViews() {
        orderUsername = (TextView) findViewById(R.id.order_username);
        orderPhone = (TextView) findViewById(R.id.order_phone);
        orderAddress = (TextView) findViewById(R.id.order_address);
        orderExpressTime = (TextView) findViewById(R.id.order_express_time);
        orderPayWay = (TextView) findViewById(R.id.order_pay_way);
        orderPayMoney = (TextView) findViewById(R.id.order_pay_money);
        orderNumber = (TextView) findViewById(R.id.order_number);
        orderTime = (TextView) findViewById(R.id.order_time);
        orderMoney = (TextView) findViewById(R.id.order_money);
        shoopingMoney = (TextView) findViewById(R.id.shooping_money);
        allMoney = (TextView) findViewById(R.id.all_money);
        dindanBottom = (LinearLayout) findViewById(R.id.dindan_bottom);
        orderDelete = (TextView) findViewById(R.id.order_delete);
        orderToPay = (TextView) findViewById(R.id.order_to_pay);
        orderGfQQ = (TextView) findViewById(R.id.order_gf_qq);
        orderGfKf= (TextView) findViewById(R.id.order_gf_phone);
    }

    private void init() {
        mBack = (ImageView) findViewById(R.id.goods_back);
        mTitle = (TextView) findViewById(R.id.tv_top_title);
        assignViews();

        mOrderPay = (TextView) findViewById(R.id.order_to_pay);
        mOrderPay.setOnClickListener(this);
        mOrderDel = (TextView) findViewById(R.id.order_delete);
        mOrderDel.setOnClickListener(this);
        mListView = (InnerListView) findViewById(R.id.listView);
    }

    private void initEvent() {
        mBack.setOnClickListener(this);
    }

    private void initData() {
        mTitle.setTextSize(16);
        /*Bundle bundle = getIntent().getBundleExtra("bundle");
        String color = bundle.getString("color");
        String size = bundle.getString("size");
        String count = bundle.getString("count");
        FxModel.OptionsIdEntity optionsIdEntity = (FxModel.OptionsIdEntity) bundle.getSerializable("OptionsIdEntity");
        FxModel fxModel = (FxModel) bundle.getSerializable("fxModel");

         ImageLoader.getInstance().loadImage(Net.DOMAIN+fxModel.getDefault_image(),new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                orderGoodImg.setImageBitmap(loadedImage);
            }
        });
        orderGoodName.setText(fxModel.getGoods_name());
        orderGoodDesc.setText("颜色："+color);
        orderCailiao.setText("尺寸："+size);
        orderGoodCount.setText(count);
        orderGoodPrice.setText("¥"+optionsIdEntity.getPrice());
        double pay = Double.valueOf(optionsIdEntity.getPrice()) * Integer.valueOf(count.substring(1));
        orderPayMoney.setText(pay+"");*/
//        bundle.putString("color",color);
//        bundle.putString("size",size);
//        bundle.putString("count",mCount.getText().toString());
//        bundle.putSerializable("OptionsIdEntity",getIntent().getSerializableExtra("OptionsIdEntity"));
//        ArrayList<Parcelable> mDates = getIntent().getParcelableArrayListExtra("mDates");
//        OrderResultEntity.OrderBean orderBean = getIntent().getParcelableExtra("OrderBean");
        order_id = getIntent().getIntExtra("order_id", -1);
        boolean isOrderDetail = getIntent().getBooleanExtra("mDataList", false);
        if (!HttpUtils.isHaveNetwork()) {
            DialogUtil.dialogUser(this,"请检查网络设置");
            return;
        }
        if (isOrderDetail){
            mTitle.setText("订单详情");
        }else {
            mTitle.setText("订单中心");
        }
        RequestParams params = new RequestParams(Net.HOST + "orders/"+order_id+".json");
        params.addHeader("Authorization", DictionaryTool.getToken(this));
        if (!HttpUtils.isHaveNetwork()) {
            DialogUtil.dialogUser(this,"请检查网络设置");
            return;
        }
        HttpUtils.HttpGetMethod(new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "onSuccess = "+ s);
                OrderResultEntity.OrderBean orderBean= GsonUtil.getResult(s,OrderResultEntity.OrderBean.class);
                Log.i(TAG,"orderResultEntity = "+orderBean.toString());
                Log.i(TAG,"getOrder_id() = "+ orderBean.getOrder_id());
                if (orderBean != null) {
                    initData2(orderBean);
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        },params);

    }

    private void initData2(OrderResultEntity.OrderBean orderBean) {
        List<OrderResultEntity.OrderBean.OrderGoodsBean> order_goodsList = orderBean.getOrder_goods();

        OrderAdapter chooseShopCarAdapter = new OrderAdapter(this, order_goodsList, R.layout.item_pay);
        mListView.setAdapter(chooseShopCarAdapter);

        orderUsername.setText(orderBean.getConsignee());
        orderAddress.setText(orderBean.getAddress());
        orderPhone.setText(orderBean.getMobile());

        //网络获取数据
        //初始化数据
        if (orderBean == null)
            return;
        orderNumber.setText(orderBean.getOrder_sn());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        orderTime.setText(sdf.format(orderBean.getAdd_time()));
        shoopingMoney.setText(orderBean.getGoods_amount());
        allMoney.setText(orderBean.getOrder_amount());
        orderPayMoney.setText(orderBean.getGoods_amount());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goods_back://返回键
                this.finish();
                break;
            case R.id.order_delete://取消支付
                requestCancelOrder();
                break;
            case R.id.order_to_pay://立即支付
                popupPay();
                mZFB.setSelected(true);
                break;
            case R.id.popup_del:
                mPopWindow.dismiss();
                break;


            case R.id.pay_zhifubao://支付宝支付
                initSelected();
                mZFB.setSelected(true);
                break;
            case R.id.pay_weixin://微信支付
                initSelected();
                mWeixin.setSelected(true);
                break;
            case R.id.pup_go_pay://去支付
                if (mZFB.isSelected()) {//支付宝支付
                    try {
                        requestAlipay();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
                if (mWeixin.isSelected()) {//微信支付
                    return;
                }
                break;
        }
    }

    /**
     * 请求支付宝支付
     */
    private void requestAlipay() {
        RequestParams params = new RequestParams(Net.HOST + "orders/"+order_id+"/create_alipay.json");
        params.addHeader("Authorization",DictionaryTool.getToken(this));
        HttpUtils.HttpPostMethod(new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "onSuccess = "+s);
                JSONObject object = null;
                AliPayUtil aliPayUtil = null;
                try {
                    object = new JSONObject(s);
                    String message = object.getString("message");
                    aliPayUtil = new AliPayUtil(OrderActivity.this, mHandler);
                    aliPayUtil.pay(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG,"alipay failed");
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        },params);
    }

    /**
     * 请求取消订单
     */
    private void requestCancelOrder() {
        if (!HttpUtils.isHaveNetwork()) {
            DialogUtil.dialogUser(this,"请检查网络设置");
            return;
        }
        RequestParams params = new RequestParams(Net.HOST + "orders/" + order_id + "/cancel.json ");
        params.addHeader("Authorization", DictionaryTool.getToken(this));
        HttpUtils.HttpPutMethod(new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String s) {
                return false;
            }

            @Override
            public void onSuccess(String s) {
                ToastUtils.show(getApplication(), "取消订单成功");
                finish();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                ToastUtils.show(getApplication(), "网络异常,\n取消订单失败");
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        }, params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    //选择支付方式
    private void initSelected() {
        mZFB.setSelected(false);
        mWeixin.setSelected(false);
    }

    //弹窗
    private void popupPay() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.popupwindow_pay, null);
        mPopWindow = new PopupWindow(contentView, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        mPopWindow.setFocusable(true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setAnimationStyle(R.style.popwin_anim_style);
        mZFB = (LinearLayout) contentView.findViewById(R.id.pay_zhifubao);
        mWeixin = (LinearLayout) contentView.findViewById(R.id.pay_weixin);
        mPopdel = (ImageView) contentView.findViewById(R.id.popup_del);
        mGoPay = (Button) contentView.findViewById(R.id.pup_go_pay);
        mZFB.setOnClickListener(this);
        mWeixin.setOnClickListener(this);
        mPopdel.setOnClickListener(this);
        mGoPay.setOnClickListener(this);
        View rootview = LayoutInflater.from(this).inflate(R.layout.popupwindow_pay, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

//    /**
//     * 封装请求参数
//     */
//    private void requestData() {
//        Map<String, String> map = new HashMap<>();
////        map.put("需要传递的key ", "需要传递的值");
//        CommandBase.requestDataNoGet(getApplicationContext(), Const.URL_WAITING_FOR_PAY, handler, null);
//    }

//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == Const.SUCCESS) {
//                //做逻辑处理
//            } else if (msg.what == Const.ERROR) {
//
//            }
//        }
//    };

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(OrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        // TODO: 2016/4/19 支付成功跳转界面
//                        finish();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(OrderActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(OrderActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    mPopWindow.dismiss();
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

}
