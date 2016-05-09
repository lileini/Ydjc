package com.zangcun.store.wxpay;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/6.
 */
public class WxPayUtil {
    private Context context;
    private Handler mHandler;
    private IWXAPI api;
    private String TAG = "WxPayUtil";

    public WxPayUtil(Context context, Handler handler) throws Exception {
        if (!(context instanceof Activity)){
            throw new Exception("必须是activity");
        }
        this.context = context;
        this.mHandler = handler;
    }
    public void pay(String payInfo){
        try{

                Log.i(TAG,"payInfo = "+payInfo);
                JSONObject json = new JSONObject(payInfo);
                if(null != json && !json.has("retcode") ){
                    PayReq req = new PayReq();
                    req.appId			= json.getString("appid");
                    req.partnerId		= json.getString("partnerid");
                    req.prepayId		= json.getString("prepayid");
                    req.nonceStr		= json.getString("noncestr");
                    req.timeStamp		= json.getString("timestamp");
                    req.packageValue	= json.getString("package");
                    req.sign			= json.getString("sign");
                    req.extData			= "app data"; // optional
                    Toast.makeText(context, "正常调起支付", Toast.LENGTH_SHORT).show();
                    // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信

                    api = WXAPIFactory.createWXAPI(context, req.appId);
                    api.sendReq(req);
                }else{
                    Log.d(TAG, "返回错误"+json.getString("retmsg"));
                    Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                }

        }catch(Exception e){
            Log.e(TAG, "异常："+e.getMessage());
            Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
        }
    }

}
