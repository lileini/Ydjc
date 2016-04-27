package com.zangcun.store.other;

//登录注册与个人中心接口
public class Const {
    public final static int SUCCESS = 0X123;
    public final static int ERROR = 0X456;
    // 请求码
    public final static int REQUEST_CODE = 1;
    public final static int REQUEST_TK = 2;
    public final static int REQUEST_FX = 3;
    public final static int REQUEST_XD = 4;
    public final static int REQUEST_FSYP = 5;
    public final static int REQUEST_ZZYP = 6;
    public final static int REQUEST_FZPS = 7;
    public final static int REQUEST_DETAIL = 8;

    public static final String HOST = "http://211.149.231.116:3000/";//主机名
    public final static String BASE_URL = "http://211.149.231.116:3000";
    // 获取手机验证码
    public final static String URL_SENDMESSGE = "http://211.149.231.116:3000/auth_token/get_code.json";
    //验证服务器发送的验证码
    public final static String URL_CODE = "http://211.149.231.116:3000/auth_token/auth_phone.json";

    //POST请求注册
    public final static String URL_USER = "http://211.149.231.116:3000/user.json";
    //PUT请求修改密码
    public final static String URL_RESET_PASSWORD = "http://211.149.231.116:3000/user/reset_password.json";
    //PUT请求重置密码
    public final static String URL_NEW_PASSWORD = "http://211.149.231.116:3000/user/new_password.json";

    //用户登录
    public final static String URL_AUTH_TOKEN = "http://211.149.231.116:3000/auth_token.json";


    //GET请求待付款订单列表
    public final static String URL_WAITING_FOR_PAY = "http://211.149.231.116:3000/orders/waiting_for_pay.json";
    //GET请求待发货订单列表
    public final static String URL_WAITING_FOR_SHIP = "http://211.149.231.116:3000/orders/waiting_for_ship.json";
    //GET请求待收货订单列表
    public final static String URL_WAITING_FOR_RECEIVE = "http://211.149.231.116:3000/orders/waiting_for_receive.json";
    //PUT请求 取消订单
    public final static String URL_CANCEL = "http://211.149.231.116:3000/orders/:id/cancel.json";
    //PUT请求 申请退款
    public final static String URL_REFUND = "http://211.149.231.116:3000/orders/:id/refund.json";
    //PUT请求 提醒发货
    public final static String URL_CARTS = "http://211.149.231.116:3000/carts.json";

    //商品分类
    public final static String URL_PRODUCTS_FX = BASE_URL + "/products/foxiang.json";
    public final static String URL_PRODUCTS_TK = BASE_URL + "/products/tangka.json";
    public final static String URL_PRODUCTS_FS = BASE_URL + "/products/fsyp.json";
    public final static String URL_PRODUCTS_XD = BASE_URL + "/products/xianglu.json";

    //广播接收
    public final static String SHOP_CAR_RECIEVER = "shop_car_reciever";


    //POST请求收藏商品
    public final static String URL_COLLECT = "http://211.149.231.116:3000/collects.json";
    //DELETE删除商品
    public static final String URL_COLLECT_DELETE = HOST + "collects/:id.json";
    //GET请求获取收藏商品列表
    public final static String URL_COLLECT_LIEBIAO = "http://211.149.231.116:3000/collects.json";

}
