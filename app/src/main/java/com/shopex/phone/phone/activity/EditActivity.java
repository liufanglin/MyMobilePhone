package com.shopex.phone.phone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.bean.SortModel;
import com.shopex.phone.phone.bean.UserInfo;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.db.User;
import com.shopex.phone.phone.library.db.DbServiceUser;
import com.shopex.phone.phone.library.toolbox.LogUtils;
import com.shopex.phone.phone.library.toolbox.PreferencesUtils;

import org.w3c.dom.Text;

/**
 * Created by samsung on 2016/2/29.
 *
 */
public class EditActivity extends BaseActivity{
    private EditText edit;
    private TextView textLimat;
    private String type;
    private Button selectBtn;
    public static final int ADDLOCALSTATUS=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setLeftBackImageText("返回", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
        if (type.equals("phone")){
            selectBtn.setVisibility(View.VISIBLE);
            textLimat.setVisibility(View.GONE);
        }
        if (type.equals("nick")||type.equals("signle")){
            textLimat.setVisibility(View.GONE);
            selectBtn.setVisibility(View.GONE);
        }
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(EditActivity.this,LocalPhoneActivity.class);
                startActivityForResult(intent,ADDLOCALSTATUS);
            }
        });
        setRightText("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = edit.getText().toString();
                if (type.equals("phone")){
                    //绑定的手机号
                    TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
                    String realsim = tm.getSimSerialNumber();
                    PreferencesUtils.setString(EditActivity.this,"bindphone",realsim);
                }else if(type.equals("nick")){
                    if (!TextUtils.isEmpty(edit.getText().toString())){
                        UserInfo.getInstance().nick=edit.getText().toString();
                    }else {
                        Toast.makeText(EditActivity.this,"请输入昵称",3000).show();
                    }

                }else if(type.equals("signle")){

                    if (!TextUtils.isEmpty(edit.getText().toString())){
                        UserInfo.getInstance().phone=edit.getText().toString();
                    }else {
                        Toast.makeText(EditActivity.this,"请输入个性签名",3000).show();
                    }

                }
                else{
                    if(edit.getText().length()==6) {
                        PreferencesUtils.setString(EditActivity.this, type, text);
                    }else{
                        Toast.makeText(EditActivity.this,"请输入6位的数字密码",3000).show();
                    }

                }
                finish();
            }
        });


    }

    @Override
    public void init() {
        edit= (EditText) findViewById(R.id.et_edit);
        selectBtn= (Button) findViewById(R.id.btn_selectphone);
        textLimat= (TextView) findViewById(R.id.tv_limit);
        Intent intent=getIntent();
        Bundle bundle= intent.getExtras();
        type=bundle.getString("type");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDLOCALSTATUS && resultCode == LocalPhoneActivity.FORPHONERESULT) {
            SortModel sortModel = (SortModel) data.getExtras().getSerializable("item");
            edit.setText(sortModel.getPhone());
        }
    }
}
