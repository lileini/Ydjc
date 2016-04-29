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
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.zangcun.store.BaseActivity;
import com.zangcun.store.R;
import com.zangcun.store.adapter.FilterAdapter;
import com.zangcun.store.adapter.FxGridAdapter;
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

// 佛像
public class FxActivity extends BaseActivity implements View.OnClickListener, Http.INetWork, AdapterView.OnItemClickListener {
    private static final int DEFAUT_FO_IV = 0;

    private int[] resIds = new int[]{R.drawable.icon_nor, R.drawable.icon_jx, R.drawable.icon_sx};

    private TextView mTvDef;
    private TextView mTvPrice;
    private TextView mTvNum;
    private LinearLayout mTvChoose;
    private MultImageVIew mIvPrice;
    private MultImageVIew mIvNum;

    private GridView mGv;
    private PullToRefreshGridView mPullToRefreshGridView;
    private List<FxModel> mDefautDatas;
    private List<FxModel> mDefautDatas11=new ArrayList<>();
    private FxGridAdapter mAdapter;

    private int mIv1CurrState;
    private int mIv2CurrState;
    private PopupWindow mPopWindow;

    private Http mHttp;
    private ImageView mFxLeft;
    private List<String> mXlData = new ArrayList<String>();
    private List<String> mXpData = new ArrayList<String>();
    private GridView xp;
    private GridView xl;
    private Button mTvCacle;
    private Button mTvSure;
    private TextView mFxTvChoose;
    private ImageView mIvChoose;
    private boolean isChage=true;
    private int flag;
    private boolean action=true;

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
        mTvChoose = (LinearLayout) findViewById(R.id.root_choose);
        mFxTvChoose = (TextView) findViewById(R.id.fx_tv_choose);
        mIvChoose = (ImageView) findViewById(R.id.fx_choose_order);
        mIvPrice = (MultImageVIew) findViewById(R.id.fx_price_order);
        mIvNum = (MultImageVIew) findViewById(R.id.fx_num_order);
        mIvChoose.setBackgroundResource(R.drawable.icon_sx_nor);
        mPullToRefreshGridView = (PullToRefreshGridView) findViewById(R.id.gv);
        mGv = mPullToRefreshGridView.getRefreshableView();
        mGv.setOnItemClickListener(this);
        mFxLeft = (ImageView) findViewById(R.id.fx_left);
    }

    private void initEvent() {
        mTvDef.setTextColor(getTextColor());
        mTvPrice.setTextColor(getTextColor());
        mTvNum.setTextColor(getTextColor());
        mFxTvChoose.setTextColor(getTextColor());
        mTvDef.setOnClickListener(this);
        mFxLeft.setOnClickListener(this);
        mTvChoose.setOnClickListener(this);
        mTvDef.setSelected(true);
        mPullToRefreshGridView.setMode(Mode.BOTH);
        mIv1CurrState = DEFAUT_FO_IV;
        mIv2CurrState = DEFAUT_FO_IV;
        mIvPrice.setStateAndImg(MultImageVIew.DEFAUT, resIds[0]);
        mIvNum.setStateAndImg(MultImageVIew.DEFAUT, resIds[0]);
        mPullToRefreshGridView.setOnRefreshListener(new OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                //刷新
                if (mDefautDatas != null) {
                    mDefautDatas.clear();
                }
                isChage=false;
                mHttp.get(Net.URL_FX, FxActivity.this, Const.REQUEST_FX);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                if (mDefautDatas == null)
                    return;
                //加载更多
                isChage=false;
                loadMoreDatas();
            }
        });
        this.findViewById(R.id.root_price).setOnClickListener(this);
        this.findViewById(R.id.root_num).setOnClickListener(this);
        this.findViewById(R.id.root_choose).setOnClickListener(this);
    }

    private void loadDatas() {
        if (mHttp == null) {
            mHttp = new Http(this);
        }
        //默认加载第一页
        mHttp.get(Net.URL_FX, this, Const.REQUEST_FX);
    }

    private void loadMoreDatas() {
        double page = (double) mDefautDatas.size() / 20;
        page += 1.9;
        mHttp.get(Net.URL_FX, this, (int) page, Const.REQUEST_FX);
        Toast.makeText(getApplicationContext(),"adfadfa",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        try {
        switch (v.getId()) {
            case R.id.fx_left:
                finish();
                break;
            case R.id.sort_fx_mr:
                isChage=true;
                Toast.makeText(getApplicationContext(),"adfadfa",Toast.LENGTH_SHORT).show();
                mTvDef.setSelected(true);
                mTvPrice.setSelected(false);
                mTvNum.setSelected(false);
                mIvPrice.setStateAndImg(MultImageVIew.DEFAUT, resIds[0]);
                mIvNum.setStateAndImg(MultImageVIew.DEFAUT, resIds[0]);
                Log.e("fadf",mDefautDatas11.size()+"") ;
               // mAdapter.setData(mDefautDatas11);
                Collections.shuffle(mDefautDatas);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.root_price:
                isChage=true;
                action=true;
                mTvDef.setSelected(false);
                mTvPrice.setSelected(true);
                mTvNum.setSelected(false);
                mIvNum.setStateAndImg(MultImageVIew.DEFAUT, resIds[0]);
                setIvStateAndResource(mIvPrice);
                break;
            case R.id.root_num:
                isChage=true;
                action=false;
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
        }catch (Exception e){
          Log.e("FxActivity",e.toString());
        }
    }

    private void popupChoose() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.filter_layout, null);
        mPopWindow = new PopupWindow(contentView, ViewPager.LayoutParams.MATCH_PARENT, mGv.getHeight(), true);
        mPopWindow.setContentView(contentView);
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
        mTvChoose = (LinearLayout) findViewById(R.id.root_choose);
        title1.setText("佛像");
        title2.setText("尺寸");

        //模拟数据
        for (int i = 0; i < 15; i++) {
            mXlData.add("" + i);
            mXpData.add("" + i);
        }
        xl.setAdapter(new FilterAdapter(mXlData, this));
        xp.setAdapter(new FilterAdapter(mXpData, this));
        View rootview = LayoutInflater.from(this).inflate(R.layout.filter_layout, null);
        mPopWindow.showAtLocation(mTvChoose, Gravity.BOTTOM, 0, 0);
    }

    protected void getSelectedChildView() {
        for (int i = 0; i < xl.getChildCount(); i++) {
            if (xl.getChildAt(i).isSelected()) {
                //获取
                Log.d("debug", mXlData.get(i));
            }
        }

        for (int i = 0; i < xp.getChildCount(); i++) {
            if (xp.getChildAt(i).isSelected()) {
                //获取
                Log.d("debug", mXpData.get(i));
            }
        }
    }

    private void setIvStateAndResource(MultImageVIew vIew) {
        if(!isChage){
            if(flag==MultImageVIew.DEFAUT){
                vIew.setStateAndImg(MultImageVIew.DEFAUT, resIds[2]);
            }else if(flag==MultImageVIew.HEIGHT_TO_LOW){
                vIew.setStateAndImg(MultImageVIew.HEIGHT_TO_LOW, resIds[1]);
            }
        }
        // 如果没有数据则无操作
        if (mDefautDatas == null) {
            return;
        }
        switch (vIew.getCurrState()) {
            //升序
            case MultImageVIew.DEFAUT:
                flag=MultImageVIew.DEFAUT;
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
                            ToastUtils.show(FxActivity.this,"数量升序");
                            return lhs.getGoods_number() > rhs.getGoods_number() ? 2 : -2;
                        }
                    });
                }
                mAdapter.notifyDataSetChanged();
                break;
            //降序
            case MultImageVIew.HEIGHT_TO_LOW:
                flag=MultImageVIew.HEIGHT_TO_LOW;
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
                            ToastUtils.show(FxActivity.this,"数量降序");
                            return lhs.getGoods_number() > rhs.getGoods_number() ? -2 : 2;
                        }
                    });
                }
                mAdapter.notifyDataSetChanged();
                break;
            //默认加载
            case MultImageVIew.LOW_TO_HEIGHT:
                Toast.makeText(getApplicationContext(),"adfadfa",Toast.LENGTH_SHORT).show();
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
        Log.e("asdfa",responseData.size()+"");
        if (responseData == null) {
            ToastUtils.show(this, "数据解析失败");
            return;
        }
        // 第一次进入
        if (mDefautDatas == null) {
            mDefautDatas = responseData;
            mDefautDatas11.addAll(mDefautDatas);
            mAdapter = new FxGridAdapter(this, mDefautDatas, R.layout.item_gv_layout);
            mGv.setAdapter(mAdapter);
        } else {
            mDefautDatas.addAll(responseData);
            mAdapter.setData(mDefautDatas);
            if(action){
                setIvStateAndResource(mIvPrice);
            }else {
                setIvStateAndResource(mIvNum);
            }

        }
        Log.d("debug", "data:" + mDefautDatas.toString());
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(FxActivity.this, DetailActivity.class);
        intent.putExtra("fx", (Serializable) mDefautDatas.get(position));
        intent.putExtra("kind", "fx");
        startActivity(intent);
    }
}
