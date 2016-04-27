package com.zangcun.store.person;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.zangcun.store.BaseActivity;
import com.zangcun.store.R;
import com.zangcun.store.activity.DetailActivity;
import com.zangcun.store.adapter.CollectAdapter;
import com.zangcun.store.model.FxModel;
import com.zangcun.store.net.CommandBase;
import com.zangcun.store.other.Const;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的收藏
 * */
public class CollectActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ImageView mBack;
    private TextView mTitle;
    private TextView mRight;
    private GridView mGridView;
    private CollectAdapter mAdapter;
    private List<FxModel> mCollects = new ArrayList<>();
    private boolean isok = false;
    private LinearLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_collect);
        requestUi();
        init();
    }

    private void init() {
        mTitle = (TextView) findViewById(R.id.tv_personal_title);
        mTitle.setText("我的收藏");
        mRight = (TextView) findViewById(R.id.pesonal_right);
        mRight.setText("编辑");
        layout = (LinearLayout) findViewById(R.id.layout);
        mBack = (ImageView) findViewById(R.id.personal_back);
        mBack.setOnClickListener(this);
        mRight.setOnClickListener(this);
        mGridView = (GridView) findViewById(R.id.gv_collect);
        mAdapter = new CollectAdapter(this, mCollects);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);

//        if (mCollects!=null||mCollects.size()!=0){
//            layout.setVisibility(View.GONE);
//            mGridView.setVisibility(View.VISIBLE);
//        }else if (mCollects==null||mCollects.size()==0){
//            layout.setVisibility(View.VISIBLE);
//            mGridView.setVisibility(View.GONE);
//        }
    }

    private void requestUi() {
        CommandBase.requestDataNoGet(getApplicationContext(), Const.URL_COLLECT_LIEBIAO, handler, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_back:
                this.finish();
                break;
            case R.id.pesonal_right:
                mAdapter.viewDel();
                if (isok) {
                    this.isok = false;
                    mRight.setText("编辑");
                } else {
                    this.isok = true;
                    mRight.setText("完成");
                }
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Const.SUCCESS) {
                mCollects = JSON.parseArray(msg.obj.toString(), FxModel.class);
                mAdapter.chageView(mCollects);
            } else if (msg.what == Const.ERROR) {
                Toast.makeText(CollectActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(CollectActivity.this, DetailActivity.class);
        intent.putExtra("collect", mCollects.get(position));
        intent.putExtra("kind", "collect");
        startActivity(intent);
    }
}
