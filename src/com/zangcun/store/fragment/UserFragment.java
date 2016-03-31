package com.zangcun.store.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import com.google.gson.JsonObject;
import com.zangcun.store.MyActivity;
import com.zangcun.store.R;
import com.zangcun.store.activity.GetCordActivity;
import com.zangcun.store.activity.RegisterActivity;
import com.zangcun.store.other.Const;
import com.zangcun.store.utils.DictionaryTool;
import com.zangcun.store.utils.HttpUtils;
import com.zangcun.store.utils.ToastUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.util.HashMap;

//登录
public class UserFragment extends BaseFragment implements View.OnClickListener, PlatformActionListener {
    public static final int RequestLogincode = 1;
    private Button btnLogin;
    private TextView mMashangLogin;
    private TextView mFoundPassWord;
    private EditText mLogin_uname;
    private EditText mLogin_pass;
    private ImageView mXinLang;
    private ImageView mQQ;
    private ImageView mWeiXin;
    private UserFragment mUserFragment;

    public static UserFragment getInstance() {
        UserFragment fragment = new UserFragment();
        return fragment;
    }

    @Override
    protected int contentViewId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void onFirstPreLoading() {
        init();
        mUserFragment = new UserFragment();
        ShareSDK.initSDK(mThis);//初始化ShareSDK
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK();//销毁ShareSDK
    }

    private void init() {
        btnLogin = (Button) mView.findViewById(R.id.btn_login);
        mMashangLogin = (TextView) mView.findViewById(R.id.mashang_login);
        mFoundPassWord = (TextView) mView.findViewById(R.id.found_password);
        mLogin_uname = (EditText) mView.findViewById(R.id.login_uname);
        mLogin_pass = (EditText) mView.findViewById(R.id.login_pass);
        mXinLang = (ImageView) mView.findViewById(R.id.logo_xinglang);
        mQQ = (ImageView) mView.findViewById(R.id.logo_qq);
        mWeiXin = (ImageView) mView.findViewById(R.id.logo_weixin);
        btnLogin.setOnClickListener(this);
        mMashangLogin.setOnClickListener(this);
        mFoundPassWord.setOnClickListener(this);
        mXinLang.setOnClickListener(this);
        mQQ.setOnClickListener(this);
        mWeiXin.setOnClickListener(this);
    }

    @Override
    protected void onFirstLoadingData() {

    }

    @Override
    protected void onFirstLoadingFinish() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login://登陆
                /*
                验证代码，验证成功才执行下面的回调
                 */
                handleLogin();
                break;
            case R.id.mashang_login://马上注册
                startActivity(RegisterActivity.class);
                break;
            case R.id.found_password://找回密码
                startActivity(GetCordActivity.class);
                break;
            case R.id.logo_xinglang://新浪
                loginXinLang();
                break;
            case R.id.logo_qq://QQ
                loginQQ();
                break;
            case R.id.logo_weixin://微信
                loginweiXin();
                break;
        }
    }

    private void handleLogin() {
        String uername = mLogin_uname.getText().toString().trim();
        String password = mLogin_pass.getText().toString();
        RequestParams params = new RequestParams(Const.URL_AUTH_TOKEN);
        params.addBodyParameter("phone",uername);
        params.addBodyParameter("password",password);
        HttpUtils.HttpPostMethod(new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String s) {
                return false;
            }

            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "onSuccess = "+ s);
                try {
                    JSONObject obj = new JSONObject(s);
                    String token = obj.getString("token");
                    if (TextUtils.isEmpty(token)){
                        return;
                    }
                    DictionaryTool.saveToken(getActivity().getApplicationContext(),token);
                    if (listener != null)
                        listener.onLoginClick("个人中心");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                ToastUtils.show(getActivity().getApplication(),"账号或密码错误，\n请重新输入");
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        },params);

    }

    /***
     * 请求token
     * @param uername
     */
    private void requestToken(String uername) {

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

    //登录成功的方法
    private void loginSucess() {
        String uname = mLogin_uname.getText().toString();
        Intent intent = new Intent(mThis, MyActivity.class);
        intent.putExtra("login_uname", uname);
//        setArguments(RequestLogincode,intent);带返回值的内容进行跳转
//        finish();
    }

    private void setArguments(int requestLogincode, Intent intent) {

    }

    //微信三方登录
    private void loginweiXin() {
        Platform platform = ShareSDK.getPlatform(mThis, Wechat.NAME);//得到微信登录平台
        platform.setPlatformActionListener(this);//监听事件授权
        if (platform.isValid()) {//是否正常登录
            String uname = platform.getDb().getUserName();
            System.out.print("通过--------------" + uname);
            loginSuccess(uname);
        } else {
            platform.showUser(null);
        }

    }

    //QQ三方登录
    private void loginQQ() {
        Platform platform = ShareSDK.getPlatform(mThis, QQ.NAME);//得到QQ登录平台
        platform.setPlatformActionListener(this);//监听事件授权
        if (platform.isValid()) {//是否正常登录
            String uname = platform.getDb().getUserName();
            System.out.print("通过--------------" + uname);
            loginSuccess(uname);
        } else {
            platform.showUser(null);
        }
    }

    //新浪三方登录
    private void loginXinLang() {
        Platform platform = ShareSDK.getPlatform(mThis, SinaWeibo.NAME);//得到新浪登录平台
        platform.setPlatformActionListener(this);//监听事件授权
        if (platform.isValid()) {//是否正常登录
            String uname = platform.getDb().getUserName();
            System.out.print("通过--------------" + uname);
            loginSuccess(uname);
        } else {
            platform.showUser(null);
        }
    }

    //登录成功执行的方法
    private void loginSuccess(String uname) {
//        Intent intent = new Intent(mThis, SplashActivity.class);
//        intent.putExtra("login_name", uname);
    }

    //完成
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        String uname = platform.getDb().getUserName();//获取第三方平台显示的名称
        System.out.print("uname-----------------" + uname);
        loginSuccess(uname);
    }

    //出错
    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Toast.makeText(mThis, platform.getName() + "授权失败,请重试", Toast.LENGTH_SHORT).show();
    }

    //取消
    @Override
    public void onCancel(Platform platform, int i) {
        Toast.makeText(mThis, platform.getName() + "授权取消", Toast.LENGTH_SHORT).show();

    }

    public interface ILoginClick {
        void onLoginClick(String text);
    }

    private ILoginClick listener;

    public void setOnLoginClickListener(ILoginClick listener) {
        this.listener = listener;
    }

    /**
     * 跳转
     *
     * @param Activity
     */
    private void startActivity(Class<?> Activity) {
        Intent intent = new Intent(getActivity(), Activity);
        startActivityForResult(intent, Const.REQUEST_CODE);
    }
}
