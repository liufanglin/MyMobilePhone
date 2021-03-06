package com.shopex.phone.phone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.shopex.phone.phone.MainActivity;
import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.common.BaseApplication;
import com.shopex.phone.phone.common.RootActivity;
import com.shopex.phone.phone.library.constants.AppConstants;
import com.shopex.phone.phone.library.toolbox.PreferencesUtils;


/**
 * Created by samsung on 2016/1/7.
 * 闪屏页
 */
public class SplashActivity extends RootActivity{
    public static String ISFIRSTOPEN="isFirstOpen";
    private ImageView splashImage;
    private Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                //  getApplicationContext().startActivity(intent);
                finish();
//                if (PreferencesUtils.getBoolean(SplashActivity.this,ISFIRSTOPEN)){
//
//                }else {
//                    Intent intent=new Intent(SplashActivity.this,GuidePageActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    finish();
//                }
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashImage= (ImageView) findViewById(R.id.iv_splash);
        splashImage.setImageResource(AppConstants.SPLASH);
        handler.sendEmptyMessageDelayed(0,3000);


    }
}
