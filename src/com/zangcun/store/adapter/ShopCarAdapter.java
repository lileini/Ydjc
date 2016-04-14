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
import com.zangcun.store.utils.*;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

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
        holder.setText(R.id.item_gwc_market_price,
                "¥" + optionsIdBean.getMarket_price());
        //中间画横线
        TextView textView = holder.getView(R.id.item_gwc_market_price);
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.setText(R.id.item_gwc_price, "¥" + optionsIdBean.getPrice());

        holder.setText(R.id.item_gwc_count, shopCarModel.getQuantity() + "");
        // TODO: 2016/4/6 三级缓存
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        imageLoader.displayImage(Net.DOMAIN + shopCarModel.getGoods_image(),//    sss
                (ImageView) holder.getView(R.id.item_gwc_img));


        holder.setOnClickListener(R.id.item_gwc_ischecked, new OnClickListener() {
            @Override
            public void onClick(View v) {
                isCheckedGood(shopCarModel);
                shopCarModel.setIschecked(shopCarModel.getIschecked() == 1 ? 0 : 1);
                calCount();
                calMoney();
                notifyDataSetChanged();
            }
        });
        if (shopCarModel.getQuantity() == 1){
            holder.setEnable(R.id.item_gwc_less,false);
        }
        holder.setOnClickListener(R.id.item_gwc_less, new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopCarModel.getQuantity() == 1) {
                    holder.setEnable(R.id.item_gwc_ischecked,false);
                    return;
                }
                incrementGood(shopCarModel,"-");
                shopCarModel.setQuantity(shopCarModel.getQuantity() - 1);
                calCount();
                calMoney();
                notifyDataSetChanged();

            }

        });
        holder.setOnClickListener(R.id.item_gwc_more, new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopCarModel.getQuantity() == 1) {
                    holder.setEnable(R.id.item_gwc_ischecked,true);
                }
                incrementGood(shopCarModel,"+");
                shopCarModel.setQuantity(shopCarModel.getQuantity() + 1);
                calCount();
                calMoney();
                notifyDataSetChanged();
            }
        });


        holder.setOnClickListener(R.id.item_gwc_del, new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!HttpUtils.isHaveNetwork()){
                    return;
                }
//                DialogUtil.showDialog(mContext);
                deleteGood(shopCarModel);

            }


        });
        calCount();
        calMoney();
        notifyDataSetChanged();

    }

    private void incrementGood(ShopCarModel shopCarModel,String methed) {
        if (methed == null)
            return;
        String url = "";
        if ("+".equals(methed)){
            url = Net.HOST+"carts/"+shopCarModel.getRec_id()+"/increment.json";
        }
        if ("-".equals(methed)){
            url = Net.HOST+"carts/"+shopCarModel.getRec_id()+"/decrement.json";
        }
        RequestParams params = new RequestParams(url);
        params.addHeader("AUTHORIZATION", DictionaryTool.getToken(mContext.getApplicationContext()));
        HttpUtils.HttpPutMethod(new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "onSuccess = "+ s);

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i(TAG, "onError = "+throwable.toString());
                ToastUtils.show(MyApplication.instance,"系统繁忙，请稍后再试");
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        },params);
    }
    private void isCheckedGood(ShopCarModel shopCarModel) {

        String url = "";
        if (shopCarModel.getIschecked() == 1){
            url = Net.HOST+"carts/"+shopCarModel.getRec_id()+"/set_unchecked.json";
        }else{
            url = Net.HOST+"carts/"+shopCarModel.getRec_id()+"/set_checked.json";
        }
        RequestParams params = new RequestParams(url);
        params.addHeader("AUTHORIZATION", DictionaryTool.getToken(mContext.getApplicationContext()));
        HttpUtils.HttpPutMethod(new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "onSuccess = "+ s);

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i(TAG, "onError = "+throwable.toString());
                ToastUtils.show(MyApplication.instance,"系统繁忙，请稍后再试");
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        },params);
    }

    private void deleteGood(ShopCarModel shopCarModel ) {
        RequestParams params = new RequestParams(Net.HOST+"carts/"+shopCarModel.getRec_id()+".json");
        params.addHeader("AUTHORIZATION", DictionaryTool.getToken(mContext.getApplicationContext()));
        HttpUtils.HttpDeleteMethod(new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                mDatas.remove(shopCarModel);
                calCount();
                calMoney();
                notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i(TAG, "onError = "+throwable.toString());
                ToastUtils.show(MyApplication.instance,"系统繁忙，请稍后再试");

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {
//                        DialogUtil.dissmissDialog();
            }
        },params);
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


    protected void calMoney() {
        int money = 0;
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).getIschecked() == 1) {
                money += Double.valueOf(mDatas.get(i).getPrice()) * mDatas.get(i).getQuantity();
            }
        }
        if (listener != null)
            listener.onPriceChanged(money);
    }

    protected void calCount() {
        int count = 0;
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).getIschecked() == 1) {
                count += mDatas.get(i).getQuantity();
            }
        }
        if (listener != null)
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
