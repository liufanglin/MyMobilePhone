package com.shopex.phone.phone.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.common.BaseApplication;
import com.shopex.phone.phone.library.constants.AppConstants;
import com.shopex.phone.phone.library.http.BackResult;
import com.shopex.phone.phone.library.http.MyAsynctask;
import com.shopex.phone.phone.library.toolbox.LogUtils;
import com.shopex.phone.phone.library.toolbox.T;
import com.shopex.phone.phone.utils.SmsUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by samsung on 2016/3/4.
 * 进行短信备份，联系人备份  号码归属地查询
 *
 */
public class BackupsActivity extends BaseActivity {
    private LinearLayout smsLinear;
    private LinearLayout phoneLinear;
    private LinearLayout selectLinear;
    private ProgressBar progressBar;
    private EditText phoneEdit;
    private Button sureBtn;
    private TextView showText;
    private String address="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backups);
        init();
        phoneLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = SmsUtils.backUpPhone(BackupsActivity.this);
                if (!flag) {
                    T.showShort(BackupsActivity.this, "内存卡不存在");
                }
            }
        });
        smsLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                new Thread() {
                    @Override
                    public void run() {
                        SmsUtils.backUpEms(BackupsActivity.this, new SmsUtils.BackUpCallBackSms()

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
        selectLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view=getLayoutInflater().inflate(R.layout.dialog_select_phone,null);
                phoneEdit= (EditText) view.findViewById(R.id.et_phone);
                sureBtn= (Button) view.findViewById(R.id.btn_select);
                showText= (TextView) view.findViewById(R.id.tv_show);
                sureBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text=getLocal(phoneEdit.getText().toString());
                        showText.setText(text);
                    }
                });
                AlertDialog.Builder builder=new AlertDialog.Builder(BackupsActivity.this);
                builder.setTitle("号码归属地查询").setView(view)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public void init() {
        super.init();
        smsLinear= (LinearLayout) findViewById(R.id.line_sms);
        phoneLinear= (LinearLayout) findViewById(R.id.line_phone);
        selectLinear= (LinearLayout) findViewById(R.id.line_select);
    }
    public void showDialog(){
        View contentView=getLayoutInflater().inflate(R.layout.dialog_backups,null);
        progressBar= (ProgressBar) contentView.findViewById(R.id.progress);
        progressBar.setMax(100);
        AlertDialog.Builder builder=new AlertDialog.Builder(BackupsActivity.this);
        builder.setTitle("备份进度").setView(contentView)
                .setNegativeButton("隐藏在后台", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
    //查询号码归属地
    public String getLocal(String phone){

        String path="http://apis.juhe.cn/mobile/get";
        Map<String,String> params=new HashMap<>();
        params.put("type", "GET");
        params.put("path", path);
        params.put("phone",phone);
        params.put("key", AppConstants.PhoneAPPKEY);
        MyAsynctask task=new MyAsynctask(this,params,new BackResult() {

            @Override
            public void BackResult(Boolean flag, String json) {
                if (flag) {
                    try {
                        JSONObject object= new JSONObject(json);
                        String result= object.getString("resultcode");
                        if (result.equals("200")){
                            JSONObject all=object.getJSONObject("result");
                            String province=all.getString("province");
                            String city=all.getString("city");
                            String company=all.getString("company");
                            String card=all.getString("card");
                            address=province+city+company+card;
                        }else {
                            LogUtils.instance.i("返回的状态码有错");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {

                }
            }
        });
        task.execute();


        return address;
    }
}
