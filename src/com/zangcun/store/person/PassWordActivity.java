package com.zangcun.store.person;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.zangcun.store.BaseActivity;
import com.zangcun.store.MyActivity;
import com.zangcun.store.R;
import com.zangcun.store.net.CommandBase;
import com.zangcun.store.other.Const;

import java.util.HashMap;
import java.util.Map;

//个人中心--重置密码
public class PassWordActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBack;
    private TextView mTitle;

    private EditText mOld_password;
    private EditText mNewPassWord;
    private EditText mSureNewPassWord;
    private Button mBtnOver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_loginpassword);
        init();
    }

    private void init() {
        mTitle= (TextView) findViewById(R.id.tv_personal_title);
        mTitle.setText("重置密码");
        mBack = (ImageView) findViewById(R.id.personal_back);
        mBack.setOnClickListener(this);

        mOld_password= (EditText) findViewById(R.id.old_password);
        mNewPassWord = (EditText) findViewById(R.id.new_password);
        mSureNewPassWord= (EditText) findViewById(R.id.sure_new_password);
        mBtnOver= (Button) findViewById(R.id.btn_over);
        mBtnOver.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_back:
                this.finish();
                break;
            case R.id.btn_over:
                requestData();
                break;
        }
    }


    private void requestData() {
        Map<String, String> map = new HashMap<>();
        map.put("old_password", mOld_password.getText().toString().trim());
        map.put("password", mNewPassWord.getText().toString());
        CommandBase.requestDataMapPut(getApplicationContext(), Const.URL_RESET_PASSWORD, handler, map);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Const.SUCCESS) {
                Intent intent = new Intent(getApplicationContext(), MyActivity.class);
                intent.putExtra("getCord", "get");
                startActivity(intent);
            } else if (msg.what == Const.ERROR) {

            }
        }
    };
}
