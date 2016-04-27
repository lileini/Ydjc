package com.zangcun.store.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.zangcun.store.BaseActivity;
import com.zangcun.store.R;
import com.zangcun.store.fragment.ShopFragment;

//点击详情页面中"锁"图标，跳转
public class ShopCarActivity extends BaseActivity {

	private ShopFragment mShopFragment;

	private TextView mTitleText;
	private ImageView mBack;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopcar);

		init();
		initData();
	}

	

	private void init() {
		mTitleText = (TextView) findViewById(R.id.title);
		mBack =  (ImageView) findViewById(R.id.left);
		
		mBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("debug", "点击了后退");
					ShopCarActivity.this.finish();
			}
		});

		mShopFragment = ShopFragment.getInstance();

		//引用fragment替换当前fragment
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, mShopFragment).commit();
		
		
	}
	
	private void initData() {
		mTitleText.setText("购物车");
	}

}
