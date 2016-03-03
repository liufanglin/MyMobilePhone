package com.shopex.phone.phone.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.common.BaseApplication;
import com.shopex.phone.phone.library.constants.AppConstants;
import com.shopex.phone.phone.library.toolbox.LogUtils;

/**
 * Created by samsung on 2016/1/12.
 * 手动输入号码到添加到黑名单
 */
public class InputPhoneActivity extends BaseActivity {
    private EditText et_name;
    private EditText et_phont;
    private Button save;
    private String name;
    private String phone;
    private Intent intent;
    public final static int RESULT_INPUT=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputphone);
        setLeftBackImageText("手工输入号码", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        intent=getIntent();
        init();
    }

    @Override
    public void init() {
        super.init();
        et_name= (EditText) findViewById(R.id.et_name);
        et_phont= (EditText) findViewById(R.id.et_phone);

        save= (Button) findViewById(R.id.btn_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=et_name.getText().toString();
                phone=et_phont.getText().toString();
                BaseApplication.db.execSQL("insert into contract_info(name, phone) values(?, ?)",
                        new Object[]{name,phone});
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                setResult(RESULT_INPUT, intent);
                finish();
            }
        });

    }
}


