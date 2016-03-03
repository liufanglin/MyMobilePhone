package com.shopex.phone.phone.library.toolbox;

import android.text.TextUtils;
import android.util.Log;

import com.shopex.phone.phone.library.constants.AppConstants;

/**
 * Created by samsung on 2016/1/6.
 * 打印日志。并且加上了标签，行号，消息，方法
 */
public class LogUtils {
    public static LogUtils instance=new LogUtils();
    private LogUtils(){};
    public void i(String msg){
        if (AppConstants.DEBUG){
            String tempStr=getFunctionName();
            if (!TextUtils.isEmpty(tempStr)){
                msg=msg+"[++++++++]"+tempStr;
            }
            Log.i(AppConstants.TAG,msg);
        }
    }
    public void e(String msg){
        if (AppConstants.DEBUG){
            String tempStr=getFunctionName();
            if (!TextUtils.isEmpty(tempStr)){
                msg=msg+"[++++++++]"+tempStr;
            }
            Log.e(AppConstants.TAG, msg);
        }
    }
    public void v(String msg){
        if (AppConstants.DEBUG){
            String tempStr=getFunctionName();
            if (!TextUtils.isEmpty(tempStr)){
                msg=msg+"[++++++++]"+tempStr;
            }
            Log.v(AppConstants.TAG, msg);
        }
    }
    public void d(String msg){
        if (AppConstants.DEBUG){
            String tempStr=getFunctionName();
            if (!TextUtils.isEmpty(tempStr)){
                msg=msg+"[++++++++]"+tempStr;
            }
            Log.d(AppConstants.TAG, msg);
        }
    }


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
