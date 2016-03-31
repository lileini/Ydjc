package com.zangcun.store.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;

//自定义ScrollView
public class MyScrollView extends FrameLayout {
    private ViewGroup mTopView;//固定在顶部的view
    private View mTopContent;//固定在顶部的内容
    private int mTopViewTop;//固定在顶部的View距离最上端的距离
    private ViewGroup mContentView;//ScrollView里面的内容
    private RealScrollView mScrollView;//真正的ScrollView
    private int mOffsetY;//置顶的偏移量(相当于margin)

    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        post(new Runnable() {
            @Override
            public void run() {
                mContentView = (ViewGroup) getChildAt(0);//得到第一个子控件
                removeAllViews();//清空
                mScrollView = new RealScrollView(getContext(), MyScrollView.this);
                addView(mScrollView);
                mScrollView.addView(mContentView);
                mScrollView.setVerticalScrollBarEnabled(false);
            }
        });
    }

    public void setTopView(int id) {
        post(new Runnable() {
            @Override
            public void run() {
                mTopView = (ViewGroup) mContentView.findViewById(id);
                mTopViewTop = mTopView.getTop();
                mTopContent = mTopView.getChildAt(0);
            }
        });
    }

    public void setOnScroll(final RealScrollView.OnScroll onScroll) {
        post(new Runnable() {
            @Override
            public void run() {
                mScrollView.setOnScroll(onScroll);
            }
        });
    }

    public void setOffset(int offset) {
        this.mOffsetY = offset;
    }

    public void onScroll(int scrollY) {
        post(new Runnable() {
            @Override
            public void run() {
                if (mTopView == null) return;
                LayoutParams params = (LayoutParams) mTopContent.getLayoutParams();
                if (scrollY >= mTopViewTop - mOffsetY && mTopContent.getParent() == mTopView) {
                    mTopView.removeView(mTopContent);
                    params.topMargin = mOffsetY;
                    addView(mTopContent, params);
                } else if (scrollY < mTopViewTop - mOffsetY && mTopContent.getParent() == MyScrollView.this) {
                    removeView(mTopContent);
                    params.topMargin = 0;
                    mTopView.addView(mTopContent, params);
                }
            }
        });
    }

    //自定义ScrollView
    public static class RealScrollView extends ScrollView {

        private MyScrollView myScrollView;
        private OnScroll onSrcoll;

        public RealScrollView(Context context, MyScrollView myScrollView) {
            super(context);
            this.myScrollView = myScrollView;
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            super.onScrollChanged(l, t, oldl, oldt);
            myScrollView.onScroll(t);
            notifyScrollListener();
        }

        //接口注册
        private void setOnScroll(OnScroll onScroll) {
            this.onSrcoll = onScroll;
        }

        //接口通知
        private void notifyScrollListener() {
            if (this.onSrcoll != null) {
                onSrcoll.onScrollY(getScrollY());//滚动距离通知出去
            }
        }

        public interface OnScroll {
            void onScrollY(int y);
        }
    }
}


