package com.zangcun.store.person;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;
import com.zangcun.store.BaseActivity;
import com.zangcun.store.R;
import com.zangcun.store.adapter.AddAddressWhellAdapter;
import com.zangcun.store.dao.CityDao;
import com.zangcun.store.model.AddressModel;
import com.zangcun.store.model.CityDateModule;
import com.zangcun.store.model.CityModel;
import com.zangcun.store.model.GetAddressResultModel;
import com.zangcun.store.net.Net;
import com.zangcun.store.utils.*;
import com.zangcun.store.widget.MyWheelView;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.util.*;


//添加地址
public class AddAddressActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBack;//标题返回图标
    private TextView mTitle;//标题
    private TextView mTitleRight;//标题右边文字

    private Spinner province_spinner;//省
    private Spinner city_spinner;//市
    private Spinner county_spinner;//县

    private int[] city = {R.array.beijin_province_item, R.array.tianjin_province_item, R.array.heibei_province_item, R.array.shanxi1_province_item, R.array.neimenggu_province_item, R.array.liaoning_province_item, R.array.jilin_province_item, R.array.heilongjiang_province_item, R.array.shanghai_province_item, R.array.jiangsu_province_item, R.array.zhejiang_province_item, R.array.anhui_province_item, R.array.fujian_province_item, R.array.jiangxi_province_item, R.array.shandong_province_item, R.array.henan_province_item, R.array.hubei_province_item, R.array.hunan_province_item, R.array.guangdong_province_item, R.array.guangxi_province_item, R.array.hainan_province_item, R.array.chongqing_province_item, R.array.sichuan_province_item, R.array.guizhou_province_item, R.array.yunnan_province_item, R.array.xizang_province_item, R.array.shanxi2_province_item, R.array.gansu_province_item, R.array.qinghai_province_item, R.array.linxia_province_item, R.array.xinjiang_province_item, R.array.hongkong_province_item, R.array.aomen_province_item, R.array.taiwan_province_item};
    private int[] countyOfBeiJing = {R.array.beijin_city_item};
    private int[] countyOfTianJing = {R.array.tianjin_city_item};
    private int[] countyOfHeBei = {R.array.shijiazhuang_city_item, R.array.tangshan_city_item, R.array.qinghuangdao_city_item, R.array.handan_city_item, R.array.xingtai_city_item, R.array.baoding_city_item, R.array.zhangjiakou_city_item, R.array.chengde_city_item, R.array.cangzhou_city_item, R.array.langfang_city_item, R.array.hengshui_city_item};
    private int[] countyOfShanXi1 = {R.array.taiyuan_city_item, R.array.datong_city_item, R.array.yangquan_city_item, R.array.changzhi_city_item, R.array.jincheng_city_item, R.array.shuozhou_city_item, R.array.jinzhong_city_item, R.array.yuncheng_city_item, R.array.xinzhou_city_item, R.array.linfen_city_item, R.array.lvliang_city_item};
    private int[] countyOfNeiMengGu = {R.array.huhehaote_city_item, R.array.baotou_city_item, R.array.wuhai_city_item, R.array.chifeng_city_item, R.array.tongliao_city_item, R.array.eerduosi_city_item, R.array.hulunbeier_city_item, R.array.bayannaoer_city_item, R.array.wulanchabu_city_item, R.array.xinganmeng_city_item, R.array.xilinguolemeng_city_item, R.array.alashanmeng_city_item};
    private int[] countyOfLiaoNing = {R.array.shenyang_city_item, R.array.dalian_city_item, R.array.anshan_city_item, R.array.wushun_city_item, R.array.benxi_city_item, R.array.dandong_city_item, R.array.liaoning_jinzhou_city_item, R.array.yingkou_city_item, R.array.fuxin_city_item, R.array.liaoyang_city_item, R.array.panjin_city_item, R.array.tieling_city_item, R.array.zhaoyang_city_item, R.array.huludao_city_item};
    private int[] countyOfJiLin = {R.array.changchun_city_item, R.array.jilin_city_item, R.array.siping_city_item, R.array.liaoyuan_city_item, R.array.tonghua_city_item, R.array.baishan_city_item, R.array.songyuan_city_item, R.array.baicheng_city_item, R.array.yanbian_city_item};
    private int[] countyOfHeiLongJiang = {R.array.haerbing_city_item, R.array.qiqihaer_city_item, R.array.jixi_city_item, R.array.hegang_city_item, R.array.shuangyashan_city_item, R.array.daqing_city_item, R.array.heilongjiang_yichun_city_item, R.array.jiamusi_city_item, R.array.qitaihe_city_item, R.array.mudanjiang_city_item, R.array.heihe_city_item, R.array.suihua_city_item, R.array.daxinganling_city_item};
    private int[] countyOfShangHai = {R.array.shanghai_city_item};

    private int[] countyOfJiangSu = {R.array.nanjing_city_item, R.array.wuxi_city_item, R.array.xuzhou_city_item, R.array.changzhou_city_item, R.array.nanjing_suzhou_city_item, R.array.nantong_city_item, R.array.lianyungang_city_item, R.array.huaian_city_item, R.array.yancheng_city_item, R.array.yangzhou_city_item, R.array.zhenjiang_city_item, R.array.jiangsu_taizhou_city_item, R.array.suqian_city_item};
    private int[] countyOfZheJiang = {R.array.hangzhou_city_item, R.array.ningbo_city_item, R.array.wenzhou_city_item, R.array.jiaxing_city_item, R.array.huzhou_city_item, R.array.shaoxing_city_item, R.array.jinhua_city_item, R.array.quzhou_city_item, R.array.zhoushan_city_item, R.array.zejiang_huzhou_city_item, R.array.lishui_city_item};
    private int[] countyOfAnHui = {R.array.hefei_city_item, R.array.wuhu_city_item, R.array.bengbu_city_item, R.array.huainan_city_item, R.array.maanshan_city_item, R.array.huaibei_city_item, R.array.tongling_city_item, R.array.anqing_city_item, R.array.huangshan_city_item, R.array.chuzhou_city_item, R.array.fuyang_city_item, R.array.anhui_suzhou_city_item, R.array.chaohu_city_item, R.array.luan_city_item, R.array.haozhou_city_item, R.array.chizhou_city_item, R.array.xuancheng_city_item};
    private int[] countyOfFuJian = {R.array.huzhou_city_item, R.array.xiamen_city_item, R.array.putian_city_item, R.array.sanming_city_item, R.array.quanzhou_city_item, R.array.zhangzhou_city_item, R.array.nanp_city_item, R.array.longyan_city_item, R.array.ningde_city_item};
    private int[] countyOfJiangXi = {R.array.nanchang_city_item, R.array.jingdezhen_city_item, R.array.pingxiang_city_item, R.array.jiujiang_city_item, R.array.xinyu_city_item, R.array.yingtan_city_item, R.array.ganzhou_city_item, R.array.jian_city_item, R.array.jiangxi_yichun_city_item, R.array.jiangxi_wuzhou_city_item, R.array.shangrao_city_item};
    private int[] countyOfShanDong = {R.array.jinan_city_item, R.array.qingdao_city_item, R.array.zaobo_city_item, R.array.zaozhuang_city_item, R.array.dongying_city_item, R.array.yantai_city_item, R.array.weifang_city_item, R.array.jining_city_item, R.array.taian_city_item, R.array.weihai_city_item, R.array.rizhao_city_item, R.array.laiwu_city_item, R.array.linxi_city_item, R.array.dezhou_city_item, R.array.liaocheng_city_item, R.array.shandong_bingzhou_city_item, R.array.heze_city_item};
    private int[] countyOfHeNan = {R.array.zhenshou_city_item, R.array.kaifang_city_item, R.array.luoyang_city_item, R.array.kaipingshan_city_item, R.array.anyang_city_item, R.array.hebi_city_item, R.array.xinxiang_city_item, R.array.jiaozuo_city_item, R.array.buyang_city_item, R.array.xuchang_city_item, R.array.leihe_city_item, R.array.sanmenxia_city_item, R.array.nanyang_city_item, R.array.shangqiu_city_item, R.array.xinyang_city_item, R.array.zhoukou_city_item, R.array.zhumadian_city_item};
    private int[] countyOfHuBei = {R.array.wuhan_city_item, R.array.huangshi_city_item, R.array.shiyan_city_item, R.array.yichang_city_item, R.array.xiangpan_city_item, R.array.erzhou_city_item, R.array.jinmen_city_item, R.array.xiaogan_city_item, R.array.hubei_jinzhou_city_item, R.array.huanggang_city_item, R.array.xianning_city_item, R.array.suizhou_city_item, R.array.enshi_city_item, R.array.shenglongjia_city_item};

    private int[] countyOfHuNan = {R.array.changsha_city_item, R.array.zhuzhou_city_item, R.array.xiangtan_city_item, R.array.hengyang_city_item, R.array.shaoyang_city_item, R.array.yueyang_city_item, R.array.changde_city_item, R.array.zhangjiajie_city_item, R.array.yiyang_city_item, R.array.hunan_bingzhou_city_item, R.array.yongzhou_city_item, R.array.huaihua_city_item, R.array.loudi_city_item, R.array.xiangxi_city_item};
    private int[] countyOfGuangDong = {R.array.guangzhou_city_item, R.array.shaoguan_city_item, R.array.shenzhen_city_item, R.array.zhuhai_city_item, R.array.shantou_city_item, R.array.foshan_city_item, R.array.jiangmen_city_item, R.array.zhangjiang_city_item, R.array.maoming_city_item, R.array.zhaoqing_city_item, R.array.huizhou_city_item, R.array.meizhou_city_item, R.array.shanwei_city_item, R.array.heyuan_city_item, R.array.yangjiang_city_item, R.array.qingyuan_city_item, R.array.dongguan_city_item, R.array.zhongshan_city_item, R.array.chaozhou_city_item, R.array.jiyang_city_item, R.array.yunfu_city_item};
    private int[] countyOfGuangXi = {R.array.nanning_city_item, R.array.liuzhou_city_item, R.array.guilin_city_item, R.array.guangxi_wuzhou_city_item, R.array.beihai_city_item, R.array.fangchenggang_city_item, R.array.qinzhou_city_item, R.array.guigang_city_item, R.array.yuelin_city_item, R.array.baise_city_item, R.array.hezhou_city_item, R.array.hechi_city_item, R.array.laibing_city_item, R.array.chuangzuo_city_item};
    private int[] countyOfHaiNan = {R.array.haikou_city_item, R.array.sanya_city_item};
    private int[] countyOfChongQing = {R.array.chongqing_city_item};
    private int[] countyOfSiChuan = {R.array.chengdu_city_item, R.array.zigong_city_item, R.array.panzhihua_city_item, R.array.luzhou_city_item, R.array.deyang_city_item, R.array.mianyang_city_item, R.array.guangyuan_city_item, R.array.suining_city_item, R.array.neijiang_city_item, R.array.leshan_city_item, R.array.nanchong_city_item, R.array.meishan_city_item, R.array.yibing_city_item, R.array.guangan_city_item, R.array.dazhou_city_item, R.array.yaan_city_item, R.array.bazhong_city_item, R.array.ziyang_city_item, R.array.abei_city_item, R.array.ganmu_city_item, R.array.liangshan_city_item};
    private int[] countyOfGuiZhou = {R.array.guiyang_city_item, R.array.lupanshui_city_item, R.array.zhunyi_city_item, R.array.anshun_city_item, R.array.tongren_city_item, R.array.qingxinan_city_item, R.array.biji_city_item, R.array.qingdongnan_city_item, R.array.qingnan_city_item};
    private int[] countyOfYunNan = {R.array.kunming_city_item, R.array.qujing_city_item, R.array.yuexi_city_item, R.array.baoshan_city_item, R.array.zhaotong_city_item, R.array.lijiang_city_item, R.array.simao_city_item, R.array.lingcang_city_item, R.array.chuxiong_city_item, R.array.honghe_city_item, R.array.wenshan_city_item, R.array.xishuangbanna_city_item, R.array.dali_city_item, R.array.dehuang_city_item, R.array.nujiang_city_item, R.array.diqing_city_item};
    private int[] countyOfXiZang = {R.array.lasa_city_item, R.array.changdu_city_item, R.array.shannan_city_item, R.array.rgeze_city_item, R.array.naqu_city_item, R.array.ali_city_item, R.array.linzhi_city_item};

    private int[] countyOfShanXi2 = {R.array.xian_city_item, R.array.tongchuan_city_item, R.array.baoji_city_item, R.array.xianyang_city_item, R.array.weinan_city_item, R.array.yanan_city_item, R.array.hanzhong_city_item, R.array.yulin_city_item, R.array.ankang_city_item, R.array.shangluo_city_item};
    private int[] countyOfGanSu = {R.array.lanzhou_city_item, R.array.jiayuguan_city_item, R.array.jinchang_city_item, R.array.baiyin_city_item, R.array.tianshui_city_item, R.array.wuwei_city_item, R.array.zhangyue_city_item, R.array.pingliang_city_item, R.array.jiuquan_city_item, R.array.qingyang_city_item, R.array.dingxi_city_item, R.array.longnan_city_item, R.array.linxia_city_item, R.array.gannan_city_item};
    private int[] countyOfQingHai = {R.array.xining_city_item, R.array.haidong_city_item, R.array.haibai_city_item, R.array.huangnan_city_item, R.array.hainan_city_item, R.array.guluo_city_item, R.array.yushu_city_item, R.array.haixi_city_item};
    private int[] countyOfNingXia = {R.array.yinchuan_city_item, R.array.shizuishan_city_item, R.array.wuzhong_city_item, R.array.guyuan_city_item, R.array.zhongwei_city_item};
    private int[] countyOfXinJiang = {R.array.wulumuqi_city_item, R.array.kelamayi_city_item, R.array.tulyfan_city_item, R.array.hami_city_item, R.array.changji_city_item, R.array.boertala_city_item, R.array.bayinguolen_city_item, R.array.akesu_city_item, R.array.kemuleisu_city_item, R.array.geshen_city_item, R.array.hetian_city_item, R.array.yili_city_item, R.array.tacheng_city_item, R.array.aleitai_city_item, R.array.shihezi_city_item, R.array.alaer_city_item, R.array.tumushihe_city_item, R.array.wujiaqu_city_item};
    private int[] countyOfHongKong = {R.array.hongkong_city_item};
    private int[] countyOfAoMen = {R.array.aomen_city_item};
    private int[] countyOfTaiWan = {R.array.taiwan_city_item};

    private ArrayAdapter<CharSequence> province_adapter;
    private ArrayAdapter<CharSequence> city_adapter;
    private ArrayAdapter<CharSequence> county_adapter;

    private PopupWindow mPopWindow;
    private TextView week;
    private EditText etName;
    private EditText etMobile;
    private EditText etDetialedAddress;
    private boolean isEdit = false;
    private GetAddressResultModel.AddressBean addressBean;
    private TextView tvAddAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_address);
        init();
        initDate();
    }

    private void initDate() {
        Intent intent = getIntent();
        if (intent == null)
            return;
        addressBean = (GetAddressResultModel.AddressBean) intent.getSerializableExtra("addressBean");

        if (addressBean == null)
            return;
        isEdit = true;
        etName.setText(addressBean.getConsignee());
        etMobile.setText(addressBean.getMobile());
        String address2 = addressBean.getAddress();
        Log.i(TAG,"address2 = "+ address2);
        int indexOf = address2.indexOf(" ", StringUtil.indexofFrom(address2, " ", 3));
        Log.i(TAG,"indexOf = "+ indexOf);
        address = addressBean.getAddress().substring(0,indexOf+1);
        tvAddAddress.setText(address);
        etDetialedAddress.setText(address2.substring(indexOf+1));
    }

    private void popupChoose() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.popupwindow_week, null);
        mPopWindow = new PopupWindow(contentView, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        mPopWindow.setFocusable(true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setAnimationStyle(R.style.popwin_anim_style);
        TextView tv1 = (TextView) contentView.findViewById(R.id.one_five);
        TextView tv2 = (TextView) contentView.findViewById(R.id.six_seven);
        TextView tv3 = (TextView) contentView.findViewById(R.id.one_seven);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        View rootview = LayoutInflater.from(this).inflate(R.layout.popupwindow_week, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }


    private void init() {
        mTitle = (TextView) findViewById(R.id.tv_personal_title);
        mTitle.setText("添加地址");
        mTitleRight = (TextView) findViewById(R.id.pesonal_right);
        mTitleRight.setText("完成");
        mTitleRight.setOnClickListener(this);
        mBack = (ImageView) findViewById(R.id.personal_back);
        mBack.setOnClickListener(this);
        week = (TextView) findViewById(R.id.tv_week);
        week.setOnClickListener(this);
        etName = (EditText) findViewById(R.id.et_shouhuoren);
        etMobile = (EditText) findViewById(R.id.et_Mobile);
        etDetialedAddress = (EditText) findViewById(R.id.et_xiangxidizhi);
        tvAddAddress = (TextView) findViewById(R.id.tv_address);
        tvAddAddress.setOnClickListener(this);

    }

    private void select(Spinner spin, ArrayAdapter<CharSequence> adapter, int arry) {
        adapter = ArrayAdapter.createFromResource(this, arry, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_back:
                finish();
                break;
            case R.id.pesonal_right:
                //点击完成执行操作
                if (isEdit) {
                    requestChangeAddress();
                } else {
                    addAddress();
                }
                break;
            case R.id.one_five:
                mPopWindow.dismiss();
                week.setText("星期一~星期五（工作日）");
                week.setTextSize(14);
                break;
            case R.id.six_seven:
                mPopWindow.dismiss();
                week.setText("星期六~星期日（休息日）");
                week.setTextSize(14);
                break;
            case R.id.one_seven:
                mPopWindow.dismiss();
                week.setText("星期一~星期日");
                week.setTextSize(14);
                break;
            case R.id.tv_week:
                popupChoose();
                break;
            case R.id.tv_address:
                popupAddress();
                break;
        }
    }
    String address = null;
    int cId = -1;
    private void popupAddress() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvAddAddress.getWindowToken(),InputMethodManager.HIDE_IMPLICIT_ONLY);
        CityDateModule.province = CityDao.getCityByPid(1);
        View contentView = LayoutInflater.from(this).inflate(R.layout.popuwindow_address, null);
        mPopWindow = new PopupWindow(contentView, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        mPopWindow.setFocusable(true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setAnimationStyle(R.style.popwin_anim_style);
        MyWheelView<CityModel> whProvince = (MyWheelView) contentView.findViewById(R.id.wheel_province);
        MyWheelView<CityModel> whCity = (MyWheelView) contentView.findViewById(R.id.wheel_city);
        MyWheelView<CityModel> whCounty = (MyWheelView) contentView.findViewById(R.id.wheel_county);

        Collections.sort(CityDateModule.province);
        whProvince.setWheelAdapter(new AddAddressWhellAdapter(this)); // 文本数据源
        whProvince.setSkin(WheelView.Skin.Holo); // common皮肤
        whProvince.setWheelData(CityDateModule.province);  // 数据集合
        whProvince.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener<CityModel>() {
            @Override
            public void onItemSelected(int i, CityModel cityModel) {
                List<CityModel> city = CityDao.getCityByPid(cityModel.getId());
                Collections.sort(city);
                Log.i(TAG,"city = "+city);
                whCity.setWheelData(city);
            }
        });


        List<CityModel> city = CityDao.getCityByPid(2);
        Collections.sort(city);
        whCity.setWheelAdapter(new AddAddressWhellAdapter(this)); // 文本数据源
        whCity.setSkin(WheelView.Skin.Holo); // common皮肤
//        List<CityModel> cityModels = CityDateModule.subMap.get(CityDateModule.province.get(whProvince.getSelection()).getName());
        List<CityModel> cityModels = CityDao.getCityByPid(2);
         Log.i(TAG,"province.get(  = "+CityDateModule.province.get(whProvince.getSelection()));
        Log.i(TAG,"cityMode = "+cityModels);
        whCity.setWheelData(cityModels);  // 数据集合
       /* whProvince.join(whCity);
        whProvince.joinDatas(CityDateModule.subMap);*/
        whCity.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener<CityModel>() {
            @Override
            public void onItemSelected(int i, CityModel cityModel) {
                List<CityModel> county = CityDao.getCityByPid(cityModel.getId());
                Collections.sort(county);
                whCounty.setWheelData(county);
            }
        });
        whCity.setOnWheelDataChangeListener(new MyWheelView.onWheelDataChangeListener() {
            @Override
            public void onWheelDataChanged(List datas, int position) {
                List<CityModel> cityByPid = CityDao.getCityByPid(((CityModel) datas.get(position)).getId());
                whCounty.setWheelData(cityByPid);
            }
        });


        List<CityModel> county = CityDao.getCityByPid(36);
        Collections.sort(county);
        whCounty.setWheelAdapter(new AddAddressWhellAdapter(this)); // 文本数据源
        whCounty.setSkin(WheelView.Skin.Holo); // common皮肤
        List<CityModel> cityByPid = CityDao.getCityByPid(52);
        whCounty.setWheelData(cityByPid);


        View rootview = LayoutInflater.from(this).inflate(R.layout.popupwindow_week, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                CityModel proCityModel = CityDateModule.province.get(whProvince.getCurrentPosition());
                CityModel cityCityModel = CityDao.getCityByPid(proCityModel.getId()).get(whCity.getCurrentPosition());
                CityModel countyCityModel = CityDao.getCityByPid(cityCityModel.getId()).get(whCounty.getCurrentPosition());
                cId = countyCityModel.getId();
                address = proCityModel.getName() +" "+cityCityModel.getName() +" "+countyCityModel.getName()+" ";
                Log.i(TAG,"address = "+address);
                Log.i(TAG,"cId = "+cId);
                tvAddAddress.setText(address);
            }
        });
    }


    private void requestChangeAddress() {
        RequestParams params = new RequestParams(Net.HOST + "addresses/" + addressBean.getId() + ".json");
        params.addHeader("Authorization", DictionaryTool.getToken(this));
        String name = etName.getText().toString();
        String mobile = etMobile.getText().toString();
        String detialedAddress = etDetialedAddress.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(mobile) || TextUtils.isEmpty(detialedAddress) || TextUtils.isEmpty(address)) {
            DialogUtil.dialogUser(this,"请填写完整信息");
            return;
        }
        addressBean.setAddress(address + detialedAddress.trim());
        addressBean.setMobile(mobile);
        addressBean.setConsignee(name);
        addressBean.setRegion_id(cId);
        String json = GsonUtil.toJson(addressBean);
        com.zangcun.store.utils.Log.i(TAG, "json = " + json);
        params.addBodyParameter("address", json);
        HttpUtils.HttpPutMethod(new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                ToastUtils.show(getApplicationContext(), "修改成功");
                setResult(101);
                finish();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                ToastUtils.show(getApplicationContext().getApplicationContext(), "修改失败");
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        }, params);
    }

    private void addAddress() {
        String name = etName.getText().toString();
        String mobile = etMobile.getText().toString();
        String detialedAddress = etDetialedAddress.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(mobile) || TextUtils.isEmpty(address)) {
            DialogUtil.dialogUser(this,"请填写完整信息");
            return;
        }
        AddressModel addressModel = new AddressModel(address +detialedAddress.trim(), mobile, cId + "", name);
        requestAddAddress(addressModel);
    }

    private void requestAddAddress(AddressModel addressModel) {
        String json = GsonUtil.toJson(addressModel);
        Log.i(TAG, "json =" + json);
        RequestParams params = new RequestParams(Net.URL_ADD_ADDRESSES);
        params.addBodyParameter("address", json);
        params.addHeader("Authorization", DictionaryTool.getToken(getApplicationContext()));
        params.addHeader("Content-Type", "application/json");
        HttpUtils.HttpPostMethod(new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "requestAddAddress onSuccess = " + s);
                setResult(100);
                finish();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i(TAG, "requestAddAddress onError = " + throwable.toString());
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        }, params);
    }
}
