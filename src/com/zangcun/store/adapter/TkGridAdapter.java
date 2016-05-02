package com.zangcun.store.adapter;

import android.content.Context;
import android.widget.ImageView;
import com.zangcun.store.R;
import com.zangcun.store.holder.ViewHolder;
import com.zangcun.store.model.FxModel;
import com.zangcun.store.net.Net;
import com.squareup.picasso.Picasso;
import com.zangcun.store.utils.Log;

import java.util.List;

//唐卡适配器
public class TkGridAdapter extends CommonAdapter<FxModel> {
    private ImageView iv;
    private List<FxModel.OptionsIdEntity> mDataList;
    private String TAG = "TkGridAdapter";

    public TkGridAdapter(Context context, List<FxModel> datas, int layoutId) {
        super(context, datas, layoutId);

    }

    @Override
    public void convert(ViewHolder holder, FxModel tkModel) {
        Log.i(TAG,"tkModel.getDefault_image(): "+ tkModel.getDefault_image());
        ImageView view = (ImageView) holder.getView(R.id.gv_iv);
        Log.i(TAG, "view: "+ view);
        Picasso.with(mContext).load(Net.DOMAIN+tkModel.getDefault_image()).placeholder(R.drawable.sp_icon_zw)
                .error(R.drawable.sp_icon_zw).into((ImageView)holder.getView(R.id.gv_iv));
        holder.setText(R.id.gv_price, "¥"+tkModel.getPrice())
                .setText(R.id.gv_info, tkModel.getGoods_name());
    }
    

}
