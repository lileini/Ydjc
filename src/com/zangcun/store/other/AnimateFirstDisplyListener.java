package com.zangcun.store.other;

import android.graphics.Bitmap;
import android.view.View;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//图片第一次显示的动画
public class AnimateFirstDisplyListener extends SimpleImageLoadingListener {
    //线程不安全的LinkedList  ,通过 Collections.synchronizedList(new LinkedList<String>())就是安全的了
    private static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
    // 集合保存图片状态是否动画过

    //绝对安全的单利模式
    private volatile static AnimateFirstDisplyListener sSelf;

    //volatile,轻量级的锁，线程安全
    private AnimateFirstDisplyListener() {

    }

    //保证只能有一个对象进来
    public static AnimateFirstDisplyListener getInstence() {
        if (sSelf == null) {
            synchronized (AnimateFirstDisplyListener.class) {
                if (sSelf == null) {
                    sSelf = new AnimateFirstDisplyListener();
                }
            }
        }
        return sSelf;
    }


    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        if (loadedImage == null) return;
        boolean isFirst = !displayedImages.contains(imageUri);//不是第一次加载的时候就不渐变
        if (isFirst) {
            FadeInBitmapDisplayer.animate(view, 1000);//加一个动画，如果是第一次加载
            displayedImages.add(imageUri);//把网址加载到集合，
        }


    }
}
