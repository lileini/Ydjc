package com.zangcun.store.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.squareup.picasso.Picasso;
import com.zangcun.store.R;
import com.zangcun.store.model.FxModel;
import com.zangcun.store.net.CommandBase;
import com.zangcun.store.net.Net;
import com.zangcun.store.other.Const;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//我的收藏适配器
public class CollectAdapter extends BaseAdapter {
    private Context mContext;
    private List<FxModel> mDataList;
    private boolean isok = false;

    public CollectAdapter(Context context, List<FxModel> mDataList) {
        this.mContext = context;
        this.mDataList = mDataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public FxModel getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_collect, null);
            holder = new ViewHolder();
            holder.price = (TextView) convertView.findViewById(R.id.gv_price);
            holder.lin_img = (ImageView) convertView.findViewById(R.id.gv_iv);
            holder.lin_miaosu = (TextView) convertView.findViewById(R.id.gv_info);
            holder.mImgDel = (ImageView) convertView.findViewById(R.id.collect_del);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (isok) {
            holder.mImgDel.setVisibility(View.VISIBLE);
        } else {
            holder.mImgDel.setVisibility(View.INVISIBLE);
        }
        holder.mImgDel.setOnClickListener(new MyDelLister(position));
        Picasso.with(mContext).load(Net.DOMAIN + mDataList.get(position)
                .getDefault_image()).placeholder(R.drawable.sp_icon_zw).error(R.drawable.sp_icon_zw).into((ImageView) holder.lin_img);
        holder.price.setText("¥" + mDataList.get(position).getPrice());
        holder.lin_miaosu.setText(mDataList.get(position).getGoods_name());
        return convertView;
    }

    class MyDelLister implements View.OnClickListener {
        private int mPosition;

        public MyDelLister(int position) {
            this.mPosition = position;
        }

        @Override
        public void onClick(View v) {
            Map<String, String> map = new HashMap<String, String>();
//            map.put("good_id", String.valueOf(mDataList.get(mPosition).getGoods_id()));
//           map.put("token", DictionaryTool.getToken(this));
            Log.e("map", map.toString());
            CommandBase.requestDataMapDel(mContext, "http://211.149.231.116:3000/collects/" + mDataList.get(mPosition).getGoods_id() + ".json", handler, map);
        }
    }

    static class ViewHolder {
        private ImageView lin_img;//商品图片
        private TextView price;//价格
        private TextView lin_miaosu;//商品描述
        private ImageView mImgDel;
    }

    public void chageView(List<FxModel> dataList) {
        this.mDataList = dataList;
        this.notifyDataSetChanged();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Const.SUCCESS) {
                try {
                    mDataList = JSON.parseArray(msg.obj.toString(), FxModel.class);
                } catch (Exception e) {
                }
                chageView(mDataList);
//                Log.e("json",msg.obj.toString());
                //做逻辑处理
            } else if (msg.what == Const.ERROR) {
                Toast.makeText(mContext, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void viewDel() {
        if (isok) {
            this.isok = false;
        } else {
            this.isok = true;
        }
        this.notifyDataSetChanged();
    }
}
