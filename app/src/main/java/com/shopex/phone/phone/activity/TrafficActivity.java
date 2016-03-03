package com.shopex.phone.phone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.fragment.InterEmsFramgent;
import com.shopex.phone.phone.fragment.InterPhoneFragment;
import com.shopex.phone.phone.library.toolbox.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsung on 2016/3/2.
 *
 */
public class TrafficActivity extends BaseActivity {
    private List<Fragment> fragmentList =new ArrayList<>();
    private LinearLayout indicator;
    private TextView findTv,goodsTv;
    private int currIndex =0;//当前页编号
    private int offset=0;//动画图片偏移量
    private float bmpW; //动画图片宽度
    private ViewPager viewPager=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic);
        setLeftBackImageText("骚扰拦截", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setRightImage(R.mipmap.icon_common_settings, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrafficActivity.this, SettingsActivtiy.class));

            }
        });
        indicator= (LinearLayout) findViewById(R.id.cursor);
        bmpW=getResources().getDimension(R.dimen.custom_indicator_width);
        //除去左右两边屏幕剩余的宽度
        int screenW= (int) (ScreenUtils.getWidthJust(TrafficActivity.this)-2*getResources().getDimension(R.dimen.default_customer_offset));
        offset= (int) ((screenW-2*bmpW));//计算偏移量

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = (int) getResources().getDimension(R.dimen.default_customer_offset);
        indicator.setLayoutParams(params);

        findTv= (TextView) findViewById(R.id.tv_find);
        goodsTv= (TextView) findViewById(R.id.tv_goods);
        findTv.setOnClickListener(new MyOnClickListener(0));
        goodsTv.setOnClickListener(new MyOnClickListener(1));

        viewPager= (ViewPager) findViewById(R.id.viewPager);
        fragmentList.add(new InterEmsFramgent());
        fragmentList.add(new InterPhoneFragment());

        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),fragmentList));
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());

    }
    class MyOnClickListener implements View.OnClickListener{

        int position;
        int one = offset + (int) bmpW;// 页卡1 -> 页卡2 偏移量


        public MyOnClickListener (int position){
            this.position=position;
        }
        @Override
        public void onClick(View v) {

            Animation animation = new TranslateAnimation(one * currIndex, one * position, 0, 0);
            currIndex = position;
            animation.setFillAfter(true);
            animation.setDuration(300);
            indicator.startAnimation(animation);
            viewPager.setCurrentItem(position);

        }
    }
    public class MyViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> list;
        public MyViewPagerAdapter(FragmentManager fm,List<Fragment> list){
            super(fm);
            this.list=list;
        }


        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

    }
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{
        int position;
        int one= (int) (offset+bmpW);//页卡1——》页卡2直接的偏移量


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            Animation animation = new TranslateAnimation(one * currIndex, one * position, 0, 0);
            currIndex = position;
            animation.setFillAfter(true);
            animation.setDuration(300);
            indicator.startAnimation(animation);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
