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
import com.zangcun.store.BaseActivity;
import com.zangcun.store.R;
import com.zangcun.store.activity.DetailActivity;
import com.zangcun.store.activity.OrderActivity;
import com.zangcun.store.adapter.IndentAdapter;
import com.zangcun.store.adapter.LinDeLiverAdapter;
import com.zangcun.store.entity.OrderResultEntity;
import com.zangcun.store.net.CommandBase;
import com.zangcun.store.net.Net;
import com.zangcun.store.other.Const;
import com.zangcun.store.utils.DictionaryTool;
import com.zangcun.store.utils.HttpUtils;
import com.zangcun.store.utils.Log;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全部订单
 * */
public class IndentActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ImageView mBack;
    private TextView mTitle;

    private ListView mListView;
    private IndentAdapter mAdapter;
    private List<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_indent);
        init();
        initDate();
    }

    private void initDate() {
        RequestParams params = new RequestParams(Net.URL_CEAT_ORDER);
        params.addHeader("Authorization", DictionaryTool.getToken(this));
        HttpUtils.HttpGetMethod(new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "onSuccess = "+s);
                Gson gson = new Gson();
                List<OrderResultEntity> orderList = gson.fromJson(s, new TypeToken<List<OrderResultEntity>>() {
                }.getType());
                Log.i(TAG, "orderList = "+orderList.toString());
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        },params);
    }

    private void init() {
        mTitle = (TextView) findViewById(R.id.tv_personal_title);
        mTitle.setText("全部订单");
        mBack = (ImageView) findViewById(R.id.personal_back);
        mBack.setOnClickListener(this);

        mListView= (ListView) findViewById(R.id.lv_indent);
        mAdapter = new IndentAdapter(this, mDatas);
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

//    /**
//     * 封装请求参数
//     */
//    private void requestData() {
//        Map<String, String> map = new HashMap<>();
//        map.put("需要传递的key ", "需要传递的值");
//        CommandBase.requestDataMap(getApplicationContext(), Const.URL_USER, handler, null);
//    }

//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == Const.SUCCESS) {
//                //做逻辑处理
//            } else if (msg.what == Const.ERROR) {
//
//            }
//        }
//    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(IndentActivity.this, OrderActivity.class);
//        intent.putExtra("indent", mDatas.get(position));
//        intent.putExtra("kind", "indent");
        startActivity(intent);
    }
}
