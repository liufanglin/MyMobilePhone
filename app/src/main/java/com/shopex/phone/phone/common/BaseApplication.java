package com.shopex.phone.phone.common;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;


import com.shopex.phone.phone.db.DaoMaster;
import com.shopex.phone.phone.db.DaoSession;
import com.shopex.phone.phone.library.constants.AppConstants;
import com.shopex.phone.phone.library.db.DaoHelp;
import com.shopex.phone.phone.utils.MyDateBaseHelper;


/**
 * Created by samsung on 2016/1/6.
 * 全局配置信息类
 */
public class BaseApplication extends Application{
    private static BaseApplication instance=null;
    public static DaoMaster daoMaster=null;
    public static DaoSession daoSession=null;
    //联系人数据库
    public static MyDateBaseHelper helper=null;
    public static SQLiteDatabase db = null;
    //短信数据库
    public static MyDateBaseHelper smsHelper=null;
    public static SQLiteDatabase smsDb=null;



    @Override
    public void onCreate() {
        super.onCreate();
        instance=BaseApplication.this;
        daoMaster= DaoHelp.getDaoMaster(getApplicationContext());
        daoSession=DaoHelp.getDaoSession(getApplicationContext());
        initdb();

    }
    public static BaseApplication getInstance(){
        return instance;
    }

    public static void initdb(){
        helper=new MyDateBaseHelper(instance, AppConstants.DB_NAME_CONTRACT,null,1);
        db=helper.getReadableDatabase();
        smsHelper=new MyDateBaseHelper(instance,AppConstants.DB_NAME_SMS,null,1);
        smsDb=smsHelper.getReadableDatabase();

    }


}
