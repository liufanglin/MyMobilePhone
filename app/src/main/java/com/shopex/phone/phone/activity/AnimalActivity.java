package com.shopex.phone.phone.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.RootActivity;


/**
 * Created by samsung on 2016/4/7.
 */
public class AnimalActivity extends RootActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa);
        final Button btn= (Button) findViewById(R.id.button);
     //   Animation anim= AnimationUtils.loadAnimation(this, R.anim.my_tran);

     //   btn.startAnimation(anim);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AnimalActivity.this,"dian le wo ",Toast.LENGTH_LONG).show();
            }

        });
        Button buttons= (Button) findViewById(R.id.click);
        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation translateAnimation = new TranslateAnimation(0.1f, 200.0f,0.1f,200.0f);
                translateAnimation.setDuration(3000);
                translateAnimation.setFillAfter(true);
                translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        btn.clearAnimation();
                        //   btn.layout(300,250,600,500);

                        LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        margin.setMargins(200, 200, 300, 350);
                        btn.setLayoutParams(margin);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                btn.startAnimation(translateAnimation);

               /* LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                margin.setMargins(200, 200, 0, 0);
                btn.setLayoutParams(margin);*/
            }
        });
    }

}
