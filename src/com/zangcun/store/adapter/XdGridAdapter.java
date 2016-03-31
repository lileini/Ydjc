package com.zangcun.store.adapter;

import android.content.Context;
import android.widget.ImageView;
import com.zangcun.store.R;
import com.zangcun.store.holder.ViewHolder;
import com.zangcun.store.model.FsypModel;
import com.zangcun.store.model.XdModel;
import com.zangcun.store.net.Net;
import com.squareup.picasso.Picasso;

import java.util.List;

//香道适配器
public class XdGridAdapter extends CommonAdapter<XdModel> {
    private ImageView iv;
    private List<FsypModel.OptionsIdEntity> mDataList;

    public XdGridAdapter(Context context, List<XdModel> datas, int layoutId) {
        super(context, datas, layoutId);

    }

    @Override
    public void convert(ViewHolder holder, XdModel tkModel) {
    	Picasso.with(mContext).load(Net.DOMAIN+tkModel.getDefault_image()).placeholder(R.drawable.sp_icon_zw).error(R.drawable.sp_icon_zw).into((ImageView)holder.getView(R.id.gv_iv));
        holder.setText(R.id.gv_price, "¥"+tkModel.getPrice())
                .setText(R.id.gv_info, tkModel.getGoods_name());
    }
    

}
