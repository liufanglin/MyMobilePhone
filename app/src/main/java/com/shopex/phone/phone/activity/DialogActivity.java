package com.shopex.phone.phone.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.common.BaseApplication;
import com.shopex.phone.phone.utils.loghelp.LogUtil;

import java.io.File;

/**
 * Created by samsung on 2016/5/5.
 */
public class DialogActivity extends Activity{

    public static final String path = "/sdcard/";

   // @Override
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
        Switch aSwitch=new Switch(this);
        linearLayout.addView(textView);
        linearLayout.addView(aSwitch);


        Button showFile=new Button(this);
        showFile.setText("打开文件");
        LinearLayout.LayoutParams btnParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnParams.setMargins(0, 20, 0, 20);
        showFile.setLayoutParams(btnParams);

        Button clearFile=new Button(this);
        showFile.setText("打开文件");
        LinearLayout.LayoutParams clearParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        clearFile.setLayoutParams(clearParams);
        clearFile.setText("清除文件");

        view.addView(linearLayout);
        view.addView(showFile);
        view.addView(clearFile);

        setContentView(view);


        SharedPreferences pre=getSharedPreferences("logswitchstate", Context.MODE_PRIVATE);
        if(pre.getBoolean("switch", false)){
            aSwitch.setChecked(true);
            LogUtil.MYLOG_SWITCH=true;
        }else {
            aSwitch.setChecked(false);
            LogUtil.MYLOG_SWITCH=false;
        }

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences preferences = getSharedPreferences("logswitchstate", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("switch", true);
                    editor.commit();
                    LogUtil.MYLOG_SWITCH=true;
                } else {
                    SharedPreferences preferences = getSharedPreferences("logswitchstate", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("switch", false);
                    editor.commit();
                    LogUtil.MYLOG_SWITCH=false;
                }
            }
        });

        showFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextFileIntent(false);
                finish();
            }
        });
        clearFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delFile();
            }
        });

    }


    /**
     * 删除制定的日志文件
     * */
    public void delFile() {// 删除日志文件
        File file = new File(path, "log.txt");
        if (file.exists()) {
            file.delete();
        }
    }


    //android获取一个用于打开文本文件的intent
    public void getTextFileIntent( boolean paramBoolean){
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (paramBoolean) {
            Uri uri1 = Uri.parse(path);
            intent.setDataAndType(uri1, "text/plain");
        } else {
            Uri uri2 = Uri.fromFile(new File(path,"log.txt"));
            intent.setDataAndType(uri2, "text/plain");
        }
        startActivity(intent);
    }




}
