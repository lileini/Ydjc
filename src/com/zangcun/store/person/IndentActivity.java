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
import com.zangcun.store.adapter.IndentAdapter;
import com.zangcun.store.net.CommandBase;
import com.zangcun.store.other.Const;

import java.util.HashMap;
import java.util.Map;

//个人中心--全部订单
public class IndentActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBack;
    private TextView mTitle;

    private ListView mListView;
    private IndentAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_indent);
        init();
    }

    private void init() {
        mTitle = (TextView) findViewById(R.id.tv_personal_title);
        mTitle.setText("全部订单");
        mBack = (ImageView) findViewById(R.id.personal_back);
        mBack.setOnClickListener(this);

        mListView= (ListView) findViewById(R.id.lv_indent);
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
