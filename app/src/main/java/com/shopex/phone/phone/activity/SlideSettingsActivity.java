package com.shopex.phone.phone.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.library.constants.AppConstants;

public class SlideSettingsActivity extends BaseActivity {


    private LinearLayout resetLayout=null;
    private LinearLayout aboutLayout=null;
    private LinearLayout msgLayout=null;
    private Button exitBtn=null;
   // private TextView versionText;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_setting);
        setLeftBackImageText("设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        resetLayout= (LinearLayout) findViewById(R.id.reset_pwd);
        aboutLayout= (LinearLayout) findViewById(R.id.about);
        msgLayout= (LinearLayout) findViewById(R.id.msg_notify);
        exitBtn= (Button) findViewById(R.id.exit);
//        versionText= (TextView) findViewById(R.id.version);
//        versionText.setText("版本号："+PackageUtils.getAppVersionName(this));

        resetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SlideSettingsActivity.this, ResetPwdActivity.class);
                startActivity(intent);
            }
        });
        aboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SlideSettingsActivity.this,ResetPwdActivity.class);
                startActivity(intent);
            }
        });
        msgLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SlideSettingsActivity.this,ResetPwdActivity.class);
                startActivity(intent);
            }
        });
        
        
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //同步状态
               finish();
                AppConstants.name=null;
                AppConstants.isLogin=false;
                Intent intent=new Intent(SlideSettingsActivity.this,LoginActivity.class);
                startActivity(intent);


            }
        });


    }
    


}
