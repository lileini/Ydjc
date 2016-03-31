package com.zangcun.store.person;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.zangcun.store.BaseActivity;
import com.zangcun.store.R;
import com.zangcun.store.adapter.LinPayAdapter;
import com.zangcun.store.net.CommandBase;
import com.zangcun.store.other.Const;

import java.util.HashMap;
import java.util.Map;

//个人中心--待付款
public class LinPayActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBack;
    private TextView mTitle;

    private ListView mListView;
    private LinPayAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_pay);
        init();
    }

    private void init() {
        mTitle = (TextView) findViewById(R.id.tv_personal_title);
        mTitle.setText("待付款");
        mBack = (ImageView) findViewById(R.id.personal_back);
        mBack.setOnClickListener(this);

        mListView= (ListView) findViewById(R.id.lv_pay);
        mListView.setAdapter(mAdapter);
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
        Map<String, String> map = new HashMap<>();
//        map.put("需要传递的key ", "需要传递的值");
        CommandBase.requestDataNoGet(getApplicationContext(), Const.URL_WAITING_FOR_PAY, handler, null);
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
