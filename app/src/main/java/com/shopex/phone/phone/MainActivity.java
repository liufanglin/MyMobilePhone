package com.shopex.phone.phone;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shopex.phone.phone.activity.AccountSettingActivity;
import com.shopex.phone.phone.activity.AllAppActivity;
import com.shopex.phone.phone.activity.InterceptionActivity;
import com.shopex.phone.phone.activity.LostPhoneActivity;
import com.shopex.phone.phone.activity.TrafficActivity;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.library.constants.AppConstants;
import com.shopex.phone.phone.library.toolbox.PreferencesUtils;

public class MainActivity extends BaseActivity {
    private GridView gridView;
    private EditText putInPwdEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLeftBackImageText("手机助手", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        setRightImage(R.mipmap.cc, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        gridView= (GridView) findViewById(R.id.gridview);
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
                        View contentView=getLayoutInflater().inflate(R.layout.dialog_putin_pwd,null);
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

                    }
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
                //本机所有的app
                if (position == 4) {
                    Intent intent = new Intent(MainActivity.this, AllAppActivity.class);
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


}
