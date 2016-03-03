package com.shopex.phone.phone.library;

import android.app.Activity;

import java.util.Vector;

/**
 * Created by samsung on 2016/1/6.
 * 核心处理类初始化一些操作
 * 如数据库等
 */
public class CoreJob {
    private static Vector<Activity> activities=new Vector<>();
    public static boolean isExiting=false;
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
    public static void exitApplication(boolean goLogin){
        isExiting=true;
        if (activities!=null){
            for (Activity activity: activities){
                activity.finish();
            }
        }
    }


}

