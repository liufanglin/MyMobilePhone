package com.shopex.phone.phone.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.fragment.InterEmsFramgent;
import com.shopex.phone.phone.fragment.InterPhoneFragment;
import com.shopex.phone.phone.library.constants.AppConstants;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by samsung on 2016/1/8.
 * 骚扰拦截
 */
public class InterceptionActivity extends BaseActivity{
    private ViewPager pager;
    private PagerTabStrip tab;
    private List<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidepage);
        setLeftBackImageText("骚扰拦截", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setRightImage(R.mipmap.icon_common_settings, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InterceptionActivity.this,SettingsActivtiy.class));

            }
        });
        init();



    }

    @Override
    public void init() {
        super.init();
        pager= (ViewPager) findViewById(R.id.vp);
        tab= (PagerTabStrip) findViewById(R.id.pagerTitle);
        tab.setTextColor(Color.BLUE);
        tab.setTextSize(0, 23);
        tab.setBackgroundColor(Color.RED);
        tab.setTabIndicatorColor(Color.BLUE);
        list=new ArrayList<>();
        list.add(new InterEmsFramgent());
        list.add(new InterPhoneFragment());
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

    }
    public class ViewPagerAdapter extends FragmentPagerAdapter{


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return list.get(position);
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return AppConstants.title[position];
        }
    }

}
