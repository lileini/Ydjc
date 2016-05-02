package com.zangcun.store.adapter;

import android.content.Context;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zangcun.store.R;
import com.zangcun.store.activity.OrderActivity;
import com.zangcun.store.entity.OrderResultEntity;
import com.zangcun.store.holder.ViewHolder;
import com.zangcun.store.model.ShopCarModel;
import com.zangcun.store.net.Net;
import com.zangcun.store.utils.Log;

import java.util.List;

//点击详情中"锁"跳转后的购物车适配器
public class OrderAdapter extends CommonAdapter<OrderResultEntity.OrderBean.OrderGoodsBean> {

    private String TAG = "OrderAdapter";



    public OrderAdapter(OrderActivity context, List<OrderResultEntity.OrderBean.OrderGoodsBean> order_goodsList, int item_pay) {
        super(context, order_goodsList, item_pay);
    }


    @Override
    public void convert(ViewHolder holder, final OrderResultEntity.OrderBean.OrderGoodsBean goodBean) {

        OrderResultEntity.OrderBean.OrderGoodsBean.GoodBean good = goodBean.getGood();
        holder.setText(R.id.item_gwc_title, good.getGoods_name());//设置商品标题
        String[] colorSize =  good.getColorSize();
        if (colorSize == null){
            colorSize = getColorSize(goodBean);
            good.setColorSize(colorSize);
        }
        holder.setText(R.id.item_gwc_desc,goodBean.getGoods_attr());//设置商品描述
        OrderResultEntity.OrderBean.OrderGoodsBean.GoodBean.OptionsIdBean optionsIdBean = good.getOptionsIdBean();
        if (optionsIdBean == null){
            for (OrderResultEntity.OrderBean.OrderGoodsBean.GoodBean.OptionsIdBean optionsIdBean2 : good.getOptions_id()) {
                if (optionsIdBean2.getSpec_1().equals(colorSize[0]) && optionsIdBean2.getSpec_2().equals(colorSize[1])) {
                    optionsIdBean = optionsIdBean2;
                    good.setOptionsIdBean(optionsIdBean2);
                }
            }
        }
        //中间画横线
        holder.setText(R.id.item_pay_money, "¥" + optionsIdBean.getPrice());

        holder.setText(R.id.item_pay_count, goodBean.getGoods_numbers() + "");
        // TODO: 2016/4/6 三级缓存
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        imageLoader.displayImage(Net.DOMAIN + good.getDefault_image(),//    sss
                (ImageView) holder.getView(R.id.item_gwc_img));


//        notifyDataSetChanged();

    }

    /**
     * 获得商品颜色和尺寸
     * @param goodBean
     * @return
     */
    private String[] getColorSize(OrderResultEntity.OrderBean.OrderGoodsBean goodBean) {
        String good_option = goodBean.getGoods_attr();
        String[] split = good_option.split(",");
        String color = split[0].substring(3);
        String size = split[1].substring(3);
        Log.i(TAG, "color = " + color);
        Log.i(TAG, "size = " + size);

        return new String[]{color, size};
    }


}
