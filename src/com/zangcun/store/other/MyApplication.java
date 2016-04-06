package com.zangcun.store.other;

import android.app.Application;
import com.zangcun.store.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import org.xutils.x;

//图片加载器
public class MyApplication extends Application {
    public static DisplayImageOptions sImageNoRoundOptions;
    public static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration build = new ImageLoaderConfiguration.Builder(this).memoryCacheSizePercentage(10)
                .memoryCacheExtraOptions(480, 800)
                .diskCacheExtraOptions(480, 800, null)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(200)
                .build();

        ImageLoader.getInstance().init(build);

        sImageNoRoundOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.sp_icon_zw)
                .showImageOnFail(R.drawable.sp_icon_zw)
                .showImageOnLoading(R.drawable.sp_icon_zw)
                .build();
        x.Ext.init(this);
        x.Ext.setDebug(true);
        instance = this;
    }
}
