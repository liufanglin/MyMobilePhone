package com.shopex.phone.phone.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.text.format.Formatter;
import android.util.Log;

import com.shopex.phone.phone.library.toolbox.LogUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by samsung on 2016/3/3.
 */
public class ReadSystemMemaryUtil {
    //获取系统可用内存
    public static String getAvailMemory(Context context) {// 获取android当前可用内存大小

        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        //mi.availMem; 当前系统的可用内存

        return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
    }
//获取系统总共内存
    public static String getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;

        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小


            str2 = str2.replaceAll("[^0-9]","");
         /*   arrayOfString = str2.split("//s+");
            for (String num : arrayOfString) {
                LogUtils.instance.i( num + "/t");
            }*/

         //   initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            initial_memory = Integer.valueOf(str2).intValue() * 1024;
            Log.i("------------=========",initial_memory+"");
            localBufferedReader.close();

        } catch (IOException e) {
        }
        return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }
}
