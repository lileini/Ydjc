package com.zangcun.store.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.zangcun.store.BaseActivity;
import com.zangcun.store.MyActivity;
import com.zangcun.store.R;
import com.zangcun.store.entity.RegisterEntity;
import com.zangcun.store.fragment.UserFragment;
import com.zangcun.store.other.Const;
import com.zangcun.store.utils.*;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
//设置注册密码
public class RegisterCordActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    private TextView mTitle;
    private Button mSaveCord;
    private EditText mPhone;
    private UserFragment.ILoginClick listener;
    private String flag;
    private static final String TAG= "RegisterCordActivity";
    private String phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_register_zhuce);
        init();
    }

    private void init() {
        mBack = (ImageView) findViewById(R.id.personal_back);
        mTitle = (TextView) findViewById(R.id.tv_personal_title);
        mPhone = (EditText) this.findViewById(R.id.phone);
        mTitle.setText("设置密码");
        mBack.setOnClickListener(this);
        mSaveCord = (Button) findViewById(R.id.savecord);
        mSaveCord.setOnClickListener(this);
        Intent intent = getIntent();
        flag = intent.getStringExtra("flag");
        if(flag.equals("get")){
            mSaveCord.setText("重置");
        }else if (flag.equals("new")){
            mSaveCord.setText("注册");
        }
        phone = intent.getStringExtra("phone");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_back:
                finish();
                break;
            case R.id.savecord:
                if (ClickUtil.isFastDoubleClick()){
                    return;
                }
                //点击注册直接跳转到PersonalFragment
                String s = mPhone.getText().toString();
                if (TextUtils.isEmpty(s)){
                    DialogUtil.dialogUser(this,"密码不能为空");
                    return;
                }
                requestData();
//                if (listener != null)
//                    listener.onLoginClick("个人中心");
                /*Intent intent = new Intent(getApplicationContext(), MyActivity.class);
                intent.putExtra("falg","zhuci");
                startActivity(intent);*/
                break;
        }
    }

    /**
     * 封装请求参数
     */
    private void requestData() {
        RequestParams params = new RequestParams(Const.URL_USER);
        String token = DictionaryTool.getToken(getApplicationContext());
        if (TextUtils.isEmpty(token)){
            // TODO: 2016/3/31 重新请求token存储
            return;
        }
        params.addBodyParameter("password",mPhone.getText().toString());
        Log.i(TAG,"token = "+token);
        params.addHeader("AUTHORIZATION", token);
        HttpUtils.HttpPostMethod(new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "onSuccess = "+ s.toString());
                RegisterEntity registerEntity = GsonUtil.getResult(s, RegisterEntity.class);
                DictionaryTool.savePWD(getApplicationContext(),mPhone.getText().toString());
                DictionaryTool.saveUser(getApplicationContext(),registerEntity.getUser().getUser_name());
                Intent intent = new Intent(getApplicationContext(), MyActivity.class);
                intent.putExtra("falg","zhuci");

                startActivity(intent);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i(TAG, "onError = "+throwable.toString());
                String s = throwable.toString();
                if (s.contains("该手机号已注册")){
                    DialogUtil.dialogUser(getApplication(),"该手机已经注册");
                }else {
                    DialogUtil.dialogUser(getApplication(),"注册失败");
                }
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        },params);
        /*Map<String, String> map = new HashMap<>();
        map.put("password ", mPhone.getText().toString());
        map.put();
        CommandBase.requestDataMap(getApplicationContext(), Const.URL_USER, handler, map);*/
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Const.SUCCESS) {
                Intent intent = new Intent(getApplicationContext(), MyActivity.class);
                intent.putExtra("falg","zhuci");
                startActivity(intent);
            } else if (msg.what == Const.ERROR) {

            }
        }
    };
}
