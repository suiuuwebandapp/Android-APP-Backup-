package com.minglang.suiuu.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/6/23 13:37
 * 修改人：Administrator
 * 修改时间：2015/6/23 13:37
 * 修改备注：
 */
public class UserDbHelper extends SQLiteOpenHelper {

    public UserDbHelper(Context context) {
        //context 上下文
        //name 数据库的名称
        //factory 游标（指针）工厂 null默认工厂
        //version 从1开始的
        super(context, "suiuu.db", null, 1);
    }

    //数据库第一次被创建的时候调用的方法。适合数据库表结构的初始化
    //如果数据库已经被创建了。oncreate方法就不会被重新调用了。
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("oncreate 数据库被第一次创建了");
        //执行sql语句
        db.execSQL("create table user (userid varchar(30), nikename varchar(50), titleimg varchar(50))");
    }

    //数据库升级调用的方法。数据库的版本号增加的时候调用。
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("onupgrade 数据库被升级了");
    }

}
