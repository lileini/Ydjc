package com.zangcun.store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.zangcun.store.R;
import com.zangcun.store.model.LinDeliverModel;

import java.util.List;


//待发货适配器
public class LinDeLiverAdapter extends BaseAdapter {
    LayoutInflater inflater;
    private List<LinDeliverModel> mDataList;
    private Context mContext;

    public LinDeLiverAdapter(Context mContext, List<LinDeliverModel> strings) {
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mDataList = strings;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public LinDeliverModel getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lindo, null);
            holder = new ViewHolder();
            holder.tv_lin_time = (TextView) convertView.findViewById(R.id.tv_lin_time);
            holder.lin_img = (ImageView) convertView.findViewById(R.id.lin_img);
            holder.lin_number = (TextView) convertView.findViewById(R.id.lin_number);
            holder.money = (TextView) convertView.findViewById(R.id.money);
            holder.btn_send = (Button) convertView.findViewById(R.id.btn_go_pay);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        OnClick aa=new OnClick(position);
        //holder.textViewl.setOnClickListener(aa);
        //
        return convertView;
    }

    static class ViewHolder {
        private TextView tv_lin_time;//下单时间
        private ImageView lin_img;//商品图片
        private TextView lin_number;//商品数量
        private TextView money;//总价
        private Button btn_send;//提醒发货按钮
    }
//
//    class OnClick implements View.OnClickListener {
//        int positon;
//        OnClick(int positon) {
//            this.positon=positon;
//        }
//
//        @Override
//        public void onClick(View v) {
//
//        }
//    }
}
