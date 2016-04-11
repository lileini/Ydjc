package com.zangcun.store.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.zangcun.store.BaseActivity;
import com.zangcun.store.R;
import com.zangcun.store.model.FxModel;
import com.zangcun.store.net.CommandBase;
import com.zangcun.store.net.Net;
import com.zangcun.store.other.Const;

import java.util.HashMap;
import java.util.Map;

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
        orderGoodImg = (ImageView) findViewById(R.id.order_good_img);
        orderGoodName = (TextView) findViewById(R.id.order_good_name);
        orderGoodDesc = (TextView) findViewById(R.id.order_good_desc);
        orderCailiao = (TextView) findViewById(R.id.order_cailiao);
        orderGoodCount = (TextView) findViewById(R.id.order_good_count);
        orderGoodPrice = (TextView) findViewById(R.id.order_good_price);
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
    }

    private void init() {
        mBack = (ImageView) findViewById(R.id.goods_back);
        mTitle = (TextView) findViewById(R.id.tv_top_title);
        assignViews();

        mOrderPay = (TextView) findViewById(R.id.order_to_pay);
        mOrderPay.setOnClickListener(this);
        mOrderDel = (TextView) findViewById(R.id.order_delete);
        mOrderDel.setOnClickListener(this);

    }

    private void initEvent() {
        mBack.setOnClickListener(this);
    }

    private void initData() {
        mTitle.setText("订单中心");
        mTitle.setTextSize(16);
        Bundle bundle = getIntent().getBundleExtra("bundle");
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
        orderPayMoney.setText(pay+"");
//        bundle.putString("color",color);
//        bundle.putString("size",size);
//        bundle.putString("count",mCount.getText().toString());
//        bundle.putSerializable("OptionsIdEntity",getIntent().getSerializableExtra("OptionsIdEntity"));

        //网络获取数据
        //初始化数据

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goods_back://返回键
                this.finish();
                break;
            case R.id.order_delete://取消支付
                break;
            case R.id.order_to_pay://立即支付
                popupPay();
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
                if (mZFB.isSelected() || mWeixin.isSelected()) {
                    return;
                } else {
                    payDialog();
                }
                break;
        }
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

    //提示框
    private void payDialog() {
        LayoutInflater inflaterDl = LayoutInflater.from(this);
        RelativeLayout layout = (RelativeLayout) inflaterDl.inflate(R.layout.showdialog, null);
        final Dialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        dialog.getWindow().setContentView(layout);
        TextView tv = (TextView) layout.findViewById(R.id.dialog_text);
        tv.setText("请先选择支付方式~");
        Button btnCancel = (Button) layout.findViewById(R.id.dialog_sure);
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    /**
     * 封装请求参数
     */
    private void requestData() {
        Map<String, String> map = new HashMap<>();
//        map.put("需要传递的key ", "需要传递的值");
        CommandBase.requestDataNoGet(getApplicationContext(), Const.URL_WAITING_FOR_PAY, handler, null);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Const.SUCCESS) {
                //做逻辑处理
            } else if (msg.what == Const.ERROR) {

            }
        }
    };

}
