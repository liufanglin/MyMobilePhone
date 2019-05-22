package com.shopex.phone.phone.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by samsung on 2016/1/14.
 * 数据库帮助类
 */
public class MyDateBaseHelper extends SQLiteOpenHelper{
    //name  代表数据库文件的名字xx.db
    //factory 游标的工厂对象  一般使用默认的参数  为null
    //version  定义的数据库的版本,我的应用中我所使用的版本
    public MyDateBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //所有表的建表语句
    //联系人  如果是黑名单中的type=1 所有备份的type=0；
    private String sql="create table contract_info(_id integer primary key autoincrement,name,phone,type)";
    //所有短信  如果是黑名单的type =1  所有备份的type=0；
    private String smssql="create table sms_info(_id integer primary key autoincrement,name,phone,content,time,type)";
    //用户表
    private String usersql="create table user_info(_id integer primary key autoincrement,name,pwd,firstname,lastname,bir,hight,weight,gender,address,leave)";
    //构造器创建数据
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
        db.execSQL(smssql);
        db.execSQL(usersql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
