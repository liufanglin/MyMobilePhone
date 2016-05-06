package com.shopex.phone.phone.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.shopex.phone.phone.common.BaseActivity;


/**
 * Created by samsung on 2016/5/5.
 */
public class FloatViewActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout view =new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(400,400);
        view.setLayoutParams(layoutParams);
        view.setOrientation(LinearLayout.VERTICAL);
        view.setGravity(Gravity.CENTER_HORIZONTAL);


        LinearLayout linearLayout=new LinearLayout(this);
        LinearLayout.LayoutParams linearParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(linearParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView textView=new TextView(this);
        textView.setText("开关");
        Switch sw=new Switch(this);
        linearLayout.addView(textView);
        linearLayout.addView(sw);


        Button open=new Button(this);
        open.setText("打开文件");
        LinearLayout.LayoutParams btnParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnParams.setMargins(0, 20, 0, 20);
        open.setLayoutParams(btnParams);

        Button clear=new Button(this);
        open.setText("打开文件");
        LinearLayout.LayoutParams clearParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        clear.setLayoutParams(clearParams);
        clear.setText("清除文件");

        view.addView(linearLayout);
        view.addView(open);
        view.addView(clear);
        setContentView(view);








    }
}
