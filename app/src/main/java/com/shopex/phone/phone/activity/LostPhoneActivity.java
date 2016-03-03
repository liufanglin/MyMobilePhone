package com.shopex.phone.phone.activity;


import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.library.toolbox.PreferencesUtils;

import java.security.KeyStore;

/**
 * Created by samsung on 2016/2/29.
 * 手机防盗界面
 */
public class LostPhoneActivity extends BaseActivity{
    private LinearLayout settingPwdLinear;
    private LinearLayout bindSimLinear;
    private LinearLayout unBindSimLinear;
    private LinearLayout resetPwdLinear;
    private TextView isOnText;
    private TextView isUnBindText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lostphone_setting);
        init();
        if (PreferencesUtils.getBoolean(LostPhoneActivity.this,"isbindsim",false)){
            isOnText.setText("已开启");
            isUnBindText.setText("已绑定");
        }else {
            isOnText.setText("请开启sim卡绑定");
            isUnBindText.setText("未绑定");
        }

        setLeftBackImageText("手机防盗", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        settingPwdLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startIntent("phonepwd");
            }
        });
        bindSimLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtils.setBoolean(LostPhoneActivity.this, "isbindsim", true);
                isOnText.setText("已开启");
                isUnBindText.setText("已绑定");
                startIntent("phone");
            }
        });
        unBindSimLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtils.setBoolean(LostPhoneActivity.this,"isbindsim",false);
                isOnText.setText("请开启sim卡绑定");
                isUnBindText.setText("未绑定");
            }
        });
        resetPwdLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent("phonepwd");
            }
        });

    }
    public void startIntent(String type){
        Intent intent=new Intent(LostPhoneActivity.this,EditActivity.class);
        Bundle bundle =new Bundle();
        bundle.putString("type", type);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void init() {
        settingPwdLinear= (LinearLayout) findViewById(R.id.line_settingpwd);
        bindSimLinear= (LinearLayout) findViewById(R.id.line_bindsim);
        unBindSimLinear= (LinearLayout) findViewById(R.id.line_unbindsim);
        resetPwdLinear= (LinearLayout) findViewById(R.id.line_restpwd);
        isOnText= (TextView) findViewById(R.id.tv_ison);
        isUnBindText= (TextView) findViewById(R.id.tv_isunbind);

    }
}
