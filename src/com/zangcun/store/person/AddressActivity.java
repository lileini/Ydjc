package com.zangcun.store.person;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.zangcun.store.BaseActivity;
import com.zangcun.store.R;
import com.zangcun.store.adapter.AddressAdapter;
import com.zangcun.store.model.GetAddressResultModel;
import com.zangcun.store.net.CommandBase;
import com.zangcun.store.net.Net;
import com.zangcun.store.other.Const;
import com.zangcun.store.utils.DictionaryTool;
import com.zangcun.store.utils.GsonUtil;
import com.zangcun.store.utils.HttpUtils;
import com.zangcun.store.utils.Log;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

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
        initDate();
    }

    private void initDate() {
        requestAddress();
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
//        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_back:
                this.finish();
                break;
            case R.id.add_address:
                startActivityForResult(new Intent(this, AddAddressActivity.class),100);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode ==100){
            requestAddress();
        }
    }

    private void requestAddress() {
        RequestParams params = new RequestParams(Net.URL_GET_ADDRESSES);
        params.addHeader("Authorization", DictionaryTool.getToken(this));
        HttpUtils.HttpGetMethod(new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "onSuccess  = "+ s);
                if (TextUtils.isEmpty(s))
                    return;
                GetAddressResultModel result = GsonUtil.getResult(s, GetAddressResultModel.class);
                if (result.getAddress() == null)
                    return;
                if (mAdapter == null){
                    mAdapter = new AddressAdapter(AddressActivity.this,result.getAddress());
                    mListView.setAdapter(mAdapter);
                }else {
                    mAdapter.setmDataList(result.getAddress());
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i(TAG, "onError  = "+ throwable.toString());
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        },params);
    }
}
