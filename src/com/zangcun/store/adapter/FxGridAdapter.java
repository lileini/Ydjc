package com.zangcun.store.adapter;

import android.content.Context;
import android.widget.ImageView;
import com.zangcun.store.R;
import com.zangcun.store.holder.ViewHolder;
import com.zangcun.store.model.FxModel;
import com.zangcun.store.net.Net;
import com.squareup.picasso.Picasso;

import java.util.List;

//佛像适配器
public class FxGridAdapter extends CommonAdapter<FxModel> {
    private ImageView iv;
    private List<FxModel.OptionsIdEntity> mDataList;

    public FxGridAdapter(Context context, List<FxModel> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, FxModel fxModel) {
        Picasso.with(mContext).load(Net.DOMAIN + fxModel.getDefault_image())
                .placeholder(R.drawable.sp_icon_zw)
                .error(R.drawable.sp_icon_zw)
                .into((ImageView) holder.getView(R.id.gv_iv));


        //ImageLoader.getInstance().displayImage(Net.DOMAIN + fxModel.getDefault_image().toString(),(ImageView) holder.getView(R.id.gv_iv), MyApplication.sImageOptions);
        holder.setText(R.id.gv_price, "¥" + fxModel.getPrice())
                .setText(R.id.gv_info, fxModel.getGoods_name());

    }


}
