package com.zangcun.store.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.zangcun.store.BaseActivity;
import com.zangcun.store.person.AddressActivity;
import com.zangcun.store.R;
import com.zangcun.store.net.CommandBase;
import com.zangcun.store.other.Const;

import java.util.HashMap;
import java.util.Map;

//支付中心
public class PayActivity extends BaseActivity implements OnClickListener {
	
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
		
		mImg = (ImageView) findViewById(R.id.pay_img);

		mName = (TextView) findViewById(R.id.pay_name);
		mDesc = (TextView) findViewById(R.id.pay_desc);
		mCailiao = (TextView) findViewById(R.id.pay_cailiao);
		mCount = (TextView) findViewById(R.id.pay_count);
		mPrice = (TextView) findViewById(R.id.pay_price);
		mAddAddress = (TextView) findViewById(R.id.pay_add_address);
		mYunfei = (TextView) findViewById(R.id.pay_yunfei);
		mSubmit = (TextView) findViewById(R.id.pay_submit);
		mTip = (TextView) findViewById(R.id.pay_tip);
		
	}

	private void initData() {
		mTitle.setText("支付中心");
		mTitle.setTextSize(16);
		
		
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
			}
			//到订单详情
			startActivity(new Intent(PayActivity.this,OrderActivity.class));
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
	

}
