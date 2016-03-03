package com.shopex.phone.phone.activity;

import android.os.Bundle;
import android.view.View;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.BaseActivity;

/**
 * Created by samsung on 2016/2/18.
 * 设置界面，关于账号的我随便写什么
 */
public class AccountSettingActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        setLeftBackImageText("返回", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setCenterTitle("设置");

    }

    @Override
    public void init() {
        super.init();

    }
}
