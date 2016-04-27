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
import com.zangcun.store.model.IndentModel;
import com.zangcun.store.net.CommandBase;
import com.zangcun.store.net.Net;
import com.zangcun.store.other.Const;
import com.zangcun.store.utils.HttpUtils;
import com.zangcun.store.utils.Log;
import com.zangcun.store.utils.ToastUtils;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.util.HashMap;
import java.util.List;
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
        initDate();

    }

    private void initDate() {
        RequestParams params =new RequestParams(Net.HOST);
        HttpUtils.HttpGetMethod(new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "onSuccess = "+ s);
                List<IndentModel> mDataList = null;
                if (mDataList == null || mDataList.size() == 0 )
                    return;
                if (mAdapter == null){
                    mAdapter = new IndentAdapter(IndentActivity.this,mDataList);
                    mListView.setAdapter(mAdapter);
                }else {
                    mAdapter.setDate(mDataList);
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i(TAG, "onError  = "+ throwable.toString());
                ToastUtils.show(IndentActivity.this,"网络错误");
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
