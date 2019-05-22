package com.shopex.phone.phone.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.common.BaseApplication;
import com.shopex.phone.phone.db.ContactUser;
import com.shopex.phone.phone.db.ContactUserDao;
import com.shopex.phone.phone.library.db.DbService;
import com.shopex.phone.phone.library.toolbox.LogUtils;
import com.shopex.phone.phone.library.toolbox.Run;
import com.shopex.phone.phone.library.toolbox.T;

import java.util.Calendar;
import java.util.Locale;


/**
 * Created by samsung on 2016/3/9.
 * 注册页面
 */
public class RegistActivity extends BaseActivity implements View.OnClickListener{

    private Button mNextButton;
    private EditText mPhoneNumberText, mPasswdText,firstname,lastname,hight,weight,et_address,et_leave;
    private CheckBox mVisiblePasswordBox;
    private RadioButton radio1,radio2;
    private TextView tv_birthday;
    private String bir;
    private String sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_regist);
        setLeftBackImageText("register", new View.OnClickListener() {
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
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        hight = (EditText) findViewById(R.id.hight);
        weight = (EditText) findViewById(R.id.weight);
        et_address = (EditText) findViewById(R.id.et_address);
        et_leave = (EditText) findViewById(R.id.et_leave);

        radio1= (RadioButton) findViewById(R.id.radio1);
        radio2= (RadioButton) findViewById(R.id.radio2);

        tv_birthday= (TextView) findViewById(R.id.tv_birthday);

        tv_birthday.setOnClickListener(this);

        mNextButton = (Button) findViewById(R.id.account_regist_next_button);//注册
        mPhoneNumberText = (EditText) findViewById(R.id.account_regist_username);//输入注册名（手机号）
        mPasswdText = (EditText) findViewById(R.id.account_regist_passwd);//密码
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
        if (v==tv_birthday){
          showDatePickerDialog(RegistActivity.this,tv_birthday,Calendar.getInstance(Locale.CHINA));
        }
    }
    // 注册用户
    private void registAccount() {
        String phoneNumber = mPhoneNumberText.getText().toString();
        String password = mPasswdText.getText().toString();
        String firstnames=firstname.getText().toString();
        String lastnames=lastname.getText().toString();
        String hights=hight.getText().toString();
        String weights=weight.getText().toString();
        String et_addresss=et_address.getText().toString();
        String et_leaves=et_leave.getText().toString();
        String sex;
        if (radio1.isChecked()){
            sex="man";
        }else {
            sex="woman";
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            T.showShort(RegistActivity.this, "account is null");
            mPhoneNumberText.requestFocus();
        }  else if (TextUtils.isEmpty(password) ) {
            T.showShort(RegistActivity.this,"password is null");
            mPasswdText.requestFocus();
        }else if (TextUtils.isEmpty(firstnames)){
            T.showShort(RegistActivity.this,"firstname is null");
            firstname.requestFocus();

        }else if (TextUtils.isEmpty(lastnames)){
            T.showShort(RegistActivity.this,"firstname is null");
            lastname.requestFocus();

        }else if (TextUtils.isEmpty(hights)){
            T.showShort(RegistActivity.this,"hight is null");
            hight.requestFocus();

        }else if (TextUtils.isEmpty(weights)){
            T.showShort(RegistActivity.this,"weight is null");
            weight.requestFocus();

        }else if (TextUtils.isEmpty(et_addresss)){
            T.showShort(RegistActivity.this,"address is null");
            et_address.requestFocus();

        }else if (TextUtils.isEmpty(et_leaves)){
            T.showShort(RegistActivity.this,"leave is null");
            et_leave.requestFocus();

        }else if (TextUtils.isEmpty(bir)){
            T.showShort(RegistActivity.this,"bir is null");
        } else {
            T.showShort(RegistActivity.this, "success");
            BaseApplication.usersDb.execSQL("insert into user_info(name,pwd,firstname,lastname,bir,hight,weight,gender,address,leave) values(?,?,?,?,?,?,?,?,?,?)",
                    new Object[]{phoneNumber, password,firstnames,lastnames,bir,hights,weights,sex,et_addresss,et_leaves});

            Intent intent=new Intent(RegistActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }





    public  void showDatePickerDialog(Activity activity, final TextView tv, Calendar calendar) {
        // Calendar 需要这样来得到
        // Calendar calendar = Calendar.getInstance();
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity,
                // 绑定监听器(How the parent is notified that the date is set.)
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // 此处得到选择的时间，可以进行你想要的操作
                        tv.setText(  year + "-" + monthOfYear
                                + "-" + dayOfMonth );
                        bir= year + "-" + monthOfYear + "-" + dayOfMonth;
                    }
                }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                ,calendar.get(Calendar.MONTH)
                ,calendar.get(Calendar.DAY_OF_MONTH)).show();
    }





}
