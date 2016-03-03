package com.shopex.phone.phone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.shopex.phone.phone.MainActivity;
import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.RootActivity;
import com.shopex.phone.phone.library.toolbox.PreferencesUtils;


/**
 * Created by samsung on 2016/1/7.
 * 闪屏页
 */
public class SplashActivity extends RootActivity{
    public static String ISFIRSTOPEN="isFirstOpen";
    private Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                if (PreferencesUtils.getBoolean(SplashActivity.this,ISFIRSTOPEN)){
                    Intent intent=new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent=new Intent(SplashActivity.this,GuidePageActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.sendEmptyMessageDelayed(0,3000);

    }
}
