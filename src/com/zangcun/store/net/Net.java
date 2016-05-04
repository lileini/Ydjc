package com.zangcun.store.net;

import android.util.Log;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;


public class Net {
    public static final String HOST = "http://211.149.231.116:3000/";//主机名

    public static final String DOMAIN = "http://www.zangcun.com/";//加在图片之前的服务器地址
    public static final String URL_FX = HOST + "products/foxiang.json";//佛像
    public static final String URL_TK = HOST + "products/tangka.json";//唐卡
    public static final String URL_FSYP = HOST + "products/fsyp.json";//佛事用品
    public static final String URL_XD = HOST + "products/xianglu.json";//香道
    public static final String URL_GOODS_DEAIL = HOST + "products/:id.json";//单个商品详情
    public static final String URL_GOODS_List = HOST + "products/:id.json";//商品列表

    public static final String URL_CARTS = HOST + "carts.json";//商品列表,Get请求
    public static final String URL_GOODS_CARTS = HOST + "carts.json";//POST请求添加至购物车
    public static final String URL_GOODS_DELETE = HOST + "carts/:id.json";//DELETE讲商品从购物车移除
    public static final String URL_INCREMENT = HOST + "carts/:id/increment.json";//PUT请求添加商品数量
    public static final String URL_DECREMENT = HOST + "carts/:id/decrement.json";//PUT请求减少商品数量
    public static final String URL_CHECKED = HOST + "carts/:id/set_checked.json";//PUT请求选中商品
    public static final String URL_UNCHECKED = HOST + "carts/:id/set_unchecked.json";//PUT请求取消选中商品


    public static final String URL_ADDRESSES = HOST + "addresses/options.json";//收货地址，登录之后带上Token请求
    public static final String URL_ADD_ADDRESSES = HOST + "addresses.json";//添加收货地址
    public static final String URL_GET_ADDRESSES = HOST + "user.json";//获取收货地址

    public static final String URL_CEAT_ORDER = HOST + "orders.json";//创建订单
    public static final String URL_WAIT_FOR_PAY = HOST + "orders/waiting_for_pay.json";//待付款
    public static final String URL_WAIT_FOR_SHIP = HOST + "orders/waiting_for_ship.json";//待收货
    public static final String URL_WAIT_FOR_RECEIVE = HOST + "orders/waiting_for_receive.json";//待收货
//    public static final String URL_GET_ADDRESSES = HOST + "user.json";//获取收货地址
//    public static final String URL_GET_ADDRESSES = HOST + "user.json";//获取收货地址




    //防止解析报错
    public static <T> T parseJson(String json, Class<T> cls) {
        T t = null;
        try {
            t = JSON.parseObject(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static <T> List<T> parseJsonList(String json, Class<T> cls) {
        List<T> list=new ArrayList<T>();
        try {
//        	Log.d("debug", "fx:"+json);
            list= JSON.parseArray(json,cls);
            Log.d("debug", "fx:"+list.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
