package com.shopex.phone.phone.common;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.view.WindowManager;


import com.shopex.phone.phone.bean.UserInfo;
import com.shopex.phone.phone.db.DaoMaster;
import com.shopex.phone.phone.db.DaoSession;
import com.shopex.phone.phone.library.constants.AppConstants;
import com.shopex.phone.phone.library.db.DaoHelp;
import com.shopex.phone.phone.utils.MyDateBaseHelper;
import com.shopex.phone.phone.utils.loghelp.LogUtil;


/**
 * Created by samsung on 2016/1/6.
 * 全局配置信息类
 */
public class BaseApplication extends Application{
    private WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
    private static BaseApplication instance=null;
    public static DaoMaster daoMaster=null;
    public static DaoSession daoSession=null;

//用户表
    public static DaoMaster daoMasterUser=null;
    public static DaoSession daoSessionUser=null;
    //联系人数据库
    public static MyDateBaseHelper helper=null;
    public static SQLiteDatabase db = null;
    //短信数据库
    public static MyDateBaseHelper smsHelper=null;
    public static SQLiteDatabase smsDb=null;

    //用户信息
    public static MyDateBaseHelper userHelper=null;
    public static SQLiteDatabase usersDb=null;


    public static LogUtil writeUtil;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=BaseApplication.this;
        daoMaster= DaoHelp.getDaoMaster(getApplicationContext());
        daoSession=DaoHelp.getDaoSession(getApplicationContext());

        daoMasterUser= DaoHelp.getDaoMasterUser(getApplicationContext());
        daoSessionUser=DaoHelp.getDaoSessionUser(getApplicationContext());
        UserInfo.getInstance();

        initdb();

//        writeUtil=new LogUtil(getApplicationContext());
//        writeUtil.createView();

    }
    public static BaseApplication getInstance(){
        return instance;
    }

    public WindowManager.LayoutParams getWindowParams() {
        return windowParams;
    }
    public static void initdb(){
        helper=new MyDateBaseHelper(instance, AppConstants.DB_NAME_CONTRACT,null,1);
        db=helper.getReadableDatabase();

        smsHelper=new MyDateBaseHelper(instance,AppConstants.DB_NAME_SMS,null,1);
        smsDb=smsHelper.getReadableDatabase();

        userHelper=new MyDateBaseHelper(instance,AppConstants.DB_NAME_USER,null,1);
        usersDb=smsHelper.getReadableDatabase();

    }


}
