package com.zangcun.store.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.zangcun.store.BaseActivity;
import com.zangcun.store.adapter.ChooseShopCarAdapter;
import com.zangcun.store.adapter.ShopCarAdapter;
import com.zangcun.store.entity.OrderResultEntity;
import com.zangcun.store.model.FxModel;
import com.zangcun.store.model.GetAddressResultModel;
import com.zangcun.store.model.ShopCarModel;
import com.zangcun.store.net.Net;
import com.zangcun.store.person.AddressActivity;
import com.zangcun.store.R;
import com.zangcun.store.net.CommandBase;
import com.zangcun.store.other.Const;
import com.zangcun.store.utils.DictionaryTool;
import com.zangcun.store.utils.GsonUtil;
import com.zangcun.store.utils.HttpUtils;
import com.zangcun.store.utils.ToastUtils;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//支付中心
public class PayActivity extends BaseActivity implements OnClickListener{
	
	private ImageView mBack;
	private TextView mTitle;
	
	private ImageView mImg;
	private TextView mName;
	private TextView mCailiao;
	private TextView mDesc;
	private TextView mPrice;
	private TextView mCount;
	private TextView mYunfei;
	private TextView mAddAddress;
	private TextView mSubmit;
	private TextView mTip;
	private FxModel fxModel;
	private String color;
	private String size;
	private ListView listView;
	private ChooseShopCarAdapter mAdapter;
	private ArrayList<ShopCarModel> mDatas;
	private GetAddressResultModel.AddressBean addressBean;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		init();
		initEvent();
		initData();
	}

	private void initEvent() {
		mAddAddress.setOnClickListener(this);
		mSubmit.setOnClickListener(this);
		mBack.setOnClickListener(this);
	
	}


	private void init() {
		mBack = (ImageView) findViewById(R.id.goods_back);
		mTitle = (TextView) findViewById(R.id.tv_top_title);
		
		/*mImg = (ImageView) findViewById(R.id.pay_img);

		mName = (TextView) findViewById(R.id.pay_name);
		mDesc = (TextView) findViewById(R.id.pay_desc);
		mCailiao = (TextView) findViewById(R.id.pay_cailiao);
		mCount = (TextView) findViewById(R.id.pay_count);
		mPrice = (TextView) findViewById(R.id.pay_price);*/
		listView = (ListView) findViewById(R.id.listView);
		mAddAddress = (TextView) findViewById(R.id.pay_add_address);
		mYunfei = (TextView) findViewById(R.id.pay_yunfei);
		mSubmit = (TextView) findViewById(R.id.pay_submit);
		mTip = (TextView) findViewById(R.id.pay_tip);

		assignViews();
	}
	private LinearLayout itemLayout;
	private LinearLayout addressLy1;
	private TextView addressName;
	private TextView addressMr;
	private TextView addressPhone;
	private TextView addressCity;
	private LinearLayout addressLy2;
	private TextView addressSzMr;
	private LinearLayout addressBj;
	private LinearLayout addressDel;

	private void assignViews() {
		itemLayout = (LinearLayout) findViewById(R.id.item_layout);
		addressLy1 = (LinearLayout) findViewById(R.id.address_ly1);
		addressName = (TextView) findViewById(R.id.address_name);
		addressMr = (TextView) findViewById(R.id.address_mr);
		addressPhone = (TextView) findViewById(R.id.address_phone);
		addressCity = (TextView) findViewById(R.id.address_city);
		addressLy2 = (LinearLayout) findViewById(R.id.address_ly2);
		addressSzMr = (TextView) findViewById(R.id.address_sz_mr);
		addressBj = (LinearLayout) findViewById(R.id.address_bj);
		addressDel = (LinearLayout) findViewById(R.id.address_del);
		itemLayout.setVisibility(View.GONE);
		addressLy2.setVisibility(View.GONE);
		addressMr.setVisibility(View.INVISIBLE);
	}

	private void requestCart() {
		if (TextUtils.isEmpty(DictionaryTool.getUser(this)) || TextUtils.isEmpty(DictionaryTool.getToken(this))){
			ToastUtils.show(getApplication(),"请先登录");
			return;
		}
		RequestParams params = new RequestParams(Net.URL_CARTS);
		params.addHeader("AUTHORIZATION", DictionaryTool.getToken(getApplicationContext()));
		HttpUtils.HttpGetMethod(new Callback.CacheCallback<String>() {
			@Override
			public boolean onCache(String s) {
				return false;
			}

			@Override
			public void onSuccess(String s) {
				com.zangcun.store.utils.Log.i(TAG, "onSuccess = "+ s);
				mDatas =  new Gson().fromJson(s,new TypeToken<List<ShopCarModel>>(){}.getType());
//                mDatas = GsonUtil.getResult2(s, ShopCarModel.class);
				com.zangcun.store.utils.Log.i(TAG, "result2 = "+ mDatas.toString());
				com.zangcun.store.utils.Log.i(TAG, "result2.size = "+ mDatas.size());
				com.zangcun.store.utils.Log.i(TAG, "model = "+ mDatas.get(0));
                /*for (ShopCarModel model :mDatas){
                    Log.i(TAG, "model = "+ model);
                }*/
				if (mDatas == null || mDatas.size() == 0) {
					mTip.setText("总金额 ¥"+(0));
					mAddAddress.setVisibility(View.GONE);
					return;
				}
				for (ShopCarModel shopCarModel:mDatas){
					if (shopCarModel.getIschecked() == 0){
						mDatas.remove(shopCarModel);
					}
					String[] colorSize =  shopCarModel.getColorSize();
					if (colorSize == null){
						colorSize = getColorSize(shopCarModel);
						shopCarModel.setColorSize(colorSize);
					}
					ShopCarModel.GoodBean.OptionsIdBean optionsIdBean = shopCarModel.getOptionsIdBean();
					if (optionsIdBean == null){
						for (ShopCarModel.GoodBean.OptionsIdBean optionsIdBean2 : shopCarModel.getGood().getOptions_id()) {
							if (optionsIdBean2.getSpec_1().equals(colorSize[0]) && optionsIdBean2.getSpec_2().equals(colorSize[1])) {
								optionsIdBean = optionsIdBean2;
								shopCarModel.setOptionsIdBean(optionsIdBean2);
							}
						}
					}
				}

				mAddAddress.setVisibility(View.VISIBLE);
				if (mAdapter == null){

					mAdapter = new ChooseShopCarAdapter(PayActivity.this, mDatas,
							R.layout.item_pay);
					listView.setDividerHeight(20);
					listView.setAdapter(mAdapter);
				}else {
					mAdapter.setData(mDatas);
				}
				alculatePay();

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
	 * 计算总花费
	 */
	private void alculatePay() {
		double moneny = 0;
		for (ShopCarModel shopCarModel :mDatas){
			moneny += shopCarModel.getQuantity() * Double.valueOf(shopCarModel.getOptionsIdBean().getPrice());
		}
		mTip.setText("总金额 ¥"+(moneny + 7)+"  (含运费 ¥7)");
	}

	/**
	 * 获得商品颜色和尺寸
	 * @param shopCarModel
	 * @return
	 */
	private String[] getColorSize(ShopCarModel shopCarModel) {
		String good_option = shopCarModel.getGood_option();
		String[] split = good_option.split(",");
		String color = split[0].substring(3);
		String size = split[1].substring(3);
		com.zangcun.store.utils.Log.i(TAG, "color = " + color);
		com.zangcun.store.utils.Log.i(TAG, "size = " + size);

		return new String[]{color, size};
	}

	private void initData() {
		mTitle.setText("支付中心");
		mTitle.setTextSize(16);
		requestCart();
		/*fxModel = (FxModel) getIntent().getSerializableExtra("fxModel");
		String count = getIntent().getStringExtra("count");
		Log.i(TAG,"count = "+ count);
		FxModel.OptionsIdEntity optionsIdEntity = (FxModel.OptionsIdEntity) getIntent().getSerializableExtra("OptionsIdEntity");
		if (fxModel == null)
			return;
		mName.setText(fxModel.getGoods_name());
		color = optionsIdEntity.getSpec_1();
		size = optionsIdEntity.getSpec_2();
		String desc = "";
		if (!TextUtils.isEmpty(color)){
			desc= "颜色："+color;
		}
		if (!TextUtils.isEmpty(size)){
			desc += ",尺寸："+size;
		}
		Log.i(TAG, "Net.DOMAIN+fxModel.getDefault_image() = "+ Net.DOMAIN+fxModel.getDefault_image());
		mDesc.setText(desc);
		mCount.setText("x"+count);
		double price = Double.valueOf(optionsIdEntity.getPrice()) * Integer.valueOf(count);
		mPrice.setText(price+"");
		*//*ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageLoader.displayImage(Net.DOMAIN+fxModel.getDefault_image(),//    sss
				mImg);*//*

		mTip.setText("总金额 ¥"+(price + 7)+"  (含运费 ¥7)");
		ImageLoader.getInstance().loadImage(Net.DOMAIN+fxModel.getDefault_image(), new SimpleImageLoadingListener(){

			@Override
			public void onLoadingComplete(String imageUri, View view,
										  Bitmap loadedImage) {
				super.onLoadingComplete(imageUri, view, loadedImage);
				mImg.setImageBitmap(loadedImage);
			}

		});*/
//		shopCarModel.getGood_option()

		//网络接口获取数据
		//展示数据
	}


	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.goods_back:
			this.finish();
			break;
		case R.id.pay_add_address:
			//选择地址
			Intent intent = new Intent(getApplication(),AddressActivity.class);
			intent.putExtra("isChooseAddress",true);
			startActivityForResult(intent,200);
			break;

		case R.id.pay_submit:
			//订单详情
			//如果没有选择地址
			/*if(mAddAddress.equals("添加收货地址")){
				Toast.makeText(PayActivity.this, "请添加收获地址",Toast.LENGTH_SHORT).show();
				return;
			}*/
			//到订单详情
			if (addressBean == null){
				ToastUtils.show(this,"请选择地址");
				return;
			}
			requestCreateOrder();

			break;
		}
		
	}

	/**
	 * 创建订单
	 */
	private void requestCreateOrder() {
		RequestParams params = new RequestParams(Net.URL_CEAT_ORDER);
		params.addBodyParameter("address_id",addressBean.getId()+"");
		params.addHeader("Authorization",DictionaryTool.getToken(this));
		if (!HttpUtils.isHaveNetwork()){
			ToastUtils.show(this,"请检查网络设置");
		}
		HttpUtils.HttpPostMethod(new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String s) {
				Log.i(TAG, "requestCreateOrder onSuccess = "+ s);
				OrderResultEntity result = GsonUtil.getResult(s, OrderResultEntity.class);
				int order_id = result.getOrder().getOrder_id();
				Log.i(TAG, "order_id = "+ order_id);
				sendBroadcast(new Intent(Const.SHOP_CAR_RECIEVER));
				Intent intent1 = new Intent(PayActivity.this, OrderActivity.class);
				intent1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent1.putParcelableArrayListExtra("mDates",mDatas);
				intent1.putExtra("addressBean",addressBean);
				intent1.putExtra("order_id",order_id);
				intent1.putExtra("OrderBean",result.getOrder());
				startActivity(intent1);
				finish();
			}

			@Override
			public void onError(Throwable throwable, boolean b) {
				Log.i(TAG, "requestCreateOrder onError = "+ throwable.toString());
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 200 && resultCode == 200){
			addressBean = (GetAddressResultModel.AddressBean) data.getSerializableExtra("addressBean");
			if (addressBean == null)
				return;
			Log.i(TAG,"addressBean.getMobile()2 = "+addressBean.getMobile());
			addressName.setText(addressBean.getConsignee());
			addressPhone.setText(addressBean.getMobile());
			addressCity.setText(addressBean.getAddress());
			mAddAddress.setVisibility(View.GONE);
			itemLayout.setVisibility(View.VISIBLE);
			addressPhone.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 封装请求参数
	 */
	private void requestData() {
		Map<String, String> map = new HashMap<>();
//        map.put("需要传递的key ", "需要传递的值");
		CommandBase.requestDataMap(getApplicationContext(), Const.URL_WAITING_FOR_PAY, handler, null);
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
