package com.shopex.phone.phone.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.shopex.phone.phone.library.toolbox.LogUtils;
import com.shopex.phone.phone.library.toolbox.PreferencesUtils;

/**
 * Created by samsung on 2016/3/1.
 * 接收开机启动的状态，获取手机号
 */
public class OpenPhoneReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.instance.i("手机开机启动了");
        if (PreferencesUtils.getBoolean(context,"isbindsim",false)){
            TelephonyManager tm= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String phone=tm.getSimSerialNumber();
            //绑定的sim卡
            String bindPhone=PreferencesUtils.getString(context,"bindphone");
            //这是获取的亲情号
            String sendPhone=PreferencesUtils.getString(context,"phone");
            if (phone.equals(bindPhone)){
                LogUtils.instance.i("sim 卡没有发生变化");
            } else {
                SmsManager smsManager=SmsManager.getDefault();
                smsManager.sendTextMessage(sendPhone,null,"您的手机正在被手机号为"+sendPhone+"的人使用",null,null);
            }
        }


    }
}
