package com.shopex.phone.phone.activity;

import android.os.Bundle;
import android.view.View;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.BaseActivity;

/**
 * Created by samsung on 2016/3/3.
 * 丢失手机的设置页面，设置一些功能是否开启
 */
public class LostAboutActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lostabout);
        setLeftBackImageText("设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
