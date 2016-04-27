package com.zangcun.store.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.zangcun.store.R;
import com.zangcun.store.activity.*;

//分类
public class SortFragment extends BaseFragment implements View.OnClickListener {
    private TextView mFx;
    private TextView mTk;
    private TextView mFsyp;
    private TextView mXd;
    private TextView mZzyp;
    private TextView mFzps;
//    private SortAdapter mAdapter;

    public static SortFragment getInstance() {
        SortFragment fragment = new SortFragment();

        return fragment;
    }

    @Override
    protected int contentViewId() {
        return R.layout.fragment_sort;
    }

    @Override
    protected void onFirstPreLoading() {
        init();
    }

    private void init() {
        mFx = (TextView) mView.findViewById(R.id.activity_fx);
        mTk = (TextView) mView.findViewById(R.id.activity_tk);
        mFsyp = (TextView) mView.findViewById(R.id.activity_fsyp);
        mXd = (TextView) mView.findViewById(R.id.activity_xd);
        mZzyp= (TextView) mView.findViewById(R.id.activity_zzyp);
        mFzps= (TextView) mView.findViewById(R.id.activity_fzps);
        mFx.setOnClickListener(this);
        mTk.setOnClickListener(this);
        mFsyp.setOnClickListener(this);
        mXd.setOnClickListener(this);
        mZzyp.setOnClickListener(this);
        mFzps.setOnClickListener(this);
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
            case R.id.activity_fx:
                startActivity(new Intent(mThis, FxActivity.class));
                break;
            case R.id.activity_tk:
                startActivity(new Intent(mThis, TkActivity.class));
                break;
            case R.id.activity_fsyp:
                startActivity(new Intent(mThis, FsypActivity.class));
                break;
            case R.id.activity_xd:
                startActivity(new Intent(mThis, XdActivity.class));
                break;
            case R.id.activity_zzyp:
                startActivity(new Intent(mThis, ZzypActivity.class));
                break;
            case R.id.activity_fzps:
                startActivity(new Intent(mThis, FzpsActivity.class));
                break;
        }
    }
}
