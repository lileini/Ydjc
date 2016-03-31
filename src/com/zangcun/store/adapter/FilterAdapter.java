package com.zangcun.store.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.zangcun.store.R;

import java.util.List;

//筛选适配器
public class FilterAdapter extends BaseAdapter {
	
	private List<String> mDataList;
	private Context mContext;
	

	public FilterAdapter(List<String> mDataList, Context mContext) {
		this.mDataList = mDataList;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return mDataList.size();
	}

	@Override
	public Object getItem(int position) {
		return mDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final TextView textView = new TextView(mContext);
		//没有图片直接 用selector 颜色来代替
		textView.setBackgroundResource(R.drawable.item_filter_selector);
		textView.setText(mDataList.get(position));
		textView.setGravity(Gravity.CENTER);
		textView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				textView.setSelected(!textView.isSelected());
			}
		});
		return textView;
	}
}
