package com.shopex.phone.phone;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shopex.phone.phone.activity.AddBlankListActivity;
import com.shopex.phone.phone.activity.AllAppActivity;
import com.shopex.phone.phone.activity.Appsdata;
import com.shopex.phone.phone.activity.BackupsActivity;
import com.shopex.phone.phone.activity.LostPhoneActivity;
import com.shopex.phone.phone.activity.MemaryActivity;
import com.shopex.phone.phone.activity.Showmain;
import com.shopex.phone.phone.activity.TrafficActivity;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.common.BaseApplication;
import com.shopex.phone.phone.library.constants.AppConstants;
import com.shopex.phone.phone.library.toolbox.LogUtils;
import com.shopex.phone.phone.library.toolbox.PreferencesUtils;
import com.shopex.phone.phone.library.toolbox.TimeUtils;
import com.shopex.phone.phone.library.view.ChartData;
import com.shopex.phone.phone.library.view.GridChart;
import com.shopex.phone.phone.library.view.InputPayPasswordDialog;
import com.shopex.phone.phone.utils.loghelp.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity  implements GridChart.OnClickListen{

    private DrawerLayout drawerLayout;
    private LayoutInflater inflater=null;
    private View drawerView;
    private FrameLayout content;
    private TextView tv_name,tv_kaluli;
    private EditText et_kaluli;
    private Button btn_setting,btn_edit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        content= (FrameLayout) findViewById(R.id.contentfragment);
        inflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        drawerView=inflater.inflate(R.layout.activity_main,null);
        setCenterTitle("android");
        if (AppConstants.isLogin){
            setRightImage(R.mipmap.ccc, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
            });
        }else {
            setRightImage(R.mipmap.cc, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
            });
        }
//        private TextView tv_name,tv_kaluli;
//        private EditText et_kaluli;
//        private Button btn_setting;
        tv_name= (TextView) drawerView.findViewById(R.id.tv_name);
        tv_kaluli= (TextView) drawerView.findViewById(R.id.tv_kaluli);
        et_kaluli= (EditText) drawerView.findViewById(R.id.et_kaluli);
        btn_setting= (Button) drawerView.findViewById(R.id.btn_setting);
        btn_edit= (Button) drawerView.findViewById(R.id.btn_edit);

        tv_name.setText(AppConstants.name);
        setNum();


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_kaluli.setVisibility(View.VISIBLE);
                tv_kaluli.setVisibility(View.GONE);

            }
        });

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_kaluli.getText().toString()!=null) {
                    PreferencesUtils.setString(MainActivity.this,TimeUtils.getCurrentDay(),ObjectTOjson(TimeUtils.getCurrentTime(), et_kaluli.getText().toString()));
                    setNum();
                }
            }
        });

        content.addView(drawerView);
        //侧滑
        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                switch (newState) {
                    case DrawerLayout.STATE_DRAGGING://拖动状态
                        Log.i("tag", "------拖动状态----");
                        break;
                    case DrawerLayout.STATE_IDLE://静止状态
                        Log.i("tag", "------静止状态----") ;
                        break;
                    case DrawerLayout.STATE_SETTLING://选中状态
                        Log.i("tag", "------选中状态----");
                        break;
                }
            }
        });


    }

    @Override
    public void onClick(String date) {

    }

    public void setNum(){
        JSONArray localarray=getArray(MainActivity.this, TimeUtils.getCurrentDay());
        if (localarray!=null&&localarray.length()>0){
            try {
                JSONObject jsonObject=localarray.optJSONObject(localarray.length()-1);
                String num=jsonObject.optString("num");
                tv_kaluli.setText(num);
                tv_kaluli.setVisibility(View.VISIBLE);
                btn_edit.setVisibility(View.VISIBLE);
                et_kaluli.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            //无数据显示设置
            tv_kaluli.setVisibility(View.GONE);
            et_kaluli.setVisibility(View.VISIBLE);
            btn_edit.setVisibility(View.GONE);

        }
    }


    /**
     * 把要保存的数据转成json 形式
     * */
    public String ObjectTOjson(String time,String num){
        //先读出来 在存进去
        JSONArray jsonArray=new JSONArray();
        JSONArray localarray=getArray(MainActivity.this, TimeUtils.getCurrentDay());
        if (localarray!=null){
            for (int i=0;i<localarray.length();i++){
                jsonArray.put(localarray.optJSONObject(i));

            }
        }
        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.put("num",num);
            jsonObject.put("time",time);
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray.toString();
    }



    public JSONArray getArray(Context context,String day){
        String array= PreferencesUtils.getString(context,day);
        try {
            if (!TextUtils.isEmpty(array)){
                JSONArray jsonArray=new JSONArray(array);
                if (jsonArray.length()>0){

                    return jsonArray;
                }
                return null;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }





}
