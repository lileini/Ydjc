package com.zangcun.store.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.zangcun.store.BaseActivity;
import com.zangcun.store.R;
import com.zangcun.store.net.CommandBase;
import com.zangcun.store.other.Const;
import com.zangcun.store.utils.DictionaryTool;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//获取注册验证码
public class RegisterActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    @ViewInject(R.id.phone)
    EditText phone;
    @ViewInject(R.id.cord)
    EditText cord;
    private Button checkPathBtn;
    private ImageView mBack;
    private TextView mTitle;
    private CountDownTimer mCountTimer;
    private Button btnSave;
    private String strPhone;
    private boolean isOnclick = true;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Const.SUCCESS) {
                if (isOnclick) {
                } else {
                    try {
                        JSONObject object = new JSONObject(msg.obj.toString());
                        String strToken = object.getString("token");
                        DictionaryTool.saveToken(getApplicationContext(),strToken);
                        Intent intent = new Intent(new Intent(RegisterActivity.this, RegisterCordActivity.class));
                        intent.putExtra("flag", "new");
                        startActivity(intent);

                    } catch (Exception e) {
                        //弹出提示框
                        Toast.makeText(getApplicationContext(), "成功了", Toast.LENGTH_SHORT).show();
                    }
                }
//
                //startActivity(new Intent(RegisterActivity.this, RegisterCordActivity.class));
            } else if (msg.what == Const.ERROR) {
                if (isOnclick) {
//                    Toast.makeText(getApplicationContext(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                    mCountTimer.cancel();
                    LayoutInflater inflaterDl = LayoutInflater.from(RegisterActivity.this);
                    RelativeLayout layout = (RelativeLayout)inflaterDl.inflate(R.layout.showdialog, null );
                    final Dialog dialog = new AlertDialog.Builder(RegisterActivity.this).create();
                    dialog.show();
                    dialog.getWindow().setContentView(layout);
                    TextView tv = (TextView) layout.findViewById(R.id.dialog_text);
                    tv.setText("请填写正确的手机号。");
                    Button btnCancel = (Button) layout.findViewById(R.id.dialog_sure);
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "失败了", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_register);
        ViewUtils.inject(this);
        init();
    }

    private void init() {
        checkPathBtn = (Button) findViewById(R.id.getcord);
        mBack = (ImageView) findViewById(R.id.personal_back);
        mTitle = (TextView) findViewById(R.id.tv_personal_title);
        btnSave = (Button) this.findViewById(R.id.savecord);
        mTitle.setText("手机验证");
        checkPathBtn.setOnClickListener(this);
        mBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        mCountTimer = new CountTimer(60000, 1000);
        phone.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getcord:
                isOnclick = true;
                mCountTimer.start();
                strPhone = phone.getText().toString().trim();
                Map<String, String> map = new HashMap<String, String>();
                map.put("phone", strPhone);
                CommandBase.requestDataMap(getApplicationContext(), Const.URL_SENDMESSGE, handler, map);
                break;
            case R.id.personal_back:
                finish();
                break;
            case R.id.savecord:
                isOnclick = false;
                savecord();
//                DictionaryTool.put(getApplicationContext(), "phone", phone.getText().toString().trim());
//                Intent intent = new Intent(new Intent(RegisterActivity.this, RegisterCordActivity.class));
//                intent.putExtra("flag", "new");
//                startActivity(intent);
                break;
        }
    }

    //计时
    public class CountTimer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public CountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            checkPathBtn.setText(millisUntilFinished / 1000 + "秒后发送");
            checkPathBtn.setBackgroundResource(R.drawable.dtk_btn_wl);
            checkPathBtn.setClickable(false);
        }

        @Override
        public void onFinish() {
            checkPathBtn.setText(R.string.getcord);
            checkPathBtn.setBackgroundResource(R.drawable.item_bg_getcord_selecotr);
            checkPathBtn.setClickable(true);

        }
    }

    private void savecord() {
        if (phone.getText().toString().trim().equals("")) {
//            Toast.makeText(getApplicationContext(), "请输入手机号码", Toast.LENGTH_SHORT).show();
            LayoutInflater inflaterDl = LayoutInflater.from(this);
            RelativeLayout layout = (RelativeLayout)inflaterDl.inflate(R.layout.showdialog, null );
            final Dialog dialog = new AlertDialog.Builder(this).create();
            dialog.show();
            dialog.getWindow().setContentView(layout);
            TextView tv = (TextView) layout.findViewById(R.id.dialog_text);
            tv.setText("请填写正确的手机号。");
            Button btnCancel = (Button) layout.findViewById(R.id.dialog_sure);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            return;

        } else if (cord.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("phone", strPhone);
        map.put("code", cord.getText().toString().trim());
        CommandBase.requestDataMap(getApplicationContext(), Const.URL_CODE, handler, map);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        mCountTimer.cancel();
        checkPathBtn.setText("发送验证码");
        checkPathBtn.setEnabled(true);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
