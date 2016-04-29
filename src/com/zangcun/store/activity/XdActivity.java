package com.zangcun.store.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
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
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.zangcun.store.BaseActivity;
import com.zangcun.store.R;
import com.zangcun.store.adapter.FilterAdapter;
import com.zangcun.store.adapter.XdGridAdapter;
import com.zangcun.store.model.FxModel;
import com.zangcun.store.net.Http;
import com.zangcun.store.net.Net;
import com.zangcun.store.other.Const;
import com.zangcun.store.utils.DialogUtil;
import com.zangcun.store.utils.ToastUtils;
import com.zangcun.store.widget.MultImageVIew;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// 香道
public class XdActivity extends BaseActivity implements View.OnClickListener,
        Http.INetWork, OnItemClickListener {
    private static final int DEFAUT_FO_IV = 0;
    private int[] resIds = new int[]{R.drawable.icon_nor, R.drawable.icon_jx, R.drawable.icon_sx};

    private TextView mTvDef;
    private TextView mTvPrice;
    private TextView mTvNum;
    private MultImageVIew mIvPrice;
    private MultImageVIew mIvNum;
    private GridView mGv;
    private PullToRefreshGridView mPullToRefreshGridView;
    private List<FxModel> mDefautDatas;
    private List<FxModel> mDefautDatas1 = new ArrayList<>();

    private XdGridAdapter mAdapter;

    private int mIv1CurrState;
    private int mIv2CurrState;

    private Http mHttp;
    private ImageView mXdLeft;
    private GridView mFilterGrid1;
    private GridView mFilterGrid2;
    private List<String> mFilterData1 = new ArrayList<String>();
    private List<String> mFilterData2 = new ArrayList<String>();
    private LinearLayout mTvChoose;

    private Button mTvCacle;
    private Button mTvSure;
    private PopupWindow mPopWindow;
    private TextView mXdTvChoose;
    private ImageView mIvChoose;
    private boolean isChage = true;
    private int flag;
    private boolean action = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_xd);
        init();
        initEvent();
        loadDatas();
    }

    private void init() {
        mTvDef = (TextView) findViewById(R.id.sort_xd_mr);
        mTvPrice = (TextView) findViewById(R.id.sort_xd_price);
        mTvNum = (TextView) findViewById(R.id.sort_xd_num);
        mTvChoose = (LinearLayout) findViewById(R.id.root_choose);
        mXdTvChoose = (TextView) findViewById(R.id.xd_tv_choose);
        mIvChoose = (ImageView) findViewById(R.id.xd_choose_order);
        mIvPrice = (MultImageVIew) findViewById(R.id.xd_price_order);
        mIvNum = (MultImageVIew) findViewById(R.id.xd_num_order);
        mPullToRefreshGridView = (PullToRefreshGridView) findViewById(R.id.gv);
        mGv = mPullToRefreshGridView.getRefreshableView();
        mGv.setOnItemClickListener(this);
        mXdLeft = (ImageView) findViewById(R.id.xd_left);
    }

    private void initEvent() {
        mTvDef.setTextColor(getTextColor());
        mTvPrice.setTextColor(getTextColor());
        mTvNum.setTextColor(getTextColor());
        mXdTvChoose.setTextColor(getTextColor());
        mTvChoose.setOnClickListener(this);
        mTvDef.setOnClickListener(this);
        mXdLeft.setOnClickListener(this);
        mTvDef.setSelected(true);
        mIvPrice.setStateAndImg(MultImageVIew.DEFAUT, resIds[0]);
        mIvNum.setStateAndImg(MultImageVIew.DEFAUT, resIds[0]);
        mIvChoose.setBackgroundResource(R.drawable.icon_sx_nor);
        mIv1CurrState = DEFAUT_FO_IV;
        mIv2CurrState = DEFAUT_FO_IV;
        mPullToRefreshGridView.setMode(Mode.BOTH);
        mPullToRefreshGridView.setOnRefreshListener(new OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<GridView> refreshView) {
                if (mDefautDatas == null)
                    return;
                // 刷新
                if (mDefautDatas != null) {
                    mDefautDatas.clear();
                }
                isChage = false;
                mHttp.get(Net.URL_FSYP, XdActivity.this, Const.REQUEST_FSYP);
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<GridView> refreshView) {
                if (mDefautDatas == null)
                    return;
                isChage = false;
                loadMoreDatas();
            }

        });
        this.findViewById(R.id.root_price).setOnClickListener(this);
        this.findViewById(R.id.root_num).setOnClickListener(this);
        this.findViewById(R.id.root_choose).setOnClickListener(this);
        this.findViewById(R.id.sort_xd_mr).setOnClickListener(this);
    }

    private void loadDatas() {
        if (mHttp == null) {
            mHttp = new Http(this);
        }
        mHttp.get(Net.URL_FSYP, this, Const.REQUEST_FSYP);
    }

    private void loadMoreDatas() {
        double page = (double) mDefautDatas.size() / 10;
        page += 1.9;
        mHttp.get(Net.URL_FSYP, this, (int) page, Const.REQUEST_FSYP);
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.xd_left:
                    finish();
                    break;
                case R.id.sort_xd_mr:
                    isChage = true;
                    mTvDef.setSelected(true);
                    mTvPrice.setSelected(false);
                    mTvNum.setSelected(false);
                    mIvPrice.setStateAndImg(MultImageVIew.DEFAUT, resIds[0]);
                    mIvNum.setStateAndImg(MultImageVIew.DEFAUT, resIds[0]);
                    Collections.shuffle(mDefautDatas);
                    mAdapter.notifyDataSetChanged();
                    break;
                case R.id.root_price:
                    isChage = true;
                    action = true;
                    mTvDef.setSelected(false);
                    mTvPrice.setSelected(true);
                    mTvNum.setSelected(false);
                    mIvNum.setStateAndImg(MultImageVIew.DEFAUT, resIds[0]);
                    setIvStateAndResource(mIvPrice);
                    break;
                case R.id.root_num:
                    isChage = true;
                    action = false;
                    mTvDef.setSelected(false);
                    mTvPrice.setSelected(false);
                    mTvNum.setSelected(true);
                    mIvPrice.setStateAndImg(MultImageVIew.DEFAUT, resIds[0]);
                    setIvStateAndResource(mIvNum);
                    break;
                case R.id.root_choose:
                    mTvChoose.setSelected(true);
                    mIvChoose.setBackgroundResource(R.drawable.sx_icon);
                    popupChoose();
                    break;
                case R.id.tv_calce:
                    mPopWindow.dismiss();
                    mTvChoose.setSelected(false);
                    mIvChoose.setBackgroundResource(R.drawable.icon_sx_nor);
                    break;
                case R.id.tv_sure:
                    getSelectedChildView();
                    break;
            }
        } catch (Exception e) {
        }
    }

    private void popupChoose() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.filter_layout, null);
        mPopWindow = new PopupWindow(contentView, ViewPager.LayoutParams.MATCH_PARENT, mGv.getHeight(), true);
        mPopWindow.setContentView(contentView);
        mFilterGrid1 = (GridView) contentView.findViewById(R.id.filter_grid_1);
        mFilterGrid2 = (GridView) contentView.findViewById(R.id.filter_grid_2);
        TextView title1 = (TextView) contentView.findViewById(R.id.filter_title1);
        TextView title2 = (TextView) contentView.findViewById(R.id.filter_title2);
        TextView tv1 = (TextView) contentView.findViewById(R.id.filter_tv1);
        TextView tv2 = (TextView) contentView.findViewById(R.id.filter_tv2);
        mTvCacle = (Button) contentView.findViewById(R.id.tv_calce);
        mTvSure = (Button) contentView.findViewById(R.id.tv_sure);
        mTvCacle.setOnClickListener(this);
        mTvSure.setOnClickListener(this);
        mTvChoose = (LinearLayout) findViewById(R.id.root_choose);
        title1.setText("香品");
        title2.setText("香炉");

        //模拟数据
        for (int i = 0; i < 15; i++) {
            mFilterData1.add("" + i);
            mFilterData2.add("" + i);
        }
        mFilterGrid1.setAdapter(new FilterAdapter(mFilterData1, this));
        mFilterGrid2.setAdapter(new FilterAdapter(mFilterData2, this));
        View rootview = LayoutInflater.from(this).inflate(R.layout.filter_layout, null);
        mPopWindow.showAtLocation(mTvChoose, Gravity.BOTTOM, 0, 0);
    }

    protected void getSelectedChildView() {
        for (int i = 0; i < mFilterGrid1.getChildCount(); i++) {
            if (mFilterGrid1.getChildAt(i).isSelected()) {
                // 获取
                Log.d("debug", mFilterData1.get(i));
            }
        }

        for (int i = 0; i < mFilterGrid2.getChildCount(); i++) {
            if (mFilterGrid2.getChildAt(i).isSelected()) {
                // 获取
                Log.d("debug", mFilterData2.get(i));
            }
        }
    }

    private void setIvStateAndResource(MultImageVIew vIew) {
        if (!isChage) {
            if (flag == MultImageVIew.DEFAUT) {
                vIew.setStateAndImg(MultImageVIew.DEFAUT, resIds[2]);
            } else if (flag == MultImageVIew.HEIGHT_TO_LOW) {
                vIew.setStateAndImg(MultImageVIew.HEIGHT_TO_LOW, resIds[1]);
            }
        }
        // 如果没有数据则无操作
        if (mDefautDatas == null) {
            return;
        }
        switch (vIew.getCurrState()) {
            case MultImageVIew.DEFAUT:
                flag = MultImageVIew.DEFAUT;
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
                flag = MultImageVIew.HEIGHT_TO_LOW;
                vIew.setStateAndImg(MultImageVIew.DEFAUT, resIds[2]);
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
        return new ColorStateList(new int[][]{
                new int[]{android.R.attr.state_selected}, new int[0]},
                new int[]{0xFFAE9962, 0xFF000000});

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
            mDefautDatas1.addAll(mDefautDatas);
            mAdapter = new XdGridAdapter(this, mDefautDatas,
                    R.layout.item_gv_layout);
            mGv.setAdapter(mAdapter);
        } else {
            mDefautDatas.addAll(responseData);
            mAdapter.setData(mDefautDatas);
            if (action) {
                setIvStateAndResource(mIvPrice);
            } else {
                setIvStateAndResource(mIvNum);
            }
        }
        mPullToRefreshGridView.onRefreshComplete();
    }

    @Override
    public void onNetError(VolleyError error, int requestCode) {
        if (this != null) {
            DialogUtil.dialogUser(this,"网络连接错误");
            mPullToRefreshGridView.onRefreshComplete();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Intent intent = new Intent(XdActivity.this, DetailActivity.class);
        intent.putExtra("xd", (Serializable) mDefautDatas.get(position));
        intent.putExtra("kind", "xd");
        startActivity(intent);
    }
}
