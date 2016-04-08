package com.zangcun.store.dao;

import com.zangcun.store.model.CityModel;
import com.zangcun.store.utils.Log;
import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2016/4/8.
 */
public class CityDao {
    private static DbManager db;
    static DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbName("zangcun.db")
            // 不设置dbDir时, 默认存储在app的私有目录.
            .setDbVersion(1)
            .setDbOpenListener(new DbManager.DbOpenListener() {
                @Override
                public void onDbOpened(DbManager db) {
                    // 开启WAL, 对写入加速提升巨大
                    db.getDatabase().enableWriteAheadLogging();
                }
            })
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                    // TODO: ...
                    // db.addColumn(...);
                    // db.dropTable(...);
                    // ...
                    // or
                    // db.dropDb();
                }
            });
    private static String TAG = "CityDao";

    public static DbManager getDbManger(){
        if(db == null)
         db = x.getDb(daoConfig);
        return db;
    }
    public static void saveCityList(List<CityModel> cityModelList){
        try {
            getDbManger().save(cityModelList);
            Log.i(TAG, "saveCityList success");
        } catch (DbException e) {
            e.printStackTrace();
            Log.i(TAG, "saveCityList fail");
        }
    }
    public static void deleteCityList(){
        try {
            getDbManger().delete(CityModel.class);
            Log.i(TAG, "deleteCityList success");
        } catch (DbException e) {
            e.printStackTrace();
            Log.i(TAG, "deleteCityList fail");
        }
    }

    public static CityModel findCity(int id){
        CityModel cityModel = null;
        try {
            cityModel = getDbManger().selector(CityModel.class).where("_id", "=", id).findFirst();
            Log.i(TAG, "findCity id  success");
        } catch (DbException e) {
            e.printStackTrace();
            Log.i(TAG, "findCity id fail");
        }
        return cityModel;
    }
    public static CityModel findCity(String name){
        CityModel cityModel = null;
        try {
            cityModel= getDbManger().selector(CityModel.class).where("_name","=",name).findFirst();
//            cityModel= getDbManger()
            Log.i(TAG, "findCity name  success");
        } catch (DbException e) {
            e.printStackTrace();
            Log.i(TAG, "findCity name fail");
        }
        return cityModel;
    }
    public static CityModel findCity(String name,int pid){
        CityModel cityModel = null;
        try {
            cityModel= getDbManger().selector(CityModel.class).where("_name","=",name).and("_pid","=",pid).findFirst();
//            cityModel= getDbManger()
            Log.i(TAG, "findCity name  success");
        } catch (DbException e) {
            e.printStackTrace();
            Log.i(TAG, "findCity name fail");
        }
        return cityModel;
    }
}
