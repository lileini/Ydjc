package com.zangcun.store.widget;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.zangcun.store.R;



//ViewPager指示器
public class AdapterIndicator extends RadioGroup {

	private Context mContext;

	private int mCount;
	private int resId = R.drawable.selector_indicator_color;
	private int size = 10;


	public void setSize(int size) {
		this.size = size;
	}

	public AdapterIndicator(Context context) {
		this(context, null);
	}

	public AdapterIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setGravity(Gravity.CENTER);
		setOrientation(HORIZONTAL);
	}

	public void setPointCount(int count) {
		mCount = count;
		initIndicator();
	}


	private void initIndicator() {
		removeAllViews();
		for(int i = 0; i < mCount; i++) {
			RadioButton button = new RadioButton(mContext);
			button.setButtonDrawable(R.drawable.selector_indicator_color);
			button.setPadding(5, 0, 5, 0);
			button.setId(i);
			addView(button);
		}
		if(mCount != 0) {
			select(0);
		}
	}

	public void select(int position) {
		check(position);
	}


	public void bindViewPager(final ViewPager viewPager) {
		if(viewPager != null) {
			viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
				@Override
				public void onPageScrolled(int i, float v, int i1) {

				}

				@Override
				public void onPageSelected(int position) {
					select(position % mCount);
				}

				@Override
				public void onPageScrollStateChanged(int i) {

				}
			});
		}
	}
}
