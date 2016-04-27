package com.zangcun.store.person;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
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
public class AddressActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ImageView mBack;
    private TextView mTitle;
    private TextView mTitleRight;
    private LinearLayout mAddCity;

    private ListView mListView;
    private AddressAdapter mAdapter;
    private boolean isok = false;

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
        mTitleRight.setOnClickListener(this);
        mBack = (ImageView) findViewById(R.id.personal_back);
        mBack.setOnClickListener(this);
        mAddCity = (LinearLayout) findViewById(R.id.add_address);
        mAddCity.setOnClickListener(this);

        mListView = (ListView) findViewById(R.id.lv_address);
        if (getIntent().getBooleanExtra("isChooseAddress", false))
            mListView.setOnItemClickListener(this);
//        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_back:
                this.finish();
                break;
            case R.id.add_address:
                startActivityForResult(new Intent(this, AddAddressActivity.class), 100);
                break;
            case R.id.pesonal_right:
                mAdapter.viewDel();
                if (isok) {
                    this.isok = false;
                    mTitleRight.setText("编辑");
                } else {
                    this.isok = true;
                    mTitleRight.setText("完成");
                }
                break;
        }
    }


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 100) {
            requestAddress();
        }
        if (requestCode == 101 && resultCode == 101) {
            requestAddress();
        }
    }

    public void requestAddress() {
        RequestParams params = new RequestParams(Net.URL_GET_ADDRESSES);
        params.addHeader("Authorization", DictionaryTool.getToken(this));
        HttpUtils.HttpGetMethod(new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "onSuccess  = " + s);
                if (TextUtils.isEmpty(s))
                    return;
                GetAddressResultModel result = GsonUtil.getResult(s, GetAddressResultModel.class);
                if (result.getAddress() == null)
                    return;
                if (mAdapter == null) {
                    mAdapter = new AddressAdapter(AddressActivity.this, result.getAddress());
                    mListView.setAdapter(mAdapter);
                } else {
                    mAdapter.setmDataList(result.getAddress());
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i(TAG, "onError  = " + throwable.toString());
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        }, params);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GetAddressResultModel.AddressBean addressBean = mAdapter.getOnclickDate(position);
        if (addressBean != null) {
            Intent intent = new Intent();
            intent.putExtra("addressBean", addressBean);
            setResult(200, intent);
            finish();
        }
    }
}
