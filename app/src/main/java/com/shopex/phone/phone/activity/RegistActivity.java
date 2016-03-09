package com.shopex.phone.phone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.common.BaseApplication;
import com.shopex.phone.phone.library.toolbox.Run;
import com.shopex.phone.phone.library.toolbox.T;


/**
 * Created by samsung on 2016/3/9.
 * 注册页面
 */
public class RegistActivity extends BaseActivity implements View.OnClickListener{

    private Button mGetVerifyCodeButton,mNextButton;
    private EditText mPhoneNumberText, mVerifyCodeText, mPasswdText;
    private CheckBox mVisiblePasswordBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_regist);
        setLeftBackImageText("注册", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
    }

    @Override
    public void init() {
        super.init();
        mGetVerifyCodeButton = (Button) findViewById(R.id.account_regist_get_verify_code_button);//提交验证码
        mNextButton = (Button) findViewById(R.id.account_regist_next_button);//注册
        mPhoneNumberText = (EditText) findViewById(R.id.account_regist_username);//输入注册名（手机号）
        mVerifyCodeText = (EditText) findViewById(R.id.account_regist_verify_code);//输入验证码
        mPasswdText = (EditText) findViewById(R.id.account_regist_passwd);//密码
        mGetVerifyCodeButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mVisiblePasswordBox = (CheckBox)findViewById(R.id.account_register_password_visible);//选择是否可见
        mVisiblePasswordBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TransformationMethod method = isChecked ? SingleLineTransformationMethod
                        .getInstance() : PasswordTransformationMethod
                        .getInstance();
                mPasswdText.setTransformationMethod(method);
                mPasswdText.setSelection(mPasswdText.getText().length());
                mPasswdText.postInvalidate();
            }
        });

      //  enableVreifyCodeButton();
    }


    @Override
    public void onClick(View v) {
      if (v == mNextButton) {
            registAccount();
            // showSecondaryStepView();
        } else {
         //   super.onClick(v);
        }
    }
    // 注册用户
    private void registAccount() {
        String phoneNumber = mPhoneNumberText.getText().toString();
        String password = mPasswdText.getText().toString();
        String verifyCode = mVerifyCodeText.getText().toString();

        if (TextUtils.isEmpty(phoneNumber)
                || !Run.isChinesePhoneNumber(phoneNumber)) {
            T.showShort(RegistActivity.this, "请正确填写手机号");
            mPhoneNumberText.requestFocus();
        }  else if (TextUtils.isEmpty(password) || password.length() < 6
                || password.length() > 20) {
            T.showShort(RegistActivity.this,"密码不合法");
            mPasswdText.requestFocus();
        }else {
            T.showShort(RegistActivity.this, "注册成功");
            BaseApplication.usersDb.execSQL("insert into user_info(name,pwd) values(?,?)",
                    new Object[]{phoneNumber, password});
            Intent intent=new Intent(RegistActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }






  /*  // 设置验证码按钮状态，倒计时60秒
    private void enableVreifyCodeButton() {
        long remainTime = System.currentTimeMillis() - 0;
        remainTime = 60 - remainTime / 1000;
        if (remainTime <= 0) {
            mGetVerifyCodeButton.setEnabled(true);
            mGetVerifyCodeButton
                    .setText("获取验证码");
            mGetVerifyCodeButton.setBackgroundResource(R.drawable.bg_verify_code_red);
            mGetVerifyCodeButton.setTextColor(Color.WHITE);
            return;
        }else{
            mGetVerifyCodeButton.setBackgroundResource(R.drawable.bg_verify_code);
            mGetVerifyCodeButton.setTextColor(getResources().getColor(R.color.default_page_bgcolor_3));
        }

        mGetVerifyCodeButton.setEnabled(false);
        mGetVerifyCodeButton.setText(remainTime+"秒");
    *//*    mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                enableVreifyCodeButton();
            }
        }, 1000);*//*
    }*/




}
