package com.shopex.phone.phone.library.toolbox;

import android.content.Context;
import android.graphics.Rect;
import android.nfc.Tag;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;

import com.shopex.phone.phone.common.BaseApplication;
import com.shopex.phone.phone.library.constants.AppConstants;
import com.shopex.phone.phone.utils.loghelp.LogUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by samsung on 2016/1/6.
 * 打印日志。并且加上了标签，行号，消息，方法
 */
public class LogUtils {
    public static LogUtils instance=new LogUtils();
    public void i(String msg){
        if (AppConstants.DEBUG){
            String tempStr=getFunctionName();
            if (!TextUtils.isEmpty(tempStr)){
                msg=msg+"[++++++++]"+tempStr;
            }
            Log.i(AppConstants.TAG,msg);
          //  writeLogtoFile(AppConstants.TAG,msg);
            LogUtil.writeLogtoFile(AppConstants.TAG, msg);
        }
    }
    public void e(String msg){
        if (AppConstants.DEBUG){
            String tempStr=getFunctionName();
            if (!TextUtils.isEmpty(tempStr)){
                msg=msg+"[++++++++]"+tempStr;
            }
            Log.e(AppConstants.TAG, msg);
          //  writeLogtoFile(AppConstants.TAG, msg);
            LogUtil.writeLogtoFile(AppConstants.TAG,msg);
        }

    }
    public void v(String msg){
        if (AppConstants.DEBUG){
            String tempStr=getFunctionName();
            if (!TextUtils.isEmpty(tempStr)){
                msg=msg+"[++++++++]"+tempStr;
            }
            Log.v(AppConstants.TAG, msg);
        //    writeLogtoFile(AppConstants.TAG, msg);
            LogUtil.writeLogtoFile(AppConstants.TAG,msg);
        }
    }
    public void d(String msg){
        if (AppConstants.DEBUG){
            String tempStr=getFunctionName();
            if (!TextUtils.isEmpty(tempStr)){
                msg=msg+"[++++++++]"+tempStr;
            }
            Log.d(AppConstants.TAG, msg);
        //    writeLogtoFile(AppConstants.TAG, msg);
            LogUtil.writeLogtoFile(AppConstants.TAG,msg);
        }
    }
 /*   public static Boolean MYLOG_SWITCH=true; // 日志文件总开关
    public static Boolean MYLOG_WRITE_TO_FILE=true;// 日志写入文件开关
    private static String MYLOGFILEName = "log.txt";// 本类输出的日志文件名称
   public static final String path = "/sdcard/";



    private static void writeLogtoFile( String tag, String text) {// 新建或打开日志文件
        if (MYLOG_SWITCH&&MYLOG_WRITE_TO_FILE) {

            try {
                File file = new File(path, MYLOGFILEName);
               if (!file.exists()) {
                    file.createNewFile();
                } else {
                    FileWriter filerWriter = new FileWriter(file, true);//后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
                    BufferedWriter bufWriter = new BufferedWriter(filerWriter);
                    bufWriter.write(tag + "===" + text);
                    bufWriter.newLine();
                    bufWriter.close();
                    filerWriter.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    *//**
     * 删除制定的日志文件
     * *//*
    public static void delFile() {// 删除日志文件

        File file = new File(path, "log.txt");
        if (file.exists()) {
            file.delete();
        }
    }

*/




















    //获取当前行号等
    private String getFunctionName(){
        StackTraceElement[] sts=Thread.currentThread().getStackTrace();
        if (sts==null){
            return null;
        }
        for (StackTraceElement st:sts){
            if (st.isNativeMethod()){
                continue;
            }
            if (st.getClassName().equals(Thread.class.getName())){
                continue;
            }
            if (st.getClassName().equals(LogUtils.this.getClass().getName())){
                continue;
            }
            return "[ " + Thread.currentThread().getName() + ": "
                    + st.getFileName() + ":" + st.getLineNumber() + " "
                    + st.getMethodName() + " ]";
        }
        return null;


    }

}
