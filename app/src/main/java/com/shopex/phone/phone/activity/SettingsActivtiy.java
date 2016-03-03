package com.shopex.phone.phone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.library.toolbox.PreferencesUtils;
import com.shopex.phone.phone.utils.ReadFriend;

/**
 * Created by samsung on 2016/1/12.
 * 设置界面
 */
public class SettingsActivtiy extends BaseActivity{
    private Switch switchs;
    private Switch switchPhone;
    private Switch switchEms;
    private LinearLayout interPhone;
    private LinearLayout interEms;
    private LinearLayout blackList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setLeftBackImageText("骚扰拦截设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
    }

    @Override
    public void init() {
        super.init();
        switchs= (Switch) findViewById(R.id.switchs);
        if (PreferencesUtils.getBoolean(SettingsActivtiy.this,"isinter")){
            switchs.setChecked(true);
        }else {
            switchs.setChecked(false);
        }
        switchPhone= (Switch) findViewById(R.id.switchPhone);
        switchEms= (Switch) findViewById(R.id.switchEms);
        interPhone= (LinearLayout) findViewById(R.id.interphone);
        interEms= (LinearLayout) findViewById(R.id.interems);
        blackList= (LinearLayout) findViewById(R.id.blacklist);
        switchs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    interPhone.setVisibility(View.GONE);
                    interEms.setVisibility(View.GONE);
                    blackList.setVisibility(View.GONE);
                    PreferencesUtils.setBoolean(SettingsActivtiy.this,"isinter",false);
                }else {
                    interPhone.setVisibility(View.VISIBLE);
                    interEms.setVisibility(View.VISIBLE);
                    blackList.setVisibility(View.VISIBLE);
                    PreferencesUtils.setBoolean(SettingsActivtiy.this,"isinter",true);
                }
            }
        });
        if (PreferencesUtils.getBoolean(SettingsActivtiy.this,"isinterphone")){
            switchPhone.setChecked(true);
        }else {
            switchPhone.setChecked(false);
        }
        switchPhone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //获取联系人
                    ReadFriend.curesorContract(SettingsActivtiy.this);
                    PreferencesUtils.setBoolean(SettingsActivtiy.this, "isinterphone", true);
                } else {
                    PreferencesUtils.setBoolean(SettingsActivtiy.this, "isinterphone", false);
                }
            }
        });
        switchEms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        blackList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivtiy.this,AddBlankListActivity.class));
            }
        });

    }
}
