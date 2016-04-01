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
import android.widget.AdapterView.OnItemClickListener;
import com.android.volley.VolleyError;
import com.zangcun.store.BaseActivity;
import com.zangcun.store.R;
import com.zangcun.store.adapter.FilterAdapter;
import com.zangcun.store.adapter.FsypGridAdapter;
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

//佛事用品
public class FsypActivity extends BaseActivity implements View.OnClickListener, Http.INetWork ,OnItemClickListener {
    private static final int DEFAUT_FO_IV = 0;
    private static final int OPEN_FO_IV = 1;

    private int[] resIds = new int[]{R.drawable.icon_nor, R.drawable.icon_jx, R.drawable.icon_sx};

    private TextView mTvDef;
    private TextView mTvPrice;
    private TextView mTvNum;
    private MultImageVIew mIvPrice;
    private MultImageVIew mIvNum;

    private GridView mGv;
    private PullToRefreshGridView mPullToRefreshGridView;
    private List<FxModel> mDefautDatas;

    private FsypGridAdapter mAdapter;

    private int mIv1CurrState;
    private int mIv2CurrState;

    private Http mHttp;
    private ImageView mFsypLeft;
	private GridView mFilterGrid1;
	private GridView mFilterGrid2;
	private List<String> mFilterData1= new ArrayList<String>();
	private List<String> mFilterData2= new ArrayList<String>();
	private TextView mTvChoose;

    private Button mTvCacle;
    private Button mTvSure;
    private PopupWindow mPopWindow;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_fsyp);
        init();
        initEvent();
        loadDatas();
    }

    private void init() {
        mTvDef = (TextView) findViewById(R.id.sort_fsyp_mr);
        mTvPrice = (TextView) findViewById(R.id.sort_fsyp_price);
        mTvNum = (TextView) findViewById(R.id.sort_fsyp_num);
        mTvChoose = (TextView) findViewById(R.id.root_choose);
        mIvPrice = (MultImageVIew) findViewById(R.id.fsyp_price_order);
        mIvNum = (MultImageVIew) findViewById(R.id.fsyp_num_order);
        mPullToRefreshGridView = (PullToRefreshGridView) findViewById(R.id.gv);
        mGv =mPullToRefreshGridView.getRefreshableView();
        mGv.setOnItemClickListener(this);
        mPullToRefreshGridView.setMode(Mode.BOTH);
        mPullToRefreshGridView.setOnRefreshListener(new OnRefreshListener2<GridView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
				//刷新
				if(mDefautDatas != null){
					mDefautDatas.clear();
				}
				 mHttp.get(Net.URL_FSYP, FsypActivity.this,  Const.REQUEST_FSYP);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
				loadMoreDatas();
			}

		});
        mFsypLeft = (ImageView) findViewById(R.id.fsyp_left);
    }

    private void initEvent() {
        mTvDef.setTextColor(getTextColor());
        mTvPrice.setTextColor(getTextColor());
        mTvNum.setTextColor(getTextColor());
        mTvChoose.setTextColor(getTextColor());
		mTvChoose.setOnClickListener(this);
        mTvDef.setOnClickListener(this);
        mFsypLeft.setOnClickListener(this);
        mTvDef.setSelected(true);
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

        mHttp.get(Net.URL_FSYP, this,  Const.REQUEST_FSYP);

    }
    private void loadMoreDatas() {
		double page = (double) mDefautDatas.size() / 10;
		page += 1.9; // 因为服务器返回的可能会少于10条，所以采用小数进一法加载下一页
		mHttp.get(Net.URL_FSYP, this, (int) page, Const.REQUEST_FSYP);
	}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fsyp_left:
                finish();
                break;
            case R.id.sort_fsyp_mr:
                mTvDef.setSelected(true);
                mTvPrice.setSelected(false);
                mTvNum.setSelected(false);
                mTvChoose.setSelected(false);
                Collections.shuffle(mDefautDatas);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.root_price:
                mTvDef.setSelected(false);
                mTvPrice.setSelected(true);
                mTvNum.setSelected(false);
                mTvChoose.setSelected(false);
                setIvStateAndResource(mIvPrice);
                break;
            case R.id.root_choose:
            	 mTvDef.setSelected(false);
                 mTvPrice.setSelected(false);
                 mTvNum.setSelected(false);
                 mTvChoose.setSelected(true);
                popupChoose();
            	 break;
            case R.id.root_num:
                mTvDef.setSelected(false);
                mTvPrice.setSelected(false);
                mTvNum.setSelected(true);
                mTvChoose.setSelected(false);
                setIvStateAndResource(mIvNum);
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
        mFilterGrid1 = (GridView) contentView.findViewById(R.id.filter_grid_1);
        mFilterGrid2 = (GridView) contentView.findViewById(R.id.filter_grid_2);
        TextView title1 = (TextView) contentView.findViewById(R.id.filter_title1);
        TextView title2 = (TextView) contentView.findViewById(R.id.filter_title2);
        TextView tv1 = (TextView) contentView.findViewById(R.id.filter_tv1);
        TextView tv2 = (TextView) contentView.findViewById(R.id.filter_tv2);
        LinearLayout layout2 = (LinearLayout) contentView.findViewById(R.id.title_layout2);
        mTvCacle = (Button) contentView.findViewById(R.id.tv_calce);
        mTvSure = (Button) contentView.findViewById(R.id.tv_sure);
        mTvCacle.setOnClickListener(this);
        mTvSure.setOnClickListener(this);
        mTvChoose = (TextView) findViewById(R.id.root_choose);
        title1.setText("佛事用品");
//        title2.setText("材料");
//		tv1.setVisibility(View.GONE);
		tv2.setVisibility(View.GONE);
        layout2.setVisibility(View.GONE);
//        这里只有唐卡一个种类就隐藏另一个layout
		LinearLayout layout = (LinearLayout) contentView.findViewById(R.id.filter_layout2);
		layout.setVisibility(View.GONE);

        //模拟数据
        for (int i = 0; i < 15; i++) {
            mFilterData1.add(""+i);
            mFilterData2.add(""+i);
        }
        mFilterGrid1.setAdapter(new FilterAdapter(mFilterData1,this));
        mFilterGrid2.setAdapter(new FilterAdapter(mFilterData2,this));
        View rootview = LayoutInflater.from(this).inflate(R.layout.filter_layout, null);
        mPopWindow.showAtLocation(mTvChoose, Gravity.BOTTOM, 0, 0);
    }

	protected void getSelectedChildView() {
		for (int i = 0; i < mFilterGrid1.getChildCount(); i++) {
			if(mFilterGrid1.getChildAt(i).isSelected()){
				//获取
				Log.d("debug", mFilterData1.get(i));
			}
		}
		
		for (int i = 0; i < mFilterGrid2.getChildCount(); i++) {
			if(mFilterGrid2.getChildAt(i).isSelected()){
				//获取
				Log.d("debug", mFilterData2.get(i));
			}
		}
	}

    private void setIvStateAndResource(MultImageVIew vIew) {
    	//如果没有数据则无操作
    	 if(mDefautDatas==null){
         	return;
         }
        switch (vIew.getCurrState()) {
            case MultImageVIew.DEFAUT:
                vIew.setStateAndImg(MultImageVIew.HEIGHT_TO_LOW, resIds[1]);
                if (vIew == mIvPrice) {
                    Collections.sort(mDefautDatas, new Comparator<FxModel>() {
                        @Override
                        public int compare(FxModel lhs, FxModel rhs) {
                            return Float.parseFloat(lhs.getPrice()) > Float.parseFloat(rhs.getPrice()) ? 1 : -1;
                        }
                    });
                } else {
                    Collections.sort(mDefautDatas, new Comparator<FxModel>() {
                        @Override
                        public int compare(FxModel lhs, FxModel rhs) {
                            return lhs.getGoods_number() > rhs.getGoods_number() ? 1 : -1;
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
                            return Float.parseFloat(lhs.getPrice()) > Float.parseFloat(rhs.getPrice()) ? -1 : 1;
                        }
                    });
                } else {
                    Collections.sort(mDefautDatas, new Comparator<FxModel>() {
                        @Override
                        public int compare(FxModel lhs, FxModel rhs) {
                            return lhs.getGoods_number() > rhs.getGoods_number() ? -1 : 1;
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
        return new ColorStateList(new int[][]{new int[]{android.R.attr.state_selected}, new int[0]
        }, new int[]{0xFFCD9207, 0xFF000000});

    }

    @Override
    public void onNetSuccess(String response, int requestCode) {
        Log.i(TAG, "onNetSuccess = "+response);

        List<FxModel> responseData = Net.parseJsonList(response, FxModel.class);
		if (responseData == null) {
			ToastUtils.show(this, "数据解析失败");
			return;
		}
		// 第一次进入
		if (mDefautDatas == null) {
			mDefautDatas = responseData;
			mAdapter = new FsypGridAdapter(this, mDefautDatas,
					R.layout.item_gv_layout);
			mGv.setAdapter(mAdapter);
		} else {
			mAdapter.addMoreData(responseData);
		}
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
		Intent intent = new Intent(FsypActivity.this,DetailActivity.class);
		intent.putExtra("fsyp", (Serializable)mDefautDatas.get(position));
		intent.putExtra("kind", "fsyp");
		startActivity(intent);		
	}
}
