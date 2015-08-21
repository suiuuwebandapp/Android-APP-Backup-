package com.minglang.suiuu.dbhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/8/21 17:50
 * 修改人：Administrator
 * 修改时间：2015/8/21 17:50
 * 修改备注：
 */
public class TripGalleryCacheUtils {
    public static  String getTripGalleryCacheData(Context context) {
        TripGalleryCacheDbHelper helper = new TripGalleryCacheDbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select data from tripgallerycache", null);
        String data = null;
        while(cursor.moveToNext()) {

            data = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return data;
    }
    public static void insertTripGalleryCache(Context context,String  time,String data){
        TripGalleryCacheDbHelper helper = new TripGalleryCacheDbHelper(context);
        //得到可读可写数据库
        SQLiteDatabase  db =  helper.getReadableDatabase();
        //执行sql语句
        db.execSQL("insert into tripgallerycache (time,data) values (?,?)", new Object[]{time,data});
        db.close();
    }
}
