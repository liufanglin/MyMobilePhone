package com.shopex.phone.phone.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.utils.ReadSystemMemaryUtil;

import java.lang.reflect.Method;
import java.util.List;

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
        PackageManager packageManager=getPackageManager();
        //第一个参数包名，第二个是uid
        //packageManager.getPackageSizeInfo();
        //在手机所以的应用程序
        List<PackageInfo> installedPackages=packageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo:installedPackages){
           // String uid=packageInfo
        //    getCacheSize(packageInfo);
        }
    }
    private void getCacheSize(PackageInfo packageInfo){
        try {
            Class<?> clazz=getClassLoader().loadClass("PackageManager");
            //通过反射获取得到当前方法
    //       Method method=clazz.getDeclaredMethod("getPackageSizeInfo", String.class, IPackageStatsObserver.class);
       //第一个表示当前方法谁调用的，第二个
      //      method.invoke(PackageManager,packageInfo.applicationInfo.packageName)

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
