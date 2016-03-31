package com.zangcun.store.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//总Fragment
public abstract class BaseFragment extends Fragment {

    public static final String TAG_FIRST_LOADING = "tag_first_loading";
    private  String TAG;

    protected View mView;
    protected FragmentActivity mThis;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        mView = inflater.inflate(contentViewId(), null);
        mThis = getActivity();
        startAysncLoad(TAG_FIRST_LOADING);
        Log.i(TAG,"Fragment~~~~~ oncreate");
        return mView;
    }


    protected void startAysncLoad(final String tag) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                if (TAG_FIRST_LOADING.equals(tag)) {
                    onFirstPreLoading();
                } else {
                    onPreLoading(tag);
                }
            }

            @Override
            protected Void doInBackground(Void... params) {
                if (TAG_FIRST_LOADING.equals(tag)) {
                    onFirstLoadingData();
                } else {
                    onLoadingData(tag);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (TAG_FIRST_LOADING.equals(tag)) {
                    onFirstLoadingFinish();
                } else {
                    onLoadingFinish(tag);
                }
            }
        }.execute();
    }

    protected abstract int contentViewId();

    protected abstract void onFirstPreLoading();

    protected abstract void onFirstLoadingData();

    protected abstract void onFirstLoadingFinish();

    protected void onPreLoading(String tag) {

    }

    protected void onLoadingData(String tag) {

    }

    protected void onLoadingFinish(String tag) {

    }
}
