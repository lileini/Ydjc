package com.zangcun.store.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.android.volley.VolleyError;
import com.zangcun.store.BaseActivity;
import com.zangcun.store.R;
import com.zangcun.store.adapter.FilterAdapter;
import com.zangcun.store.adapter.FxGridAdapter;
import com.zangcun.store.model.FxModel;
import com.zangcun.store.net.Http;
import com.zangcun.store.net.Net;
import com.zangcun.store.other.Const;
import com.zangcun.store.utils.ToastUtils;
import com.zangcun.store.widget.MultImageVIew;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// 佛像
public class FxActivity extends BaseActivity implements View.OnClickListener,
		Http.INetWork, AdapterView.OnItemClickListener {
	private static final int DEFAUT_FO_IV = 0;
	private static final int OPEN_FO_IV = 1;

	private int[] resIds = new int[] { R.drawable.icon_nor, R.drawable.icon_jx, R.drawable.icon_sx };

	private TextView mTvDef;
	private TextView mTvPrice;
	private TextView mTvNum;
	private TextView mTvChoose;
	private MultImageVIew mIvPrice;
	private MultImageVIew mIvNum;

	private GridView mGv;
	private PullToRefreshGridView mPullToRefreshGridView;
	private List<FxModel> mDefautDatas;

	private FxGridAdapter mAdapter;

	private int mIv1CurrState;
	private int mIv2CurrState;
	private PopupWindow mPopWindow;

	private Http mHttp;
	private ImageView mFxLeft;
	//香炉的种类
	private List<String> mXlData = new ArrayList<String>();
	//香品的种类
	private List<String> mXpData = new ArrayList<String>();
	private GridView xp;
	private GridView xl;
	private Button mTvCacle;
	private Button mTvSure;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sort_fx);
		init();
		initEvent();
		loadDatas();
	}

	private void init() {
		mTvDef = (TextView) findViewById(R.id.sort_fx_mr);
		mTvPrice = (TextView) findViewById(R.id.sort_fx_price);
		mTvNum = (TextView) findViewById(R.id.sort_fx_num);
		mTvChoose = (TextView) findViewById(R.id.root_choose);
		mIvPrice = (MultImageVIew) findViewById(R.id.fx_price_order);
		mIvNum = (MultImageVIew) findViewById(R.id.fx_num_order);
		// mGv = (GridView) findViewById(R.id.gv);
		mPullToRefreshGridView = (PullToRefreshGridView) findViewById(R.id.gv);
		mGv = mPullToRefreshGridView.getRefreshableView();
		mGv.setOnItemClickListener(this);
		mFxLeft = (ImageView) findViewById(R.id.fx_left);
	}

	private void initEvent() {
		mTvDef.setTextColor(getTextColor());
		mTvPrice.setTextColor(getTextColor());
		mTvNum.setTextColor(getTextColor());
		mTvChoose.setTextColor(getTextColor());
		mTvDef.setOnClickListener(this);
		mFxLeft.setOnClickListener(this);
		mTvChoose.setOnClickListener(this);
		mTvDef.setSelected(true);
		mPullToRefreshGridView.setMode(Mode.BOTH);
		mPullToRefreshGridView
				.setOnRefreshListener(new OnRefreshListener2<GridView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<GridView> refreshView) {
						//刷新
						if(mDefautDatas != null){
							mDefautDatas.clear();
						}
						mHttp.get(Net.URL_FX, FxActivity.this,  Const.REQUEST_FX);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<GridView> refreshView) {
						//加载更多
						loadMoreDatas();
					}

				});
		mIv1CurrState = DEFAUT_FO_IV;
		mIv2CurrState = DEFAUT_FO_IV;
		mIvPrice.setStateAndImg(MultImageVIew.DEFAUT, resIds[0]);
		mIvNum.setStateAndImg(MultImageVIew.DEFAUT, resIds[0]);
		this.findViewById(R.id.root_price).setOnClickListener(this);
		this.findViewById(R.id.root_num).setOnClickListener(this);
		this.findViewById(R.id.root_choose).setOnClickListener(this);
	}

	private void loadDatas() {
		if (mHttp == null) {
			mHttp = new Http(this);
		}
		//默认加载第一页
		mHttp.get(Net.URL_FX, this,Const.REQUEST_FX);

	}

	private void loadMoreDatas() {
		double page = (double) mDefautDatas.size() / 20;
		page += 1.9; // 因为服务器返回的可能会少于10条，所以采用小数进一法加载下一页
		
		mHttp.get(Net.URL_FX, this, (int) page,  Const.REQUEST_FX);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fx_left:
			finish();
			break;
		case R.id.sort_fx_mr:
			mTvDef.setSelected(true);
			mTvPrice.setSelected(false);
			mTvNum.setSelected(false);
			mTvChoose.setSelected(false);
			Collections.shuffle(mDefautDatas);//此处报空指针异常
			mAdapter.notifyDataSetChanged();
			break;
		case R.id.root_price:
			mTvDef.setSelected(false);
			mTvPrice.setSelected(true);
			mTvNum.setSelected(false);
			mTvChoose.setSelected(false);
			setIvStateAndResource(mIvPrice);
			break;
		case R.id.root_num:
			mTvDef.setSelected(false);
			mTvPrice.setSelected(false);
			mTvNum.setSelected(true);
			mTvChoose.setSelected(false);
			setIvStateAndResource(mIvNum);
			break;
		case R.id.root_choose:
			mTvDef.setSelected(false);
			mTvPrice.setSelected(false);
			mTvNum.setSelected(false);
			mTvChoose.setSelected(true);
			//筛选
			popupChoose();
			break;
			case R.id.tv_calce:
				mPopWindow.dismiss();
				break;
			case R.id.tv_sure:
				getSelectedChildView();
				break;
		}
	}

	private void popupChoose(){
		View contentView = LayoutInflater.from(this).inflate(R.layout.filter_layout, null);
		mPopWindow = new PopupWindow(contentView, ViewPager.LayoutParams.MATCH_PARENT, mGv.getHeight(),true);
		mPopWindow.setContentView(contentView);
		mPopWindow.setFocusable(true);
		mPopWindow.setOutsideTouchable(true);
		mPopWindow.setBackgroundDrawable(new BitmapDrawable());
		xp = (GridView) contentView.findViewById(R.id.filter_grid_1);
		xl = (GridView) contentView.findViewById(R.id.filter_grid_2);
		TextView title1 = (TextView) contentView.findViewById(R.id.filter_title1);
		TextView title2 = (TextView) contentView.findViewById(R.id.filter_title2);
		TextView tv1 = (TextView) contentView.findViewById(R.id.filter_tv1);
		TextView tv2 = (TextView) contentView.findViewById(R.id.filter_tv2);
		mTvCacle = (Button) contentView.findViewById(R.id.tv_calce);
		mTvSure = (Button) contentView.findViewById(R.id.tv_sure);
		mTvCacle.setOnClickListener(this);
		mTvSure.setOnClickListener(this);
		mTvChoose = (TextView) findViewById(R.id.root_choose);
		title1.setText("佛像");
		title2.setText("尺寸");
//		tv1.setVisibility(View.GONE);
//		tv2.setVisibility(View.GONE);
		//这里只有唐卡一个种类就隐藏另一个layout
//		LinearLayout layout = (LinearLayout) view.findViewById(R.id.filter_layout2);
//		layout.setVisibility(View.GONE);

		//模拟数据
		for (int i = 0; i < 15; i++) {
			mXlData.add(""+i);
			mXpData.add(""+i);
		}
		xl.setAdapter(new FilterAdapter(mXlData,this));
		xp.setAdapter(new FilterAdapter(mXpData,this));
		View rootview = LayoutInflater.from(this).inflate(R.layout.filter_layout, null);
		mPopWindow.showAtLocation(mTvChoose, Gravity.BOTTOM, 0, 0);
	}

	protected void getSelectedChildView() {
		for (int i = 0; i < xl.getChildCount(); i++) {
			if(xl.getChildAt(i).isSelected()){
				//获取
				Log.d("debug", mXlData.get(i));
			}
		}
		
		for (int i = 0; i < xp.getChildCount(); i++) {
			if(xp.getChildAt(i).isSelected()){
				//获取
				Log.d("debug", mXpData.get(i));
			}
		}
	}

	private void setIvStateAndResource(MultImageVIew vIew) {
		// 如果没有数据则无操作
		if (mDefautDatas == null) {
			return;
		}
		switch (vIew.getCurrState()) {
		case MultImageVIew.DEFAUT:
			vIew.setStateAndImg(MultImageVIew.HEIGHT_TO_LOW, resIds[1]);
			if (vIew == mIvPrice) {
				Collections.sort(mDefautDatas, new Comparator<FxModel>() {
					@Override
					public int compare(FxModel lhs, FxModel rhs) {
						return Float.parseFloat(lhs.getPrice()) > Float
								.parseFloat(rhs.getPrice()) ? 1 : -1;
					}
				});
			} else {
				Collections.sort(mDefautDatas, new Comparator<FxModel>() {
					@Override
					public int compare(FxModel lhs, FxModel rhs) {
						return lhs.getGoods_number() > rhs.getGoods_number() ? 1
								: -1;
					}
				});
			}
			mAdapter.notifyDataSetChanged();

			break;
		case MultImageVIew.HEIGHT_TO_LOW:
			vIew.setStateAndImg(MultImageVIew.LOW_TO_HEIGHT, resIds[2]);
			if (vIew == mIvPrice) {
				Collections.sort(mDefautDatas, new Comparator<FxModel>() {
					@Override
					public int compare(FxModel lhs, FxModel rhs) {
						return Float.parseFloat(lhs.getPrice()) > Float
								.parseFloat(rhs.getPrice()) ? -1 : 1;
					}
				});
			} else {
				Collections.sort(mDefautDatas, new Comparator<FxModel>() {
					@Override
					public int compare(FxModel lhs, FxModel rhs) {
						return lhs.getGoods_number() > rhs.getGoods_number() ? -1
								: 1;
					}
				});
			}
			mAdapter.notifyDataSetChanged();
			break;
		case MultImageVIew.LOW_TO_HEIGHT:
			vIew.setStateAndImg(MultImageVIew.DEFAUT, resIds[0]);
			Collections.shuffle(mDefautDatas);
			mAdapter.notifyDataSetChanged();

			break;
		}

	}

	private ColorStateList getTextColor() {
		return new ColorStateList(new int[][] {
				new int[] { android.R.attr.state_selected }, new int[0] },
				new int[] { 0xFFCD9207, 0xFF000000 });
	}

	@Override
	public void onNetSuccess(String response, int requestCode) {
		List<FxModel> responseData = Net.parseJsonList(response, FxModel.class);
		if (responseData == null) {
			ToastUtils.show(this, "数据解析失败");
			return;
		}
		// 第一次进入
		if (mDefautDatas == null) {
			mDefautDatas = responseData;
			mAdapter = new FxGridAdapter(this, mDefautDatas,
					R.layout.item_gv_layout);
			mGv.setAdapter(mAdapter);
		} else {
			mAdapter.addMoreData(responseData);
		}
		Log.d("debug", "data:"+mDefautDatas.toString());
		mPullToRefreshGridView.onRefreshComplete();
	}

	@Override
	public void onNetError(VolleyError error, int requestCode) {
		if (this != null) {
			ToastUtils.show(this, "网络连接失败");
			mPullToRefreshGridView.onRefreshComplete();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
		Intent intent = new Intent(FxActivity.this,DetailActivity.class);
		intent.putExtra("fx", (Serializable)mDefautDatas.get(position));
		intent.putExtra("kind", "fx");
		startActivity(intent);
	}
	
}
