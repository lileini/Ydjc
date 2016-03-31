package com.zangcun.store.activity;

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
import com.zangcun.store.adapter.RefundAdapter;
import com.zangcun.store.net.CommandBase;
import com.zangcun.store.other.Const;

import java.util.HashMap;
import java.util.Map;

//申请退款
public class RefundActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mTitleImg;
    private TextView mTitle;

    private ListView mListView;
    private RefundAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_tuikuan_excel_one);
        init();
    }

    private void init() {
        mTitleImg = (ImageView) findViewById(R.id.personal_back);
        mTitle = (TextView) findViewById(R.id.tv_personal_title);
        mTitleImg.setOnClickListener(this);
        mTitle.setText("申请退款");

        mListView= (ListView) findViewById(R.id.refund_lv);
        mListView.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personal_back:
                finish();
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
