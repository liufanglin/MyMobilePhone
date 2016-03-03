package com.shopex.phone.phone.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.shopex.phone.phone.R;
import com.shopex.phone.phone.adapter.BaseApplicationInfoAdapter;
import com.shopex.phone.phone.bean.AppInfo;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.utils.AppInfoParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsung on 2016/2/17.
 */
public class AllAppActivity extends BaseActivity {
    private Button localApp;
    private Button systemApp;
    private ListView allApp;
    private List<AppInfo> localList=new ArrayList<>();
    private List<AppInfo> systemList=new ArrayList<>();
    private PackageManager pm=null;
    private BaseApplicationInfoAdapter adapter;
    //标记是系统应用还是本地应用
    private boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allapp);
        init();
        setLeftBackImageText("应用程序", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loadAppInfomation();
        adapter=new BaseApplicationInfoAdapter(AllAppActivity.this, localList,true);
        allApp.setAdapter(adapter);
        allApp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showAppDetail(position);
            }
        });

    }
    public  void showAppDetail(int postion) {
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        if (flag){
            intent.setData(Uri.parse("package:" + localList.get(postion).getPackname()));
        }else {
            intent.setData(Uri.parse("package:" + systemList.get(postion).getPackname()));
        }

        startActivity(intent);
    }

    @Override
    public void init() {
        localApp= (Button) findViewById(R.id.btn_local);
        systemApp= (Button) findViewById(R.id.btn_system);
        allApp= (ListView) findViewById(R.id.lv_all);
        systemApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=false;
                loadAppInfomation();
                adapter=new BaseApplicationInfoAdapter(AllAppActivity.this, systemList,flag);
                allApp.setAdapter(adapter);


            }
        });
        localApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=true;
                loadAppInfomation();
                adapter = new BaseApplicationInfoAdapter(AllAppActivity.this, localList, flag);
                allApp.setAdapter(adapter);
            }
        });
    }

    public void loadAppInfomation() {
        localList.clear();
        systemList.clear();
        List<AppInfo> list=AppInfoParser.getAppInfos(AllAppActivity.this);
        for(int i=0;i<list.size();i++){
            if (list.get(i).isUserApp()){
                //用户app
                localList.add(list.get(i));
            } else{
                systemList.add(list.get(i));
            }
        }
    }




}
