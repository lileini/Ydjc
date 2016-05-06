package com.zangcun.store.model;

import com.zangcun.store.dao.CityDao;
import com.zangcun.store.utils.Log;

import java.util.*;

/**
 * Created by Administrator on 2016/5/6.
 */
public class CityDateModule extends Thread {
    public static List<CityModel> province;
    public static HashMap<String,List<CityModel>> subMap = null;
    public static HashMap<String,List<CityModel>> childMap = null;
    private String TAG = "CityDateModule";

    private final HashMap<String,List<CityModel>> createSubDate(){
        if (subMap != null)
            return subMap;
        subMap = new HashMap<>();
        for (CityModel cityModel:province){
            int id = cityModel.getId();
//            Log.i(TAG,"id = "+ id);
            List<CityModel> citys = CityDao.getCityByPid(cityModel.getId());
//            Log.i(TAG,"citys = "+citys);
            if (citys == null)
                continue;
            subMap.put(cityModel.getName(),citys);
        }
        return subMap;
    }
    private final HashMap<String,List<CityModel>> createChildDate(){
        if (childMap != null)
            return childMap;
        childMap = new HashMap<>();
        Collection<List<CityModel>> values = createSubDate().values();
        Iterator<List<CityModel>> iterator = values.iterator();
        List<CityModel> cityModels = new ArrayList<>();
        while (iterator.hasNext()){
            cityModels.addAll(iterator.next());
        }
        for (CityModel cityModel:cityModels){
            int id = cityModel.getId();
//            Log.i(TAG,"id = "+ id);
            List<CityModel> citys = CityDao.getCityByPid(cityModel.getId());
//            Log.i(TAG,"citys = "+citys);
            if (citys == null)
                continue;
            childMap.put(cityModel.getName(),citys);
        }
        return childMap;
    }

    @Override
    public void run() {
        province = CityDao.getCityByPid(1);
        createSubDate();
        createChildDate();
        Log.i(TAG,"subMap = "+subMap.toString());
        Log.i(TAG,"childMap = "+childMap.toString());
    }
}
