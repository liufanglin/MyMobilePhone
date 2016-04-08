package com.shopex.phone.phone.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shopex.phone.phone.MainActivity;
import com.shopex.phone.phone.R;
import com.shopex.phone.phone.bean.Contract;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.common.BaseApplication;
import com.shopex.phone.phone.library.constants.AppConstants;
import com.shopex.phone.phone.library.toolbox.T;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends BaseActivity {

    private Button loginBtn = null;
    private EditText account = null;
    private EditText psw = null;
    private TextView forgotPwd = null;
    private ProgressDialog mDialog = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setLeftBackImageText("登录", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ProgressBar progressBar=new ProgressBar(this);
        progressBar.setVisibility(View.VISIBLE);
        initView();

    }

    public void initView() {

        account = (EditText) findViewById(R.id.account);
        psw = (EditText) findViewById(R.id.psw);
        forgotPwd = (TextView) findViewById(R.id.forgot_pwd);

        loginBtn = (Button) findViewById(R.id.login);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String accountStr = account.getText().toString();
                String pwdStr = psw.getText().toString();

                if (TextUtils.isEmpty(accountStr) || TextUtils.isEmpty(pwdStr)) {
                    Toast.makeText(LoginActivity.this, "密码为空", Toast.LENGTH_LONG).show();
                } else {
                    Cursor cursor= BaseApplication.usersDb.query(AppConstants.TABLE_USER,new String[]{"name","pwd"}, null, null, null, null, null) ;
                    while(cursor.moveToNext()){
                        String name=cursor.getString(0);
                        String phone=cursor.getString(1);
                        if (name.equals(accountStr)){
                            if (phone.equals(pwdStr)){
                                T.showShort(LoginActivity.this, "登录成功");
                                AppConstants.name=name;
                                AppConstants.isLogin=true;
                                finish();
                                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                Bundle bundle=new Bundle();
                                bundle.putString("name",name);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        }
                    }
                    cursor.close();

                }


            }
        });


        forgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               startActivity(new Intent(LoginActivity.this, RegistActivity.class));

            }
        });

    }

}
