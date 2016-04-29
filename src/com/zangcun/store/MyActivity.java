package com.zangcun.store;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zangcun.store.dao.CityDao;
import com.zangcun.store.fragment.*;
import com.zangcun.store.model.CityModel;
import com.zangcun.store.net.Net;
import com.zangcun.store.other.Const;
import com.zangcun.store.utils.DictionaryTool;
import com.zangcun.store.utils.HttpUtils;
import com.zangcun.store.utils.ToastUtils;
import com.zangcun.store.widget.TabLayout;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends BaseActivity implements TabLayout.ITabClick, UserFragment.ILoginClick, PersonalFragment.PersionILoginClick {
    public static String[] mTabs = new String[]{"专题", "分类", "购物车", "登录"};
    public static String[] mTabs1 = new String[]{"专题", "分类", "购物车", "个人中心"};
    public static final String IS_LOGIN = "个人中心";
    private View mTitle;
    private TextView mTitleText;
    private TabLayout mTab;
    private String mCurrFragmentTag;
    private boolean isLogin;
    private String strFlag = null;
    private Object adressId;
    private boolean canEixt;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            canEixt = false;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initEvent();
    }


    private void init() {
        mTab = (TabLayout) findViewById(R.id.tab);
        mTitle = findViewById(R.id.title_group);
        mTitleText = (TextView) findViewById(R.id.title);
        strFlag = getIntent().getStringExtra("falg");
        autoLogin();
    }

    private void initEvent() {
//        Log.e("MyActivity","执行了这里");
        if (strFlag != null) {
            mTab.setTab(3);
        } else {
            mTab.setTab(0);
        }

        mTitle.setVisibility(View.GONE);
        mTab.setOnTabClickListener(this);
        initFragment();

    }

    /**
     * 自动登录
     */
    private void autoLogin() {
        String user = DictionaryTool.getUser(getApplicationContext());
        String pwd = DictionaryTool.getPWD(getApplicationContext());
        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pwd)) {
            isLogin = true;
            login(user, pwd);
            getAdressId();
        }
    }

    private void login(String user, String pwd) {
        RequestParams params = new RequestParams(Const.URL_AUTH_TOKEN);
        params.addBodyParameter("phone", user);
        params.addBodyParameter("password", pwd);
        HttpUtils.HttpPostMethod(new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String s) {
                return false;
            }

            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "onSuccess = " + s);
                try {
                    JSONObject obj = new JSONObject(s);
                    String token = obj.getString("token");
//                    Log.i(TAG, "onSuccess = " + token);
                    if (TextUtils.isEmpty(token)) {
                        return;
                    }
                    DictionaryTool.saveToken(getApplicationContext(), token);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                ToastUtils.show(getApplication(), "自动登录失败");
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        }, params);
    }

    private void initFragment() {
        if (isLogin) {
            for (int i = 0; i < mTabs.length; i++) {
                putFragment(mTabs1[i], getFragmentByIndex(i));
            }
            mTab.setTabText(3, "个人中心", R.drawable.btn_icon_gr_sel, R.drawable.btn_icon_gr);
        } else {
            for (int i = 0; i < mTabs.length; i++) {
                putFragment(mTabs[i], getFragmentByIndex(i));
            }
        }

        /*for (int i = 0; i < mTabs.length; i++) {
            putFragment(mTabs[i], getFragmentByIndex(i));
        }*/
        if (strFlag != null) {
            if (strFlag.equals("退出登录")) {
                for (int i = 0; i < mTabs.length; i++) {
                    putFragment(mTabs[i], getFragmentByIndex(i));
                }
                switchFragment(mTabs[3]);
                mTab.setTabText(3, "登录", R.drawable.btn_icon_gr_sel, R.drawable.btn_icon_gr);
            } else {
                for (int i = 0; i < mTabs.length; i++) {
                    putFragment(mTabs[i], getFragmentByIndexPersion(i));
                }
                switchFragment(mTabs[3]);
                mTab.setTabText(3, "个人中心", R.drawable.btn_icon_gr_sel, R.drawable.btn_icon_gr);
            }
        }
        else {
            switchFragment(mTabs[0]);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeApp();
    }

    @Override
    public void tabClick(int index) {
        if (index == 0) {
            mTitle.setVisibility(View.GONE);
        } else {
            mTitle.setVisibility(View.VISIBLE);
            if (isLogin) {
                mTitleText.setText(mTabs1[index]);
            } else {
                mTitleText.setText(mTabs[index]);
            }
        }
        if (index == mTabs.length - 1) {
            if (isLogin) {
                switchFragment("个人中心");
            } else {
                switchFragment(mTabs[index]);
            }
        } else {
            switchFragment(mTabs[index]);
        }

    }

    public BaseFragment getFragmentByIndex(int index) {
        switch (index) {
            case 1:
                return SortFragment.getInstance();
            case 2:
                return ShopFragment.getInstance();
            case 3:
                if (isLogin) {
                    PersonalFragment personalFragment = PersonalFragment.getInstance();
                    return personalFragment;
                } else {
                    UserFragment fragment = UserFragment.getInstance();
                    fragment.setOnLoginClickListener(this);
                    return fragment;
                }
            default:
                return SpecialFragment.getInstance();
        }
    }

    public BaseFragment getFragmentByIndexPersion(int index) {
        switch (index) {
            case 0:
                return SpecialFragment.getInstance();
            case 1:
                return SortFragment.getInstance();
            case 2:
                return ShopFragment.getInstance();
            case 3:
                PersonalFragment fragment = PersonalFragment.getInstance();
                fragment.setPersionOnLoginClickListener(this);
//                ToastUtils.show(this,"asdfa");
                return fragment;
            default:
                return null;
        }
    }

    @Override
    public void onLoginClick(String text) {
        putFragment(text, PersonalFragment.getInstance());
        // mTab.setTabText(3,text);
        mTab.setTabText(3, text, R.drawable.btn_icon_gr_sel, R.drawable.btn_icon_gr);
        Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
        mTitleText.setText(text);
        switchFragment(text);
        isLogin = true;
    }

    @Override
    public void onPersionLoginClick(String text) {
        putFragment(text, UserFragment.getInstance());
        mTab.setTabText(3, "登录", R.drawable.btn_icon_gr, R.drawable.btn_icon_gr_sel);
        mTitleText.setText(text);
        switchFragment(text);
        isLogin = true;
//        Log.i(TAG, "onPersionLoginClick ");
    }
    /**
     * 监听返回键
     * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 按两次退出程序
     * */
    private void exit() {
        if (!canEixt) {
            canEixt = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    /**
     * 获取收货地址id存进数据库
     */
    public void getAdressId() {
        RequestParams params = new RequestParams(Net.URL_ADDRESSES);
        params.addHeader("Authorization", DictionaryTool.getToken(getApplicationContext()));
        HttpUtils.HttpGetMethod(new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "onSuccess = " + s);
                List<CityModel> cityList = new Gson().fromJson(s, new TypeToken<ArrayList<CityModel>>() {
                }.getType());
                Log.i(TAG, "cityList= " + cityList);
                if (cityList == null) {
                    return;
                }
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        CityDao.saveCityList(cityList);
                    }
                }.start();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

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
    protected void onRestart() {
        super.onRestart();
//        Log.e("onRestart","执行了这里");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(PersonalFragment.aBoolean){
            PersonalFragment.aBoolean=false;
            isLogin=false;
            strFlag="退出登录";
            initFragment();
        }
        if(UserFragment.Login){
            putFragment("个人中心", PersonalFragment.getInstance());
            // mTab.setTabText(3,text);
            mTab.setTabText(3, "个人中心", R.drawable.btn_icon_gr_sel, R.drawable.btn_icon_gr);
            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
            mTitleText.setText("个人中心");
            switchFragment("个人中心");
            isLogin = true;
        }

    }
}
