package com.zangcun.store.person;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.zangcun.store.BaseActivity;
import com.zangcun.store.R;

//个人中心--关于我们
public class AboutActivity extends BaseActivity {
    private ImageView mBack;
    private TextView mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_about);
        mTitle = (TextView) findViewById(R.id.tv_personal_title);
        mTitle.setText("关于我们");
        mBack = (ImageView) findViewById(R.id.personal_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.personal_back:
                        finish();
                        break;
                }
            }
        });
    }
}
