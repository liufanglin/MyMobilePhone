package com.shopex.phone.phone.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.library.toolbox.AppUtils;


/**
 * Created by samsung on 2016/1/20.
 * 关于界面
 */
public class AboutActivity extends BaseActivity{
    private TextView version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setLeftBackImageText("关于手机助手", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        version= (TextView) findViewById(R.id.tv_version);
        version.setText("手机助手App"+"   "+ AppUtils.getVersionName(this));





    }

}
