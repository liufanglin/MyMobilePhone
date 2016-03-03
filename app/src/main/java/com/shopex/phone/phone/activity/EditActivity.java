package com.shopex.phone.phone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.bean.SortModel;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.library.toolbox.LogUtils;
import com.shopex.phone.phone.library.toolbox.PreferencesUtils;

/**
 * Created by samsung on 2016/2/29.
 *
 */
public class EditActivity extends BaseActivity{
    private EditText edit;
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
                PreferencesUtils.setString(EditActivity.this, type, text);
                if (type.equals("phone")){
                    //绑定的手机号
                    TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
                    String realsim = tm.getSimSerialNumber();
                    PreferencesUtils.setString(EditActivity.this,"bindphone",realsim);
                }
                finish();
            }
        });


    }

    @Override
    public void init() {
        edit= (EditText) findViewById(R.id.et_edit);
        selectBtn= (Button) findViewById(R.id.btn_selectphone);
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
