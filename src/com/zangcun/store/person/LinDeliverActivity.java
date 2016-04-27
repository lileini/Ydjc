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
import com.zangcun.store.BaseActivity;
import com.zangcun.store.R;
import com.zangcun.store.activity.DetailActivity;
import com.zangcun.store.activity.OrderActivity;
import com.zangcun.store.adapter.LinDeLiverAdapter;
import com.zangcun.store.net.CommandBase;
import com.zangcun.store.other.Const;

import java.util.ArrayList;
import java.util.List;

/**
 * 待发货
 * */
public class LinDeliverActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    private ImageView mBack;
    private TextView mTitle;

    private ListView mListView;
    private LinDeLiverAdapter mAdapter;
    private List<String> mDatas = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_deliver);
        init();
        requestData();
    }

    private void init() {
        mTitle = (TextView) findViewById(R.id.tv_personal_title);
        mTitle.setText("待发货");
        mBack = (ImageView) findViewById(R.id.personal_back);
        mBack.setOnClickListener(this);

        mListView= (ListView) findViewById(R.id.lv_deliver);
        mAdapter = new LinDeLiverAdapter(this, mDatas);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
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
        CommandBase.requestDataNoGet(getApplicationContext(), Const.URL_WAITING_FOR_SHIP, handler, null);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(LinDeliverActivity.this, OrderActivity.class);
//        intent.putExtra("deliver", mDatas.get(position));
//        intent.putExtra("kind", "deliver");
        startActivity(intent);
    }
}
