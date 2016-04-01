package com.zangcun.store.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zangcun.store.person.*;
import com.zangcun.store.R;
import com.zangcun.store.utils.DictionaryTool;

//个人中心
public class PersonalFragment extends BaseFragment implements View.OnClickListener {
    private RelativeLayout mIndent;//全部订单
    private RelativeLayout mCollect;//我的收藏
    private RelativeLayout mLoginPassword;//登录密码
    private RelativeLayout mAdress;//收货地址
    private RelativeLayout mAcount;//第三方账号
    private RelativeLayout mExit;//退出登录
    private RelativeLayout mAbout;//关于我们
    private LinearLayout mLinPay;//待付款
    private LinearLayout mLinDiliver;//待发货
    private LinearLayout mLinCollection;//待收货
    private LinearLayout mLinPJ;
    private TextView tvPhone;

    public static PersonalFragment getInstance() {
        PersonalFragment fragment = new PersonalFragment();
        return fragment;
    }

    @Override
    protected int contentViewId() {
        return R.layout.personal;
    }

    @Override
    protected void onFirstPreLoading() {
        init();
        intentData();
    }

    private void init() {
        mIndent = (RelativeLayout) mView.findViewById(R.id.indent);
        mCollect = (RelativeLayout) mView.findViewById(R.id.collect);
        mLoginPassword = (RelativeLayout) mView.findViewById(R.id.login_password);
        mAdress = (RelativeLayout) mView.findViewById(R.id.address);
        mAcount = (RelativeLayout) mView.findViewById(R.id.acount);
        mExit = (RelativeLayout) mView.findViewById(R.id.exit);
        mAbout = (RelativeLayout) mView.findViewById(R.id.about);
        mLinPay = (LinearLayout) mView.findViewById(R.id.lin_pay);
        mLinDiliver = (LinearLayout) mView.findViewById(R.id.lin_deliver);
        mLinCollection = (LinearLayout) mView.findViewById(R.id.lin_collection);
        mLinPJ = (LinearLayout) mView.findViewById(R.id.lin_pj);
        tvPhone = (TextView) mView.findViewById(R.id.fragment_dsx_username_text);
        mIndent.setOnClickListener(this);
        mCollect.setOnClickListener(this);
        mLoginPassword.setOnClickListener(this);
        mAdress.setOnClickListener(this);
        mAcount.setOnClickListener(this);
        mExit.setOnClickListener(this);
        mAbout.setOnClickListener(this);
        mLinPay.setOnClickListener(this);
        mLinDiliver.setOnClickListener(this);
        mLinCollection.setOnClickListener(this);
        mLinPJ.setOnClickListener(this);
    }

    private void intentData() {
        String pwd = DictionaryTool.getPWD(getActivity().getApplicationContext());
        String user = DictionaryTool.getUser(getActivity().getApplicationContext());
        if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(user)){

            tvPhone.setText(user);
        }else {

            tvPhone.setText(DictionaryTool.get(getActivity(), "phone", "").toString());
        }
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
            case R.id.indent://全部订单
                startActivity(new Intent(mThis, IndentActivity.class));
                break;
            case R.id.collect://我的收藏
                startActivity(new Intent(mThis, CollectActivity.class));
                break;
            case R.id.login_password://登录密码
                startActivity(new Intent(mThis, PassWordActivity.class));
                break;
            case R.id.address://收货地址
                startActivity(new Intent(mThis, AddressActivity.class));
                break;
            case R.id.acount://第三方账号
                startActivity(new Intent(mThis, AcountActivity.class));
                break;
            case R.id.exit://退出
                if (listener != null)
                    listener.onPersionLoginClick("个人中心");
                break;
            case R.id.about://关于我们
                startActivity(new Intent(mThis, AboutActivity.class));
                break;
            case R.id.lin_pay://待付款
                startActivity(new Intent(mThis, LinPayActivity.class));
                break;
            case R.id.lin_deliver://待发货
                startActivity(new Intent(mThis, LinDeliverActivity.class));
                break;
            case R.id.lin_collection://待收货
                startActivity(new Intent(mThis, LinCollectionActivity.class));
                break;
            case R.id.lin_pj://待评价
                startActivity(new Intent(mThis, LinPJActivity.class));
                break;
        }
    }

    public interface PersionILoginClick {
        void onPersionLoginClick(String text);
    }

    private PersionILoginClick listener;

    public void setPersionOnLoginClickListener(PersionILoginClick listener) {
        this.listener = listener;
    }
}
