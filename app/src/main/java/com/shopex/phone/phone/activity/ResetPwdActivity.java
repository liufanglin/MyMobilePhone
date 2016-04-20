package com.shopex.phone.phone.activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.shopex.phone.phone.MainActivity;
import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.common.BaseApplication;
import com.shopex.phone.phone.library.constants.AppConstants;
import com.shopex.phone.phone.library.toolbox.T;


public class ResetPwdActivity extends BaseActivity {

    private Button sureBtn = null;
    private EditText pwdStr = null;
    private EditText oldPwd=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        setLeftBackImageText("设置密码", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initViewInLoginOk();

    }


    private void initViewInLoginOk() {

        sureBtn = (Button) findViewById(R.id.sure);
        pwdStr = (EditText) findViewById(R.id.pwd);
        oldPwd= (EditText) findViewById(R.id.oldpwd);
        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void
            onClick(View v) {
                if (AppConstants.name == null) {
                    T.showShort(ResetPwdActivity.this, "请您先登录");
                    return;
                }
                if(TextUtils.isEmpty(pwdStr.getText())){
                    T.showShort(ResetPwdActivity.this, "请输入新密码");
                    return;
                }
                Cursor cursor = BaseApplication.usersDb.query(AppConstants.TABLE_USER, new String[]{"name", "pwd"}, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    String name = cursor.getString(0);
                    String phone = cursor.getString(1);
                    if (name.equals(AppConstants.name)) {
                        if (phone.equals(oldPwd.getText().toString())) {
                            ContentValues values=new ContentValues();
                            values.put("pwd",pwdStr.getText().toString());
                            BaseApplication.usersDb.update(AppConstants.TABLE_USER, values, "name=?", new String[]{AppConstants.name});
                            T.showShort(ResetPwdActivity.this, "密码修改成功，请重新登录");
                            AppConstants.isLogin = true;
                            finish();
                            Intent intent = new Intent(ResetPwdActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                }
                cursor.close();

            }
        });

       CheckBox mVisiblePasswordBox = (CheckBox)findViewById(R.id.eyeView);//选择是否可见
        mVisiblePasswordBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TransformationMethod method = isChecked ? SingleLineTransformationMethod
                        .getInstance() : PasswordTransformationMethod
                        .getInstance();
                pwdStr.setTransformationMethod(method);
                pwdStr.setSelection(pwdStr.getText().length());
                pwdStr.postInvalidate();
            }
        });

    }


}
