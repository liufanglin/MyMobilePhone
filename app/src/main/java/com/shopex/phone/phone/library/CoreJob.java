package com.shopex.phone.phone.library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.shopex.phone.phone.activity.LoginActivity;
import com.shopex.phone.phone.bean.UserInfo;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.common.BaseApplication;
import com.shopex.phone.phone.library.constants.AppConstants;
import com.shopex.phone.phone.library.db.DaoHelp;
import com.shopex.phone.phone.library.toolbox.PreferencesUtils;

import java.util.Vector;

/**
 * Created by samsung on 2016/1/6.
 * 核心处理类初始化一些操作
 * 如数据库等
 */
public class CoreJob {
    private static Vector<Activity> activities=new Vector<>();
    public static boolean isExiting=false;

    public static void init(Context context){

    }



    //添加activity到集合
    public static synchronized  void addToActivityStack(Activity activity){
        if (activities==null){
            activities=new Vector<>();
        }
        if (!isExiting){
            activities.add(activity);
        }

    }
    //移除出这个集合
    public static synchronized  void removeFormActivityStack(Activity activity){
        if (activities!=null&&!isExiting){
            activities.remove(activity);
        }

    }

    //退出应用
    public static void exitApplication(boolean goLogin,Context context) {
        isExiting = true;
        AppConstants.name = null;
        AppConstants.isLogin = false;
        if (activities != null) {
            for (Activity activity : activities) {
                activity.finish();
            }
        }
        if (goLogin) {
            UserInfo.instance=null;
            PreferencesUtils.setString(BaseApplication.getInstance(), "loginpwd", null);
            Intent intent=new Intent(context,LoginActivity.class);
            context.startActivity(intent);
        }else {
            System.exit(0);
        }
    }




}

