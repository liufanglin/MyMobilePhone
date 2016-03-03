package com.shopex.phone.phone.library.toolbox;

import android.content.Context;
import android.content.SharedPreferences;

import com.shopex.phone.phone.library.constants.AppConstants;

/**
 * Created by samsung on 2016/1/7.
 * 设置配置文件
 *
 */
public class PreferencesUtils {
    private static String PREFERENCE_NAME= AppConstants.PREFERENCE_NAME;

    public static boolean setString(Context context,String key,String name){
        SharedPreferences preferences=context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(key,name);
        return editor.commit();
    }
    public static String getString(Context context,String key,String defaultValue){
        SharedPreferences preferences=context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return preferences.getString(key, defaultValue);
    }
    public static String getString(Context context,String key){
        return getString(context,key,null);
    }
    public static boolean setInt(Context context,String key,int name){
        SharedPreferences preferences=context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt(key, name);
        return editor.commit();
    }
    public static int getInt(Context context,String key,int defaultValue){
        SharedPreferences preferences=context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(key, defaultValue);
    }
    public static int getInt(Context context,String key){
        return getInt(context, key, -1);
    }
    public static boolean setBoolean(Context context,String key,boolean name){
        SharedPreferences preferences=context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean(key, name);
        return editor.commit();
    }
    public static boolean getBoolean(Context context,String key,boolean defaultValue){
        SharedPreferences preferences=context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defaultValue);
    }
    public static boolean getBoolean(Context context,String key){
        return getBoolean(context, key, false);
    }



}
