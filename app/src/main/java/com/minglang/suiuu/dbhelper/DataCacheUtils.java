package com.minglang.suiuu.dbhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：旅图随游数据库缓存的工具类 id = 1为旅途缓存数据,id=2为随游缓存数据
 * 创建人：Administrator
 * 创建时间：2015/8/21 17:50
 * 修改人：Administrator
 * 修改时间：2015/8/21 17:50
 * 修改备注：
 */
public class DataCacheUtils {
    //得到旅图缓存数据
    public static Map getCacheData(Context context,String id) {
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select id,time,data from tripgallerycache where id = "+id,  null);
        Map<String,String> map = new HashMap<>();
        while(cursor.moveToNext()) {
            map.put("id",cursor.getString(0));
            map.put("time",cursor.getString(1));
            map.put("data",cursor.getString(2));
        }
        cursor.close();
        db.close();
        return map;
    }
    //添加旅图数据缓存
    public static void insertCacheData(Context context,String  time,String data,String id){
        DbHelper helper = new DbHelper(context);
        //得到可读可写数据库
        SQLiteDatabase  db =  helper.getReadableDatabase();
        //执行sql语句
        db.execSQL("insert into tripgallerycache (id,time,data) values (?,?,?)", new Object[]{id,time, data});
        db.close();
    }
    //更新缓存
    public static void updateCacheData(Context context,String time,String id,String data){
        DbHelper helper = new DbHelper(context);
        //得到可读可写数据库
        SQLiteDatabase  db =  helper.getWritableDatabase();
        //执行sql语句
        db.execSQL("update tripgallerycache set time = ?,data = ? where id = ?", new Object[]{time,data,id});
        db.close();
    }
}
