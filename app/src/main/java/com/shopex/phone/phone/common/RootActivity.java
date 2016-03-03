package com.shopex.phone.phone.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.shopex.phone.phone.library.CoreJob;

/**
 * Created by samsung on 2016/1/6.
 * 根activity 所有的activity都要继承自它
 */

public class RootActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CoreJob.addToActivityStack(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CoreJob.removeFormActivityStack(this);
    }
}
