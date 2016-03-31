package com.zangcun.store.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.zangcun.store.BaseActivity;
import com.zangcun.store.MyActivity;
import com.zangcun.store.R;
import com.zangcun.store.net.CommandBase;
import com.zangcun.store.other.Const;

import java.util.HashMap;
import java.util.Map;

//找回密码
public class GetCordActivity extends BaseActivity implements View.OnClickListener {
    private Button checkPathBtn;
    private ImageView mBack;
    private TextView mTitile;
    private TextView user_phone;
    private CountDownTimer mCountTimer;
    private Button yanzheng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_getcord);
        init();
    }

    private void init() {
        checkPathBtn = (Button) findViewById(R.id.getcord);
        mBack = (ImageView) findViewById(R.id.personal_back);
        mTitile = (TextView) findViewById(R.id.tv_personal_title);
        user_phone = (TextView) findViewById(R.id.user_phone);
        yanzheng = (Button) findViewById(R.id.yanzheng);
        yanzheng.setOnClickListener(this);
        mTitile.setText("找回密码");
        checkPathBtn.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mCountTimer = new CountTimer(60000, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getcord:
                mCountTimer.start();
                requestData();
                break;
            case R.id.personal_back:
                finish();
                break;
            case R.id.yanzheng:
//               yanZheng();
                //验证之前需要判断用户输入的信息是否为空
                if (user_phone.getText().toString().trim().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"请输入有效的电话号码", Toast.LENGTH_SHORT).show();
                }else{
                    requestData();
                }


                break;
        }
    }

    //每隔30秒可点击一次验证码
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

    /**
     * 封装请求参数
     */
    private void requestData() {
        Map<String, String> map = new HashMap<>();
        map.put("password  ", user_phone.getText().toString().trim());
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

    private void yanZheng() {
        requestData();
        Intent intent = new Intent(getApplicationContext(), RegisterCordActivity.class);
        intent.putExtra("flag", "get");
        startActivity(intent);
    }
}
