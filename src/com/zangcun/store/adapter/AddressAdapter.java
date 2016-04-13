package com.zangcun.store.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zangcun.store.R;
import com.zangcun.store.model.AddressModel;
import com.zangcun.store.model.GetAddressResultModel;
import com.zangcun.store.net.Net;
import com.zangcun.store.person.AddAddressActivity;
import com.zangcun.store.person.AddressActivity;
import com.zangcun.store.utils.*;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.util.List;

//收货地址适配器
public class AddressAdapter extends BaseAdapter {
    private Context mContext;
    private List<GetAddressResultModel.AddressBean> mDataList;
    private String TAG= "AddressAdapter";

    public AddressAdapter(Context mContext, List<GetAddressResultModel.AddressBean> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
    }
    public void setmDataList(List<GetAddressResultModel.AddressBean> mDataList){
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public GetAddressResultModel.AddressBean getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GetAddressResultModel.AddressBean addressBean = mDataList.get(position);
        Log.i(TAG, "addressBean = "+ addressBean.toString());
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_address_add, null);
            holder = new ViewHolder();
            holder.address_name = (TextView) convertView.findViewById(R.id.address_name);
            holder.address_mr = (TextView) convertView.findViewById(R.id.address_mr);
            holder.address_phone = (TextView) convertView.findViewById(R.id.address_phone);
            holder.address_city = (TextView) convertView.findViewById(R.id.address_city);
            holder.address_sz_mr = (TextView) convertView.findViewById(R.id.address_sz_mr);
            holder.address_bj = (LinearLayout) convertView.findViewById(R.id.address_bj);
            holder.address_del = (LinearLayout) convertView.findViewById(R.id.address_del);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (addressBean != null){

            holder.address_name.setText(addressBean.getConsignee());
            holder.address_phone.setText(addressBean.getMobile());
            holder.address_city.setText(addressBean.getAddress());
            //设置编辑事件
            holder.address_bj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AddAddressActivity.class);
                    intent.putExtra("addressBean",addressBean);
                    ((AddressActivity)mContext).startActivityForResult(intent,101);
                }
            });
            //设置删除事件
            holder.address_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestAddressDelete(addressBean);
                }
            });
            //设置默认地址
            holder.address_sz_mr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestAddressDefault(addressBean);
                }
            });
        }
        return convertView;
    }

    private void requestAddressDelete(GetAddressResultModel.AddressBean addressBean) {
        RequestParams params = new RequestParams(Net.HOST+"addresses/"+addressBean.getId()+".json");
        params.addHeader("Authorization", DictionaryTool.getToken(mContext));
        addressBean.setIs_default(true);
        params.addBodyParameter("address", GsonUtil.toJson(addressBean));
        HttpUtils.HttpDeleteMethod(new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                ToastUtils.show(mContext.getApplicationContext(),"删除成功");
                mDataList.remove(addressBean);
                notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                ToastUtils.show(mContext.getApplicationContext(),"删除失败");
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        },params);
    }

    private void requestAddressDefault(GetAddressResultModel.AddressBean addressBean) {
        RequestParams params = new RequestParams(Net.HOST+"addresses/"+addressBean.getId()+".json");
        params.addHeader("Authorization", DictionaryTool.getToken(mContext));
        addressBean.setIs_default(true);
        String json = GsonUtil.toJson(addressBean);
        Log.i(TAG,"json = "+ json);
        params.addBodyParameter("address", json);
        HttpUtils.HttpPutMethod(new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                ToastUtils.show(mContext.getApplicationContext(),"设置成功");
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                ToastUtils.show(mContext.getApplicationContext(),"设置失败");
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        },params);
    }

    static class ViewHolder {
        private TextView address_name;//收货人姓名
        private TextView address_mr;//默认地址
        private TextView address_phone;//收货人电话
        private TextView address_city;//详细地址
        private TextView address_sz_mr;//设为默认
        private LinearLayout address_bj;//编辑
        private LinearLayout address_del;//删除

    }
}
