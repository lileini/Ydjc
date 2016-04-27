package com.zangcun.store.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.zangcun.store.R;
import com.zangcun.store.net.Net;
import com.squareup.picasso.Picasso;

import java.util.List;

//详情界面Viewpager适配器
public class GoodsViewPagerAdapter extends PagerAdapter {

	private Context mCtx;
	private SparseArray<ImageView> mImageList;
	private GoodsViewPagerAdapter.IPagerClick iPagerClick;
	private List<String> mDatas;


	public GoodsViewPagerAdapter(Context mCtx, List<String> mDatas, GoodsViewPagerAdapter.IPagerClick iPagerClick) {
		this.mCtx = mCtx;
		this.mDatas = mDatas;
		this.iPagerClick = iPagerClick;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View view, Object o) {
		return view == o;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView = new ImageView(container.getContext());
		Picasso.with(container.getContext())
				.load(Net.DOMAIN+mDatas.get(position % mDatas.size()))
				.placeholder(R.drawable.sp_icon_zw)
				.into(imageView);
		container.addView(imageView);
		return imageView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	//返回集合
	public SparseArray<ImageView> getViewList() {
		return mImageList;
	}

	public interface IPagerClick {
		void onPagerClick(int pos);
	}

}
