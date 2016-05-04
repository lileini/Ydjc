package com.zangcun.store.person;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zangcun.store.BaseActivity;
import com.zangcun.store.R;
import com.zangcun.store.activity.DetailActivity;
import com.zangcun.store.activity.OrderActivity;
import com.zangcun.store.adapter.IndentAdapter;
import com.zangcun.store.adapter.LinPayAdapter;
import com.zangcun.store.entity.OrderResultEntity;
import com.zangcun.store.entity.UpDateOrder;
import com.zangcun.store.model.FxModel;
import com.zangcun.store.net.CommandBase;
import com.zangcun.store.net.Net;
import com.zangcun.store.other.Const;
import com.zangcun.store.other.MyApplication;
import com.zangcun.store.utils.*;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 待付款
 */
public class LinPayActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener,PullToRefreshBase.OnRefreshListener2 {
    private ImageView mBack;
    private TextView mTitle;
    private PullToRefreshListView mListView;
    private IndentAdapter mAdapter;
    private LinearLayout layout;
    public List<OrderResultEntity.OrderBean> mDataList;
    private int page = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_pay);
        init();
        requestData();
        EventBus.getDefault().register(this);
    }

    private void init() {
        mTitle = (TextView) findViewById(R.id.tv_personal_title);
        mTitle.setText("待付款");
        mBack = (ImageView) findViewById(R.id.personal_back);
        mBack.setOnClickListener(this);

        mListView = (PullToRefreshListView) findViewById(R.id.lv_pay);
        layout = (LinearLayout) findViewById(R.id.layout);
        /*mAdapter = new LinPayAdapter(this, mDatas);
        mListView.setAdapter(mAdapter);*/
        mListView.setOnItemClickListener(this);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView.setOnRefreshListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_back:
                this.finish();
                break;
        }
    }

    /**
     * 封装请求参数
     */
    private void requestData() {
        RequestParams params =new RequestParams(Net.URL_WAIT_FOR_PAY);
        params.addHeader("Authorization", DictionaryTool.getToken(this));

        HttpUtils.HttpGetMethod(new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "onSuccess = "+ s);
                mDataList = new Gson().fromJson(s, new TypeToken<List<OrderResultEntity.OrderBean>>() {
                }.getType());
                if (mDataList == null || mDataList.size() == 0 ){
                    layout.setVisibility(View.VISIBLE);
                    return;
                }
                Log.i(TAG,"mDataList.size() = "+mDataList.size());
                page = 2;
                layout.setVisibility(View.GONE);
                setAdapter();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i(TAG, "onError  = "+ throwable.toString());
                DialogUtil.dialogUser(LinPayActivity.this,"网络错误");
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {
                mListView.onRefreshComplete();
            }
        },params);
    }
    private void setAdapter() {
        if (mAdapter == null){
            mAdapter = new IndentAdapter(LinPayActivity.this,mDataList);
            mListView.setAdapter(mAdapter);
        }else {
            mAdapter.setDate(mDataList);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public void updateListView(UpDateOrder upDateOrder){
        requestData();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(LinPayActivity.this, OrderActivity.class);
//        intent.putExtra("linpay", mDatas.get(position));
//        intent.putExtra("kind", "linpay");
        startActivity(intent);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        requestData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        Log.i(TAG,"dateList.size() = "+mDataList.size());
        Log.i(TAG,"page = "+page);
        requestLoadMore();
    }

    private void requestLoadMore() {
        RequestParams params =new RequestParams(Net.URL_WAIT_FOR_PAY+"?page="+page);
        params.addHeader("Authorization", DictionaryTool.getToken(this));

        HttpUtils.HttpGetMethod(new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "onSuccess = "+ s);

                List<OrderResultEntity.OrderBean> dateList = new Gson().fromJson(s, new TypeToken<List<OrderResultEntity.OrderBean>>() {
                }.getType());
                if (dateList == null || dateList.size() == 0 ){
                    ToastUtils.show(MyApplication.instance,"没有更多数据了");
                    return;
                }
                Log.i(TAG,"dateList.size() = "+dateList.size());
                page += 2;
                mDataList.addAll(dateList);
                setAdapter();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i(TAG, "onError  = "+ throwable.toString());
                DialogUtil.dialogUser(LinPayActivity.this,"网络错误");
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {
                mListView.onRefreshComplete();
            }
        },params);
    }
}
