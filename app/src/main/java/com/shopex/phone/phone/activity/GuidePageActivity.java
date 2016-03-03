package com.shopex.phone.phone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shopex.phone.phone.MainActivity;
import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.RootActivity;
import com.shopex.phone.phone.library.constants.AppConstants;
import com.shopex.phone.phone.library.toolbox.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsung on 2016/1/7.
 */
public class GuidePageActivity extends RootActivity{
    private ViewGroup viewGroup;
    private int[] imgae = AppConstants.GUIDE_PAGE;
    private List<View> list;
    private ViewPager pager;
    private ImageView[] dotImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guidepage);
        initViewPager();
        initDot();
        pager.setAdapter(new MyAdapater(list));
        pager.setOnPageChangeListener(new MyPageChangeListener());

    }

    /**
     * 初始化viewpager
     */
    public void initViewPager() {
        pager = (ViewPager) findViewById(R.id.vp);
        list = new ArrayList<>();
        for (int i = 0; i < imgae.length; i++) {
            if(i==imgae.length-1) {
                View view = LayoutInflater.from(GuidePageActivity.this).inflate(R.layout.guide_begin_page, null);
                Button btn= (Button) view.findViewById(R.id.start_login);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoTarget();
                    }
                });
                list.add(view);
            }else {
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageResource(imgae[i]);
                list.add(imageView);
            }



        }
    }

    /**
     * 初始化小点
     */
    public void initDot() {
        dotImageView = new ImageView[list.size()];
        viewGroup = (ViewGroup) findViewById(R.id.viewgroup);
        LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < list.size(); i++) {
            margin.setMargins(5, 0, 0, 0);
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(margin);
            dotImageView[i] = imageView;
            if (i == 0) {
                dotImageView[i].setBackgroundResource(AppConstants.GUIDE_PAGE[0]);

            } else {
                dotImageView[i].setBackgroundResource(AppConstants.GUIDE_PAGE_POTION[1]);
            }
            viewGroup.addView(dotImageView[i]);
        }
    }



    public void gotoTarget(){


        //保存打开引导页标记
      //  PreferencesUtils.putBoolean(GuidePageActivity.this,SplashActivity.NOT_FIRST_OPEN,true);
        PreferencesUtils.setBoolean(GuidePageActivity.this,SplashActivity.ISFIRSTOPEN,true);
        Intent it = new Intent(GuidePageActivity.this, MainActivity.class);
        startActivity(it);
        finish();

    }



    //viewpage适配器
    public class MyAdapater extends PagerAdapter {
        public List<View> list;

        public MyAdapater(List<View> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }
    }

    //页面滑动监听
    public class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < list.size(); i++) {
                if (position == i) {
                    dotImageView[i].setBackgroundResource(AppConstants.GUIDE_PAGE_POTION[0]);
                } else {
                    dotImageView[i].setBackgroundResource(AppConstants.GUIDE_PAGE_POTION[1]);
                }
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
