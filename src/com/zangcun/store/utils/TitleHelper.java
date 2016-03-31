package com.zangcun.store.utils;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import com.zangcun.store.R;


//控制标题栏的工具类
public class TitleHelper {
    //设置没有返回键的标题栏
    public static void initTitle(View tiele, String text) {
        tiele.findViewById(R.id.goods_back).setVisibility(View.INVISIBLE);
        tiele.findViewById(R.id.goods_right).setVisibility(View.INVISIBLE);
        TextView titleTv = (TextView) tiele.findViewById(R.id.title);
        titleTv.setText(text);
    }

    //设置带有返回键的标题栏，并添加点击监听
    public static void initTitle(Activity ac, View title, String text) {
        title.findViewById(R.id.goods_back).setVisibility(View.VISIBLE);
        title.findViewById(R.id.goods_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ac.finish();
            }
        });
        title.findViewById(R.id.goods_right).setVisibility(View.INVISIBLE);
        TextView tv = (TextView) title.findViewById(R.id.tv_top_title);
        tv.setText(text);
    }
    //页面没有标题栏
    public  static  void  noTitle(View title){
        title.findViewById(R.id.title_group).setVisibility(View.GONE);
    }
}
