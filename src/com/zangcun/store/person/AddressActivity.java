package com.zangcun.store.person;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.zangcun.store.BaseActivity;
import com.zangcun.store.R;
import com.zangcun.store.adapter.AddressAdapter;
import com.zangcun.store.net.CommandBase;
import com.zangcun.store.other.Const;

import java.util.HashMap;
import java.util.Map;

//个人中心--收货地址
public class AddressActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBack;
    private TextView mTitle;
    private TextView mTitleRight;
    private LinearLayout mAddCity;

    private ListView mListView;
    private AddressAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_address);
        init();
    }

    private void init() {
        mTitle = (TextView) findViewById(R.id.tv_personal_title);
        mTitle.setText("收货地址");
        mTitleRight = (TextView) findViewById(R.id.pesonal_right);
        mTitleRight.setText("编辑");
        mBack = (ImageView) findViewById(R.id.personal_back);
        mBack.setOnClickListener(this);
        mAddCity = (LinearLayout) findViewById(R.id.add_address);
        mAddCity.setOnClickListener(this);

        mListView = (ListView) findViewById(R.id.lv_address);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_back:
                this.finish();
                break;
            case R.id.add_address:
                startActivity(new Intent(this, AddAddressActivity.class));
                break;
        }
    }

    /**
     * 封装请求参数
     */
    private void requestData() {
        Map<String, String> map = new HashMap<>();
        map.put("需要传递的key ", "需要传递的值");
        CommandBase.requestDataMap(getApplicationContext(), Const.URL_USER, handler, null);
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
