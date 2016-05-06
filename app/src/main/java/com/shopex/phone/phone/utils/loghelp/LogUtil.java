package com.shopex.phone.phone.utils.loghelp;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.activity.DialogActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**使用步骤   1：必须有LogUtil ，DialogActivity(集成Activity  不要继承我们基类的，否则会因为设置了dialog风格导致异常),要有ic_launcher图片
 *
 * 2.在AndroidManifes中注册DialogActivity(风格是对话框风格，栈顶单例模式，防止点击悬浮框重复创建)
 *   <activity android:name=".activity.DialogActivity"
 android:theme="@style/Theme.AppCompat.Dialog"
 android:launchMode="singleTop"
 android:screenOrientation="portrait"></activity>
 要加入权限：
 <!-- 往SDCard写入数据权限 -->
 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 <!-- 在SDCard中创建与删除文件权限 -->
 <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" />
 <!-- 修改窗体权限 -->
 <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
 <uses-permission android:name="android.permission.GET_TASKS"/>

 3.在Application 类里面new出LogUtil类  LogUtil util=new LogUtil(getApplicationContext)  util.createView();
 4.在你的输出日志工具类中调用LogUtil.writeLogtoFile();
 4.如果要退出应用时候关闭悬浮框，就调用  LogUtil 中的dismiss()方法

 *
 * Created by samsung on 2016/5/5.
 */
public class LogUtil {
    public static WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
    public static Boolean MYLOG_SWITCH=false; // 日志文件总开关
    private static String MYLOGFILEName = "log.txt";// 本类输出的日志文件名称
    public static final String path = "/sdcard/";
    public Context context;
    private WindowManager windowManager = null;
    private WindowManager.LayoutParams windowManagerParams = null;
    private FloatView floatView = null;

    public LogUtil(Context context){
        this.context=context;
    }
    public static void writeLogtoFile( String tag, String text) {// 新建或打开日志文件

        if (MYLOG_SWITCH) {
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


//创建出悬浮窗
    public void createView() {
        if (floatView == null) {
            floatView = new FloatView(context.getApplicationContext());
            floatView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Intent intent =new Intent(context,DialogActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                }
            });
            floatView.setImageResource(R.mipmap.ic_launcher); // 这里简单的用自带的icon来做演示
            // 获取WindowManager
            windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            // 设置LayoutParams(全局变量）相关参数
            windowManagerParams = windowParams;
            windowManagerParams.type = WindowManager.LayoutParams.TYPE_PHONE; // 设置window type
            windowManagerParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
            // 设置Window flag
            windowManagerParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            /*
            * 注意，flag的值可以为：
            * LayoutParams.FLAG_NOT_TOUCH_MODAL 不影响后面的事件
            * LayoutParams.FLAG_NOT_FOCUSABLE 不可聚焦
            * LayoutParams.FLAG_NOT_TOUCHABLE 不可触摸
            */
            // 调整悬浮窗口至左上角，便于调整坐标
            windowManagerParams.gravity = Gravity.LEFT | Gravity.TOP;
            // 以屏幕左上角为原点，设置x、y初始值
            windowManagerParams.x = 0;
            windowManagerParams.y = 0;
            // 设置悬浮窗口长宽数据
            windowManagerParams.width = 80;
            windowManagerParams.height = 80;
            // 显示myFloatView图像
            windowManager.addView(floatView, windowManagerParams);
        }
    }


    //在什么时候让悬浮窗隐藏
    public void dismiss(){
        // 在程序退出(Activity销毁）时销毁悬浮窗口
        windowManager.removeView(floatView);
    }

    //悬浮窗组件
    class FloatView extends ImageView {
        private float mTouchX;
        private float mTouchY;
        private float x;
        private float y;
        private float mStartX;
        private float mStartY;
        private OnClickListener mClickListener;
        private WindowManager windowManager = (WindowManager) getContext()
                .getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        // 此windowManagerParams变量为获取的全局变量，用以保存悬浮窗口的属性
        private WindowManager.LayoutParams windowManagerParams = windowParams;
        public FloatView(Context context) {
            super(context);
        }
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            //获取到状态栏的高度
            Rect frame = new Rect();
            getWindowVisibleDisplayFrame(frame);
            int statusBarHeight = frame.top;
            System.out.println("statusBarHeight:"+statusBarHeight);
            // 获取相对屏幕的坐标，即以屏幕左上角为原点
            x = event.getRawX();
            y = event.getRawY() - statusBarHeight; // statusBarHeight是系统状态栏的高度
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: // 捕获手指触摸按下动作
                // 获取相对View的坐标，即以此View左上角为原点
                    mTouchX = event.getX();
                    mTouchY = event.getY();
                    mStartX = x;
                    mStartY = y;
                    break;
                case MotionEvent.ACTION_MOVE: // 捕获手指触摸移动动作
                    updateViewPosition();
                    break;
                case MotionEvent.ACTION_UP: // 捕获手指触摸离开动作
                    updateViewPosition();
                    mTouchX = mTouchY = 0;
                    if ((x - mStartX) < 5 && (y - mStartY) < 5) {
                        if(mClickListener!=null) {
                            mClickListener.onClick(this);
                        }
                    }
                    break;
            }
            return true;
        }
        @Override
        public void setOnClickListener(OnClickListener l) {
            this.mClickListener = l;
        }
        private void updateViewPosition() {
            // 更新浮动窗口位置参数
            windowManagerParams.x = (int) (x - mTouchX);
            windowManagerParams.y = (int) (y - mTouchY);
            windowManager.updateViewLayout(this, windowManagerParams); // 刷新显示
        }

    }

}
