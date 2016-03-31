package com.zangcun.store.utils;


import android.app.Activity;
import android.util.SparseArray;
import android.view.View;

//封装找ID的方法
public class UI {


    private Object item;


    private UI(Object obj) {
        item = obj;
    }

    public static UI create(View view) {
        return new UI(view);
    }

    public static UI create(Activity ac) {
        return new UI(ac);
    }

    public <T> T finById(int id) {
        View view = null;
        if (item instanceof Activity) {
            view = ((Activity) item).findViewById(id);
        } else if (item instanceof View) {
            view = ((View) item).findViewById(id);
        }
        return (T) view;
    }

    //得到holder,用于listview的优化
    public <T extends View> T getHolder(int id) {
        if (!(item instanceof View)) throw new ClassCastException("only View can be used");
        View view = (View) item;
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;

    }

}

