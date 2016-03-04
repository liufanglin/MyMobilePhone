package com.shopex.phone.phone.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.utils.ReadSystemMemaryUtil;

/**
 * Created by samsung on 2016/3/3.
 * 管理手机内存
 */
public class MemaryActivity extends BaseActivity {
    private TextView totleMemaryText;
    private RecyclerView list;
    private Button sureBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memary);
        init();
        String systemAvail= ReadSystemMemaryUtil.getAvailMemory(MemaryActivity.this);
        String systemTotle=ReadSystemMemaryUtil.getTotalMemory(MemaryActivity.this);
        totleMemaryText.setText("系统可用内存:"+systemAvail+"  系统总内存"+systemTotle);
        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void init() {
        super.init();
        totleMemaryText= (TextView) findViewById(R.id.tv_showmemary);
        list= (RecyclerView) findViewById(R.id.list);
        sureBtn= (Button) findViewById(R.id.btn_clearall);

    }
}
