package com.zangcun.store;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class BaseActivity extends FragmentActivity {
    public static String TAG;
    public static ArrayList<Activity> mActivityList = new ArrayList<Activity>();

    protected HashMap<String, Fragment> mFragments;
    protected String mCurFragment;
    protected boolean isFirst;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityList.add(this);
        mFragments = new HashMap<String, Fragment>();
        TAG = getLocalClassName();
        Log.i(TAG,"~~~~~~~~~~~~~Oncreate");
    }

    protected void putFragment(String tag, Fragment fragment) {
        mFragments.put(tag, fragment);
    }

    protected void switchFragment(String fragmentTag) {
        FragmentManager fmanager = getSupportFragmentManager();
        FragmentTransaction ft = fmanager.beginTransaction();

        Fragment fragment = fmanager.findFragmentByTag(fragmentTag);
        if (!TextUtils.isEmpty(mCurFragment)) {
            Fragment lastFragment = fmanager.findFragmentByTag(mCurFragment);
            if (lastFragment != null) {
                ft.hide(lastFragment);
            }
        }

        if (fragment == null) {
            fragment = mFragments.get(fragmentTag);

            ft.add(R.id.container, fragment, fragmentTag);
        } else {
            ft.show(fragment);
        }

        mCurFragment = fragmentTag;
        ft.commitAllowingStateLoss();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityList.remove(this);
    }

    public static void closeApp() {
        for (Activity activity : mActivityList) {
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
        System.exit(0);
    }


//    public static void startActivity(Activity ac, Class<? extends Activity> cls) {
//        Intent intent = new Intent(ac, cls);
//        ac.startActivity(intent);
//        ac.overridePendingTransition(R.anim.slide_bottom_in, R.anim.scale_out); //只能与启动Activity和finish结合使用
//    }

//    public void startActivityNoAnim(Class<? extends Activity> cls) {
//        Intent intent = new Intent(this, cls);
//        startActivity(intent);
//        overridePendingTransition(0, 0);
//    }


    public void closeActivity() {
        finish();
//        overridePendingTransition(R.anim.scale_in, R.anim.slide_bottom_out);
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }
}
