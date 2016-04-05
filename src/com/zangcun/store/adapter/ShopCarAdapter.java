package com.zangcun.store.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.zangcun.store.R;
import com.zangcun.store.holder.ViewHolder;
import com.zangcun.store.model.ShopCarModel;
import com.zangcun.store.utils.Log;

import java.util.List;

//点击详情中"锁"跳转后的购物车适配器
public class ShopCarAdapter extends CommonAdapter<ShopCarModel> {

    private PriceAndCountChangeListener listener;
    private String TAG = "ShopCarAdapter";

    public ShopCarAdapter(Context context, List<ShopCarModel> datas, int layoutId) {
        super(context, datas, layoutId);

    }

    @Override
    public void convert(ViewHolder holder, final ShopCarModel shopCarModel) {

        holder.getView(R.id.item_gwc_ischecked).setSelected(shopCarModel.getIschecked() == 1);
        holder.setText(R.id.item_gwc_title, shopCarModel.getGoods_name());
        getPrice(shopCarModel);
        holder.setText(R.id.item_gwc_market_price,
                "¥" + shopCarModel.getGood().getOptions_id().get(0).getMarket_price());
        //中间画横线
        TextView textView = holder.getView(R.id.item_gwc_market_price);
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.setText(R.id.item_gwc_price, "¥" + shopCarModel.getPrice());

        holder.setText(R.id.item_gwc_count, shopCarModel.getQuantity() + "");
//		ImageLoader imageLoader = ImageLoader.getInstance();
//    	imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
//    	imageLoader.displayImage(shopCarModel.getImgUrl(),
//				(ImageView) holder.getView(R.id.item_gwc_img));
        holder.setOnClickListener(R.id.item_gwc_ischecked, new OnClickListener() {
            @Override
            public void onClick(View v) {
                shopCarModel.setIschecked(shopCarModel.getIschecked() == 0 ? 1:0);
                calCount();
                calMoney();
                notifyDataSetChanged();
            }
        });

        holder.setOnClickListener(R.id.item_gwc_less, new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopCarModel.getQuantity() == 0) {
                    return;
                }
                shopCarModel.setQuantity(shopCarModel.getQuantity() - 1);
                calCount();
                calMoney();
                notifyDataSetChanged();
            }


        });
        holder.setOnClickListener(R.id.item_gwc_more, new OnClickListener() {
            @Override
            public void onClick(View v) {
                shopCarModel.setQuantity(shopCarModel.getQuantity() + 1);
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

    private String[] getPrice(ShopCarModel shopCarModel) {
        String good_option = shopCarModel.getGood_option();
        String[] split = good_option.split(",");
        String color = split[0].substring(3);
        String size = split[1].substring(3);
        Log.i(TAG, "color = "+ color);
        Log.i(TAG, "size = "+ size);

        return new String[]{color,size};
    }


    protected void calMoney() {
        int money = 0;
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).getIschecked() == 1) {
                money += Integer.valueOf(mDatas.get(i).getPrice()) * mDatas.get(i).getQuantity();
            }
        }
        listener.onPriceChanged(money);
    }

    protected void calCount() {
        int count = 0;
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).getIschecked() == 1) {
                count += mDatas.get(i).getQuantity();
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
