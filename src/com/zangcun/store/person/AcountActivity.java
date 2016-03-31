package com.zangcun.store.person;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import com.zangcun.store.BaseActivity;
import com.zangcun.store.R;

import java.util.HashMap;

//个人中心--第三方账号绑定
public class AcountActivity extends BaseActivity implements View.OnClickListener, PlatformActionListener {
    private ImageView mBack;
    private TextView mTitle;

    private LinearLayout mXinLang;
    private LinearLayout mWeiXin;
    private LinearLayout mQQ;
    private TextView mCancleXL;
    private TextView mCancleWX;
    private TextView mCancleQQ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_acount);
        init();
    }

    private void init() {
        mTitle = (TextView) findViewById(R.id.tv_personal_title);
        mTitle.setText("第三方账号");
        mBack = (ImageView) findViewById(R.id.personal_back);
        mBack.setOnClickListener(this);

        mXinLang = (LinearLayout) findViewById(R.id.connetion_xinlang);
        mWeiXin = (LinearLayout) findViewById(R.id.connetion_qq);
        mQQ = (LinearLayout) findViewById(R.id.connetion_weixin);
        mCancleXL= (TextView) findViewById(R.id.cancle_xinlang);
        mCancleXL= (TextView) findViewById(R.id.cancle_weixin);
        mCancleXL= (TextView) findViewById(R.id.cancle_qq);

        mXinLang.setOnClickListener(this);
        mWeiXin.setOnClickListener(this);
        mQQ.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_back:
                this.finish();
                break;
            case R.id.connetion_xinlang:
                loginXinLang();
                break;
            case R.id.connetion_weixin:
                loginweiXin();
                break;
            case R.id.connetion_qq:
                loginQQ();
//                mCancleQQ.setVisibility(View.VISIBLE);
        }
    }

    private void loginSuccess(String uname) {
//        Intent intent = new Intent(this, SplashActivity.class);
//        intent.putExtra("login_name", uname);
    }

    private void loginXinLang() {
        Platform platform = ShareSDK.getPlatform(this, SinaWeibo.NAME);
        platform.setPlatformActionListener(this);
        if (platform.isValid()) {
            String uname = platform.getDb().getUserName();
            System.out.print("通过--------------" + uname);
            loginSuccess(uname);
        } else {
            platform.showUser(null);
        }
    }

    private void loginQQ() {
        Platform platform = ShareSDK.getPlatform(this, QQ.NAME);
        platform.setPlatformActionListener(this);
        if (platform.isValid()) {
            String uname = platform.getDb().getUserName();
            System.out.print("通过--------------" + uname);
            loginSuccess(uname);
        } else {
            platform.showUser(null);
        }
    }

    private void loginweiXin() {
        Platform platform = ShareSDK.getPlatform(this, Wechat.NAME);
        platform.setPlatformActionListener(this);
        if (platform.isValid()) {
            String uname = platform.getDb().getUserName();
            System.out.print("通过--------------" + uname);
            loginSuccess(uname);
        } else {
            platform.showUser(null);
        }

    }


    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        String uname = platform.getDb().getUserName();
        System.out.print("uname-----------------" + uname);
        loginSuccess(uname);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Toast.makeText(this, platform.getName() + "授权失败,请重试", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Toast.makeText(this, platform.getName() + "授权取消", Toast.LENGTH_SHORT).show();
    }
}
