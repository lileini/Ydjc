package com.zangcun.store.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zangcun.store.R;
import com.zangcun.store.holder.ViewHolder;
import com.zangcun.store.model.ShopCarModel;
import com.zangcun.store.net.Net;
import com.zangcun.store.other.MyApplication;
import com.zangcun.store.utils.DictionaryTool;
import com.zangcun.store.utils.HttpUtils;
import com.zangcun.store.utils.Log;
import com.zangcun.store.utils.ToastUtils;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.util.List;

//点击详情中"锁"跳转后的购物车适配器
public class ChooseShopCarAdapter extends CommonAdapter<ShopCarModel> {

    private String TAG = "ChooseShopCarAdapter";

    public ChooseShopCarAdapter(Context context, List<ShopCarModel> datas, int layoutId) {
        super(context, datas, layoutId);

    }


    @Override
    public void convert(ViewHolder holder, final ShopCarModel shopCarModel) {


        holder.setText(R.id.item_gwc_title, shopCarModel.getGoods_name());//设置商品标题
        String[] colorSize =  shopCarModel.getColorSize();
        if (colorSize == null){
            colorSize = getColorSize(shopCarModel);
            shopCarModel.setColorSize(colorSize);
        }
        holder.setText(R.id.item_gwc_desc,shopCarModel.getGood_option());//设置商品描述
        ShopCarModel.GoodBean.OptionsIdBean optionsIdBean = shopCarModel.getOptionsIdBean();
        if (optionsIdBean == null){
            for (ShopCarModel.GoodBean.OptionsIdBean optionsIdBean2 : shopCarModel.getGood().getOptions_id()) {
                if (optionsIdBean2.getSpec_1().equals(colorSize[0]) && optionsIdBean2.getSpec_2().equals(colorSize[1])) {
                    optionsIdBean = optionsIdBean2;
                    shopCarModel.setOptionsIdBean(optionsIdBean2);
                }
            }
        }
        //中间画横线
        holder.setText(R.id.item_pay_money, "¥" + optionsIdBean.getPrice());

        holder.setText(R.id.item_pay_count, shopCarModel.getQuantity() + "");
        // TODO: 2016/4/6 三级缓存
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        imageLoader.displayImage(Net.DOMAIN + shopCarModel.getGoods_image(),//    sss
                (ImageView) holder.getView(R.id.item_gwc_img));


        notifyDataSetChanged();

    }

    /**
     * 获得商品颜色和尺寸
     * @param shopCarModel
     * @return
     */
    private String[] getColorSize(ShopCarModel shopCarModel) {
        String good_option = shopCarModel.getGood_option();
        String[] split = good_option.split(",");
        String color = split[0].substring(3);
        String size = split[1].substring(3);
        Log.i(TAG, "color = " + color);
        Log.i(TAG, "size = " + size);

        return new String[]{color, size};
    }


}
