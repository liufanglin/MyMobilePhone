package com.shopex.phone.phone.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.utils.SmsUtils;

/**
 * Created by samsung on 2016/3/4.
 * 进行短信备份，联系人备份
 */
public class BackupsActivity extends BaseActivity {
    private LinearLayout smsLinear;
    private LinearLayout phoneLinear;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backups);
        init();
        phoneLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        smsLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                new Thread() {
                    @Override
                    public void run() {
                        SmsUtils.backUp(BackupsActivity.this, new SmsUtils.BackUpCallBackSms()

                        {
                            @Override
                            public void befor(int count) {
                                progressBar.setMax(count);
                            }

                            @Override
                            public void onBackUpSms(int process) {
                                progressBar.setProgress(process);
                            }

                        });
                    }
                }.start();


            }
        });
    }

    @Override
    public void init() {
        super.init();
        smsLinear= (LinearLayout) findViewById(R.id.line_sms);
        phoneLinear= (LinearLayout) findViewById(R.id.line_phone);

    }
    public void showDialog(){
        View contentView=getLayoutInflater().inflate(R.layout.dialog_backups,null);
        progressBar= (ProgressBar) contentView.findViewById(R.id.progress);
        progressBar.setMax(100);
        AlertDialog.Builder builder=new AlertDialog.Builder(BackupsActivity.this);
        builder.setTitle("备份进度").setView(contentView)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();
    }




}
