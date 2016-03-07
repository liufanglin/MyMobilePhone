package com.shopex.phone.phone.receiver;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Build;
import android.telephony.SmsMessage;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.BaseApplication;
import com.shopex.phone.phone.library.constants.AppConstants;
import com.shopex.phone.phone.library.toolbox.LogUtils;
import com.shopex.phone.phone.service.LocationService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by samsung on 2016/1/15.
 * 拦截短信的广播接收着
 */
public class SmsReceiver extends BroadcastReceiver{
    private NotificationManager nm;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        //获取超级管理员权限
        DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        String action=intent.getAction();
        if(action.equals("android.provider.Telephony.SMS_RECEIVED")) {
            LogUtils.instance.i("11-------------------");
            Object[] obj = (Object[]) intent.getExtras().get("pdus");
            //创建一个短信对象
            SmsMessage[] sms = new SmsMessage[obj.length];
            for (int i = 0; i < obj.length; i++) {
                sms[i] = SmsMessage.createFromPdu((byte[]) obj[i]);
            }
            for (SmsMessage temp : sms) {
                String content = temp.getDisplayMessageBody();
                if (content.equals("loadlocation")){
                    Intent intent1=new Intent(context, LocationService.class);
                    context.startActivity(intent1);
                    abortBroadcast();
                }if (content.equals("startmusic")){
                 //   MediaPlayer player = MediaPlayer.create(context, android.support.v7.appcompat.R.r);
                  //  player.setVolume(1.0f, 1.0f);
                   // player.start();
                   // abortBroadcast();
                }if (content.equals("cleardata")){
                    dpm.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
                    abortBroadcast();

                }if (content.equals("localscree")){
                    dpm.resetPassword("123", 0);
                    dpm.lockNow();
                    abortBroadcast();
                }

                //获取发短信的人
                String phone = temp.getDisplayOriginatingAddress();
                LogUtils.instance.i("22-------------------"+phone);
                //获取发短信的时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                long time = temp.getTimestampMillis();
                String dd = sdf.format(new Date(time));
                List list =selectPhone();
                for (int i=0;i<list.size();i++){
                    LogUtils.instance.i("3333-------------------");
                    if (phone.equals(list.get(i))){
                        //把拦截的短信添加到短信数据库
                        LogUtils.instance.i("444-------------------"+phone);
                        BaseApplication.smsDb.execSQL("insert into sms_info(phone,content,time) values(?,?,?)",
                        new Object[]{phone,content,time});
                        abortBroadcast();
                    }
                }
            }
        }

    }
    //读取联系人
    public List<String> selectPhone() {
        List<String> list = new ArrayList<>();
        Cursor cursor = BaseApplication.db.query(AppConstants.TABLE, new String[]{"phone"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String number = cursor.getString(0);
            list.add(number);
        }
        return list;
    }
    //保存拦截的短信到本地数据库


}
