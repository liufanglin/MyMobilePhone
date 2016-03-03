package com.shopex.phone.phone.common;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.shopex.phone.phone.R;
import com.shopex.phone.phone.library.CoreJob;

/**
 * Created by samsung on 2016/1/6.
 */
public class BaseActivity extends RootActivity {
    //中间标题
    private TextView title=null;
    //左边的返回按钮
    private LinearLayout left_back_menu=null;
    //左边返回文字
    private TextView left_title=null;
    //右边图片
    private ImageView right_menu=null;
    //右边文本
    private TextView right_text=null;
    //toolbar
    private Toolbar toolbar=null;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        //记录打开的activity
        CoreJob.addToActivityStack(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除
        CoreJob.removeFormActivityStack(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        LinearLayout layout= (LinearLayout) LayoutInflater.from(BaseActivity.this).inflate(R.layout.base_activity_root,null);
        LinearLayout container= (LinearLayout) layout.findViewById(R.id.content);
        LayoutInflater.from(BaseActivity.this).inflate(layoutResID,container);
        toolbar= (Toolbar) layout.findViewById(R.id.toolbar);
        title= (TextView) layout.findViewById(R.id.title);
        left_back_menu= (LinearLayout) layout.findViewById(R.id.left_back_menu);
        left_title= (TextView) layout.findViewById(R.id.left_back_menu_text);
        right_menu= (ImageView) layout.findViewById(R.id.right_menu_image);
        right_text= (TextView) layout.findViewById(R.id.right_menu_text);
        //设置toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        super.setContentView(layout);
    }

    @Override
    public void setContentView(View view) {
        LinearLayout layout= (LinearLayout) LayoutInflater.from(BaseActivity.this).inflate(R.layout.base_activity_root,null);
        LinearLayout container= (LinearLayout) layout.findViewById(R.id.content);
        container.addView(view);
        toolbar= (Toolbar) layout.findViewById(R.id.toolbar);
        title= (TextView) layout.findViewById(R.id.title);
        left_back_menu= (LinearLayout) layout.findViewById(R.id.left_back_menu);
        left_title= (TextView) layout.findViewById(R.id.left_back_menu_text);
        right_menu= (ImageView) layout.findViewById(R.id.right_menu_image);
        right_text= (TextView) layout.findViewById(R.id.right_menu_text);

        //设置toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        super.setContentView(layout);
    }
    //设置中间位置标题
    public void setCenterTitle(String tit){
        if (null!=tit){
            if (null!=null){
                toolbar.setTitle("");
            }
            title.setText(tit);
        }
    }
    //设置左边返回按钮是否可见
    public void setLeftBackImage(){
        left_back_menu.setVisibility(View.VISIBLE);

    }
    //设置左边文字和图片
    public void setLeftBackImageText(String title,View.OnClickListener listener){
        left_back_menu.setVisibility(View.VISIBLE);
        if (null!=title){
            left_title.setText(title);
        }
        left_back_menu.setOnClickListener(listener);
    }
    //设置右边图片
    public void setRightImage(int imgRes,View.OnClickListener listener){
        right_menu.setVisibility(View.VISIBLE);
        right_menu.setImageResource(imgRes);
        right_menu.setOnClickListener(listener);
    }
    public void setRightText(String text,View.OnClickListener listener){
        right_text.setVisibility(View.VISIBLE);
        right_text.setText(text);
        right_text.setOnClickListener(listener);
    }

    public void init(){}
    public void initVariables(){}
    public void loadData(){}

}
