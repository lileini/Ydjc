package com.zangcun.store.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.zangcun.store.R;
import com.zangcun.store.holder.ViewHolder;
import com.zangcun.store.model.ShopCarModel;

import java.util.List;

//点击详情中"锁"跳转后的购物车适配器
public class ShopCarAdapter extends CommonAdapter<ShopCarModel> {

    private PriceAndCountChangeListener listener;

    public ShopCarAdapter(Context context, List<ShopCarModel> datas, int layoutId) {
        super(context, datas, layoutId);

    }

    @Override
    public void convert(ViewHolder holder, final ShopCarModel shopCarModel) {

        holder.getView(R.id.item_gwc_ischecked).setSelected(
                shopCarModel.isChecked());
        holder.setText(R.id.item_gwc_title, shopCarModel.getTitle());
        holder.setText(R.id.item_gwc_market_price,
                "¥" + shopCarModel.getMarketPrice());
        //中间画横线
        TextView textView = holder.getView(R.id.item_gwc_market_price);
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.setText(R.id.item_gwc_price, "¥" + shopCarModel.getPrice());

        holder.setText(R.id.item_gwc_count, shopCarModel.getCount() + "");
//		ImageLoader imageLoader = ImageLoader.getInstance();
//    	imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
//    	imageLoader.displayImage(shopCarModel.getImgUrl(),
//				(ImageView) holder.getView(R.id.item_gwc_img));
        holder.setOnClickListener(R.id.item_gwc_ischecked, new OnClickListener() {
            @Override
            public void onClick(View v) {
                shopCarModel.setChecked(!shopCarModel.isChecked());
                calCount();
                calMoney();
                notifyDataSetChanged();
            }
        });

        holder.setOnClickListener(R.id.item_gwc_less, new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopCarModel.getCount() == 0) {
                    return;
                }
                shopCarModel.setCount(shopCarModel.getCount() - 1);
                calCount();
                calMoney();
                notifyDataSetChanged();
            }


        });
        holder.setOnClickListener(R.id.item_gwc_more, new OnClickListener() {
            @Override
            public void onClick(View v) {
                shopCarModel.setCount(shopCarModel.getCount() + 1);
                calCount();
                calMoney();
                notifyDataSetChanged();
            }
        });


        holder.setOnClickListener(R.id.item_gwc_del, new OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatas.remove(shopCarModel);
                calCount();
                calMoney();
                notifyDataSetChanged();
            }
        });

    }


    protected void calMoney() {
        int money = 0;
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).isChecked()) {
                money += Integer.valueOf(mDatas.get(i).getPrice()) * mDatas.get(i).getCount();
            }
        }
        listener.onPriceChanged(money);
    }

    protected void calCount() {
        int count = 0;
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).isChecked()) {
                count += mDatas.get(i).getCount();
            }
        }
        listener.onCountCHanged(count);
    }


    public PriceAndCountChangeListener getListener() {
        return listener;
    }

    public void setListener(PriceAndCountChangeListener listener) {
        this.listener = listener;
    }


    public interface PriceAndCountChangeListener {
        void onPriceChanged(int total);

        void onCountCHanged(int count);
    }
//
//    private void onCliick(ViewHolder holder, final int id, final ShopCarModel shopCarModel) {
//        holder.setOnClickListener(id, new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (id) {
//                    case R.id.item_gwc_less:
//                        if (shopCarModel.getCount() == 0) {
//                            return;
//                        }
//                        shopCarModel.setCount(shopCarModel.getCount() - 1);
//                        calCount();
//                        calMoney();
//                        notifyDataSetChanged();
//                        break;
//                }
//            }
//        });
//    }
}
