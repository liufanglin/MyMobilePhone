package com.shopex.phone.phone.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
import com.shopex.phone.phone.bean.UserInfo;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.common.BaseApplication;
import com.shopex.phone.phone.db.User;
import com.shopex.phone.phone.library.constants.AppConstants;
import com.shopex.phone.phone.library.db.DbServiceUser;
import com.shopex.phone.phone.library.toolbox.PreferencesUtils;
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
//        setLeftBackImageText("登录", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        ProgressBar progressBar=new ProgressBar(this);
        progressBar.setVisibility(View.VISIBLE);
        initView();

    }

    public void initView() {

        account = (EditText) findViewById(R.id.account);
        psw = (EditText) findViewById(R.id.psw);
        forgotPwd = (TextView) findViewById(R.id.forgot_pwd);
        String name=PreferencesUtils.getString(LoginActivity.this,"loginname",null);
        String password=PreferencesUtils.getString(LoginActivity.this,"loginpwd",null);
        if (!TextUtils.isEmpty(name)) {
            account.setText(name);
        }
        if (!TextUtils.isEmpty(password)){
            psw.setText(password);
        }

        loginBtn = (Button) findViewById(R.id.login);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent w=new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(w);
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
                                PreferencesUtils.setString(LoginActivity.this, "loginname", accountStr);
                                PreferencesUtils.setString(LoginActivity.this,"loginpwd",pwdStr);
                                PreferencesUtils.setString(BaseApplication.getInstance(), "loginpwd", null);
                                AppConstants.name=name;
                                AppConstants.isLogin=true;
                                finish();
                                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                Bundle bundle=new Bundle();
                                bundle.putString("name", name);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                UserInfo.getInstance().account=accountStr;
                                UserInfo.getInstance().pwd=pwdStr;
                                User user=DbServiceUser.getInstance(LoginActivity.this).SelectNote(accountStr);
                                if (user!=null) {
                                    UserInfo.getInstance().phone = user.getPhone() == null ? "" : user.getPhone();
                                    UserInfo.getInstance().photo = user.getPhoto() == null ? "" : user.getPhoto();
                                    UserInfo.getInstance().nick = user.getNick() == null ? "" : user.getNick();
                                }


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
