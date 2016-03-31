package com.zangcun.store.adapter;

import android.content.Context;
import android.widget.ImageView;
import com.zangcun.store.R;
import com.zangcun.store.holder.ViewHolder;
import com.zangcun.store.model.TkModel;
import com.zangcun.store.net.Net;
import com.squareup.picasso.Picasso;

import java.util.List;

//唐卡适配器
public class TkGridAdapter extends CommonAdapter<TkModel> {
    private ImageView iv;
    private List<TkModel.OptionsIdEntity> mDataList;

    public TkGridAdapter(Context context, List<TkModel> datas, int layoutId) {
        super(context, datas, layoutId);

    }

    @Override
    public void convert(ViewHolder holder, TkModel tkModel) {
    	Picasso.with(mContext).load(Net.DOMAIN+tkModel.getDefault_image()).placeholder(R.drawable.sp_icon_zw).error(R.drawable.sp_icon_zw).into((ImageView)holder.getView(R.id.gv_iv));
        holder.setText(R.id.gv_price, "¥"+tkModel.getPrice())
                .setText(R.id.gv_info, tkModel.getGoods_name());
    }
    

}
