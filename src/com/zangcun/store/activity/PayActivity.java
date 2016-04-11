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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.zangcun.store.BaseActivity;
import com.zangcun.store.adapter.ShopCarAdapter;
import com.zangcun.store.model.FxModel;
import com.zangcun.store.model.ShopCarModel;
import com.zangcun.store.net.Net;
import com.zangcun.store.person.AddressActivity;
import com.zangcun.store.R;
import com.zangcun.store.net.CommandBase;
import com.zangcun.store.other.Const;
import com.zangcun.store.utils.DictionaryTool;
import com.zangcun.store.utils.HttpUtils;
import com.zangcun.store.utils.ToastUtils;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//支付中心
public class PayActivity extends BaseActivity implements OnClickListener,ShopCarAdapter.PriceAndCountChangeListener {
	
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
	private ShopCarAdapter mAdapter;

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
				List<ShopCarModel> mDatas =  new Gson().fromJson(s,new TypeToken<List<ShopCarModel>>(){}.getType());
//                mDatas = GsonUtil.getResult2(s, ShopCarModel.class);
				com.zangcun.store.utils.Log.i(TAG, "result2 = "+ mDatas.toString());
				com.zangcun.store.utils.Log.i(TAG, "result2.size = "+ mDatas.size());
				com.zangcun.store.utils.Log.i(TAG, "model = "+ mDatas.get(0));
                /*for (ShopCarModel model :mDatas){
                    Log.i(TAG, "model = "+ model);
                }*/
				if (mDatas == null || mDatas.size() == 0) {
					mTip.setText("总金额 ¥"+(0));
					return;
				}
				if (mAdapter == null){

					mAdapter = new ShopCarAdapter(PayActivity.this, mDatas,
							R.layout.item_shop_car);
					mAdapter.setListener(PayActivity.this);
					listView.setDividerHeight(20);
					listView.setAdapter(mAdapter);
				}else {
					mAdapter.setData(mDatas);
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
			startActivity(intent);
			break;

		case R.id.pay_submit:
			//订单详情
			//如果没有选择地址
			if(mAddAddress.equals("添加收货地址")){
				Toast.makeText(PayActivity.this, "请添加收获地址",Toast.LENGTH_SHORT).show();
//				return;
			}
			//到订单详情
			Intent intent1 = new Intent(PayActivity.this, OrderActivity.class);
			intent1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			Bundle bundle = new Bundle();
			bundle.putString("color",color);
			bundle.putString("size",size);
			bundle.putString("count",mCount.getText().toString());
			bundle.putSerializable("OptionsIdEntity",getIntent().getSerializableExtra("OptionsIdEntity"));
			bundle.putSerializable("fxModel",getIntent().getSerializableExtra("fxModel"));
			intent1.putExtra("bundle",bundle);
			startActivity(intent1);
			break;
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


	@Override
	public void onPriceChanged(int total) {
		mTip.setText("总金额 ¥"+total);
	}

	@Override
	public void onCountCHanged(int count) {

	}
}
