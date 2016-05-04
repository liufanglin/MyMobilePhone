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
import com.shopex.phone.phone.library.constants.AppConstants;
import com.shopex.phone.phone.library.toolbox.PreferencesUtils;
import com.shopex.phone.phone.library.view.ChartData;
import com.shopex.phone.phone.library.view.GridChart;
import com.shopex.phone.phone.library.view.InputPayPasswordDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity  implements GridChart.OnClickListen{
    private GridView gridView;
    private EditText putInPwdEdit;
    private DrawerLayout drawerLayout;
    private LayoutInflater inflater=null;
    private View drawerView;
    private FrameLayout content;
    private GridChart mGridChart;
    private TextView mSelectText;
    private List<String> dateList;
    private List<ChartData> mDataList=new ArrayList<>();
    public void addData(){
        mDataList= getAppLiuLiang();
    }

    public List<ChartData> getAppLiuLiang(){
        List<ChartData> list =new ArrayList<>();
        PackageManager pckMan = getPackageManager();
        List<PackageInfo> packs = pckMan.getInstalledPackages(0);
        ArrayList<HashMap<String, Object>> item = new ArrayList<HashMap<String, Object>>();
        for (PackageInfo p : packs) {
            if ((p.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0
                    && (p.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0) {
                int appid = p.applicationInfo.uid;
                long rxdata = TrafficStats.getUidRxBytes(appid);
                rxdata = rxdata / 1024;
                long txdata = TrafficStats.getUidTxBytes(appid);
                txdata = txdata / 1024;
                long data_total = rxdata + txdata;
                ChartData date=new ChartData();
                date.day=p.applicationInfo.loadLabel(getPackageManager()).toString();
                date.sales=data_total+"";
                list.add(date);
            }
        }
        return list;
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1001){
                openInputPayPasswordFrame();
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        dateList=getSevenDay();
        addData();
        content= (FrameLayout) findViewById(R.id.contentfragment);
        inflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        drawerView=inflater.inflate(R.layout.activity_main,null);
        setCenterTitle("手机助手");
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
        gridView= (GridView)drawerView.findViewById(R.id.gridview);
        mGridChart= (GridChart) drawerView.findViewById(R.id.mChart);
        mSelectText = (TextView) drawerView.findViewById(R.id.data);
        mGridChart.setLongitudeNumber(mDataList.size());
        mGridChart.setmViewTag("App消耗流量统计");
        mGridChart.setmPointData(mDataList);
        if(mDataList.size()>0)
            mGridChart.setmCurrentNumberAndTouch(mDataList.size()-1,false);

        mGridChart.setmOnclickListen(this);
        mGridChart.postInvalidate();





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
     //   mDrawerToggle=new ActionBarDrawerToggle(this,mDrawerToggle,);
        gridView.setAdapter(new MyBaseAdapter());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //手机防盗功能
                if (position == 0) {
                    if (PreferencesUtils.getString(MainActivity.this, "phonepwd", null) == null) {
                        Intent intent = new Intent(MainActivity.this, LostPhoneActivity.class);
                        startActivity(intent);
                    } else {
                        openInputPayPasswordFrame();
                    }
                }
                //流量统计功能
                if(position==1){
                    Intent intent =new Intent(MainActivity.this,Showmain.class);
                    startActivity(intent);
                }

                //骚扰拦截
                if (position ==2) {
                    Intent intent = new Intent(MainActivity.this, TrafficActivity.class);
                    startActivity(intent);
                }

                //黑名单管理
                if (position ==3){
                    Intent intent =new Intent(MainActivity.this, AddBlankListActivity.class);
                    startActivity(intent);
                }
                //本机所有的app
                if (position == 4) {
                    Intent intent = new Intent(MainActivity.this, AllAppActivity.class);
                    startActivity(intent);

                }
                //备份
                if(position==5){
                    Intent intent =new Intent(MainActivity.this, BackupsActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onClick(String date) {
            mSelectText.setText(date);

    }

    public class MyBaseAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        public MyBaseAdapter(){
            inflater= (LayoutInflater) MainActivity.this.getSystemService(MainActivity.this.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return AppConstants.MAIN_IMAGE.length;
        }

        @Override
        public Object getItem(int position) {
            return AppConstants.MAIN_TEXT[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView==null){
                view=inflater.inflate(R.layout.activity_gridview_item,null);
            }else {
                view=convertView;
            }
            ImageView imageView= (ImageView) view.findViewById(R.id.iv_show);
            imageView.setImageResource(AppConstants.MAIN_IMAGE[position]);
            TextView textViewTitle= (TextView) view.findViewById(R.id.tv_title);
            textViewTitle.setText(AppConstants.MAIN_TEXTTITLE[position]);
            TextView textView= (TextView) view.findViewById(R.id.tv_sizetitle);
            textView.setText(AppConstants.MAIN_TEXT[position]);
            return view;
        }
    }


    // 打开输入支付密码界面
    private void openInputPayPasswordFrame() {
        InputPayPasswordDialog.openDialog(MainActivity.this, new InputPayPasswordDialog.OnPayPasswordListener() {

            @Override
            public void onResult(String payPassword) {
                // 验证支付密码是否正确

                if (TextUtils.isEmpty(payPassword)){
                    Toast.makeText(MainActivity.this,"请输入密码",3000).show();
                }else if(payPassword.equals(PreferencesUtils.getString(MainActivity.this,"phonepwd").trim())){
                    Intent intent = new Intent(MainActivity.this, LostPhoneActivity.class);
                    startActivity(intent);
                }else {
                    handler.sendEmptyMessageDelayed(1001,1000);
                    Toast.makeText(MainActivity.this,"验证密码输入错误，请重新输入",3000).show();
                }

            }
            @Override
            public double onPaymentAmount() {
               return  0.00;
            }
        });
    }


    //获取最近七天日期
    public List<String> getSevenDay(){
        List<String> list=new ArrayList<>();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM-dd");
        for (int i=6;i>=0;i--){
            long time=System.currentTimeMillis()-i*24*3600*1000;
            String day=simpleDateFormat.format(new Date(time));
            list.add(day);
        }
        return list;
    }



}
