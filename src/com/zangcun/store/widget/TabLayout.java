package com.zangcun.store.widget;

import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zangcun.store.MyActivity;
import com.zangcun.store.R;

// 控制底部tab标签切换
public class TabLayout extends LinearLayout implements View.OnClickListener {

	private int[] resIds = new int[] { R.drawable.btn_icon_sy, R.drawable.btn_icon_fl, R.drawable.btn_icon_gwc,
			R.drawable.btn_icon_gr };
	private int[] selectResIds = new int[] { R.drawable.btn_icon_sy_sel, R.drawable.btn_icon_fl_sel,
			R.drawable.btn_icon_gwc_sel, R.drawable.btn_icon_gr_sel };
	private int mCurrTab;

	public TabLayout(Context context) {
		super(context);
		init();
	}

	public TabLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TabLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		setOrientation(LinearLayout.HORIZONTAL);
		for (int i = 0; i < MyActivity.mTabs.length; i++) {
			ViewGroup view = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.tab_item, null);
			ImageView iv = (ImageView) view.findViewById(R.id.tab_item_iv);
			TextView tv = (TextView) view.findViewById(R.id.tab_item_tv);
			iv.setImageDrawable(setBg(getContext(), selectResIds[i], resIds[i]));
			tv.setText(MyActivity.mTabs[i]);
			LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			params.weight = 1;
			addView(view, params);
			view.setOnClickListener(this);
		}
}

	public void setTabText(int index, String text, int check, int noCheck) {
		LinearLayout childAt = (LinearLayout) getChildAt(index);
		TextView textView=(TextView)childAt.findViewById(R.id.tab_item_tv);
		ImageView imageView=(ImageView)childAt.findViewById(R.id.tab_item_iv);
		imageView.setImageDrawable(setBg(getContext(), check, noCheck));
		textView.setText(text);
	}

	public void setTab(int index) {
		getChildAt(mCurrTab).setSelected(false);
		getChildAt(index).setSelected(true);
		LinearLayout childAt = (LinearLayout) getChildAt(index);
		TextView textView=(TextView)childAt.findViewById(R.id.tab_item_tv);
//		if(getChildAt(index).isEnabled())
//		{
//			textView.setTextColor(this.getResources().getColor(R.color.red));
//		}else{
//			textView.setTextColor(this.getResources().getColor(R.color.yellow));
//		}
		mCurrTab = index;
		if (iTabClick != null)
			iTabClick.tabClick(index);
	}

	@Override
	public void onClick(View v) {
		for (int i = 0; i < resIds.length; i++) {
			if (v != getChildAt(i)) {
				getChildAt(i).setSelected(false);
			} else {
				if (mCurrTab == i)
					break;
				getChildAt(i).setSelected(true);
				mCurrTab = i;
				if (iTabClick != null)
					iTabClick.tabClick(i);
			}
		}

	}

	private StateListDrawable setBg(Context ctx, int resId, int defresId) {
		StateListDrawable sDrawable = new StateListDrawable();
		sDrawable.addState(new int[] { android.R.attr.state_selected }, ctx.getResources().getDrawable(resId));
		sDrawable.addState(new int[] { android.R.attr.state_pressed }, ctx.getResources().getDrawable(resId));
		sDrawable.addState(new int[] {}, ctx.getResources().getDrawable(defresId));
		return sDrawable;
	}

	private ITabClick iTabClick;

	public void setOnTabClickListener(ITabClick iTabClick) {
		this.iTabClick = iTabClick;
	}

	public interface ITabClick {
		void tabClick(int index);
	}
}
