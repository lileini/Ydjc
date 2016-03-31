package com.zangcun.store;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.zangcun.store.widget.AnimSplashUtils;

//程序启动时的引导页
public class SplashActivity extends BaseActivity {

    private static final long DELAY_TIME = 3000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        redirectByTime();
    }

    private void redirectByTime() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MyActivity.class));
                AnimSplashUtils.activityZoomAnimation(SplashActivity.this);
                finish();

            }
        }, DELAY_TIME);
    }
}
