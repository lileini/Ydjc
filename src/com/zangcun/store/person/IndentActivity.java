package com.zangcun.store.person;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zangcun.store.BaseActivity;
import com.zangcun.store.R;
import com.zangcun.store.activity.OrderActivity;
import com.zangcun.store.adapter.IndentAdapter;
import com.zangcun.store.entity.OrderResultEntity;
import com.zangcun.store.entity.UpDateOrder;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全部订单
 * */
public class IndentActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener,PullToRefreshBase.OnRefreshListener2 {
    private ImageView mBack;
    private TextView mTitle;

    private PullToRefreshListView mListView;
    private IndentAdapter mAdapter;
    private List<OrderResultEntity.OrderBean> mDataList;
    private int page;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_indent);
        init();
        initDate();
        EventBus.getDefault().register(this);

    }

    public void initDate() {
        RequestParams params =new RequestParams(Net.URL_CEAT_ORDER);
        params.addHeader("Authorization", DictionaryTool.getToken(this));

        HttpUtils.HttpGetMethod(new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "onSuccess = "+ s);
                mDataList = new Gson().fromJson(s, new TypeToken<List<OrderResultEntity.OrderBean>>() {
                }.getType());
                if (mDataList == null || mDataList.size() == 0 )
                    return;
                Log.i(TAG,"mDataList.size() = "+mDataList.size());
                page = 2;
                setAdapter();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i(TAG, "onError  = "+ throwable.toString());
                DialogUtil.dialogUser(IndentActivity.this,"网络错误");
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
            mAdapter = new IndentAdapter(IndentActivity.this,mDataList);
            mListView.setAdapter(mAdapter);
        }else {
            mAdapter.setDate(mDataList);
        }
    }

    private void init() {
        mTitle = (TextView) findViewById(R.id.tv_personal_title);
        mTitle.setText("全部订单");
        mBack = (ImageView) findViewById(R.id.personal_back);
        mBack.setOnClickListener(this);

        mListView= (PullToRefreshListView) findViewById(R.id.lv_indent);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public void updateListView(UpDateOrder upDateOrder){
        initDate();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == 100){//付款成功 重新刷新数据
            initDate();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG,"onItemClick");
        Intent  intent = new Intent(this,OrderActivity.class);
        intent.putExtra("order_id",mDataList.get(position).getOrder_id());
        intent.putExtra("orderDetail",true);
        startActivityForResult(intent,100);
    }



    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        initDate();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        Log.i(TAG,"dateList.size() = "+mDataList.size());
        Log.i(TAG,"page = "+page);
        requestLoadMore();
    }

    private void requestLoadMore() {
        RequestParams params =new RequestParams(Net.URL_CEAT_ORDER+"?page="+page);
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
                DialogUtil.dialogUser(IndentActivity.this,"网络错误");
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
