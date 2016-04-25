package com.shopex.phone.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
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

import com.shopex.phone.phone.activity.AllAppActivity;
import com.shopex.phone.phone.activity.BackupsActivity;
import com.shopex.phone.phone.activity.LostPhoneActivity;
import com.shopex.phone.phone.activity.MemaryActivity;
import com.shopex.phone.phone.activity.Showmain;
import com.shopex.phone.phone.activity.TrafficActivity;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.library.constants.AppConstants;
import com.shopex.phone.phone.library.toolbox.PreferencesUtils;
import com.shopex.phone.phone.library.view.InputPayPasswordDialog;

public class MainActivity extends BaseActivity {
    private GridView gridView;
    private EditText putInPwdEdit;
    private DrawerLayout drawerLayout;
    private LayoutInflater inflater=null;
    private View drawerView;
    private FrameLayout content;
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
        content= (FrameLayout) findViewById(R.id.contentfragment);
        inflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        drawerView=inflater.inflate(R.layout.activity_main,null);
        setLeftBackImageText("手机助手", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
                       /* View contentView=getLayoutInflater().inflate(R.layout.dialog_putin_pwd,null);
                        putInPwdEdit= (EditText) contentView.findViewById(R.id.et_pwd);
                        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("请输入密码").setView(contentView)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (TextUtils.isEmpty(putInPwdEdit.getText().toString())){
                                            Toast.makeText(MainActivity.this,"请输入密码",3000).show();
                                        }else if(putInPwdEdit.getText().toString().equals(PreferencesUtils.getString(MainActivity.this,"phonepwd").trim())){
                                            Intent intent = new Intent(MainActivity.this, LostPhoneActivity.class);
                                            startActivity(intent);
                                            dialog.dismiss();
                                        }
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog=builder.create();
                        dialog.show();

*/
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
               /* if (position == 2) {
                    Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, InterceptionActivity.class);
                    startActivity(intent);
                }*/
                //内存管理
                if (position ==3){
                    Intent intent =new Intent(MainActivity.this, MemaryActivity.class);
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


}
