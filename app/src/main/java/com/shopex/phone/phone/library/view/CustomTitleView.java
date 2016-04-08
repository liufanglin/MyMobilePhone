package com.shopex.phone.phone.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.shopex.phone.phone.R;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by samsung on 2016/3/23.
 */
public class CustomTitleView extends View {
    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;

    //绘制文本时控制文本绘制范围
    private Rect mBound;
    private Paint mPaint;

//自定义属性的话需要调用三个参数的构造函数
    public CustomTitleView(Context context) {
        this(context, null);
    }

    public CustomTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
//在这里获取我自定义样式的属性
    //我们重写了3个构造方法，默认的布局文件调用的是两个参数的构造方法，所以记得让所有的构造调用我们的三个参数的构造，我们在三个参数的构造中获得自定义属性。
    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
       //获取自定义样式属性
       	TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CustomTitleView, defStyleAttr, 0);
		mTitleTextSize = a.getDimensionPixelSize(R.styleable.CustomTitleView_titleTextSizes, 40);
		mTitleTextColor = a.getColor(R.styleable.CustomTitleView_titleTextColors, Color.BLUE);
        mTitleText= (String) a.getText(R.styleable.CustomTitleView_titleTexts);
        a.recycle();
        //获取绘制文本的宽和高度
        mPaint=new Paint();
        mPaint.setTextSize(mTitleTextSize);
        mBound=new Rect();
        //这样能够用mBound获取文本的长和宽
        mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitleText=randomText();
                postInvalidate();
            }
        });
    }

    private String randomText()
    {
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 4)
        {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set)
        {
            sb.append("" + i);
        }

        return sb.toString();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        //获取的是我们设置的宽高  如果设置的是wran match 则会默认选择屏幕宽度，会选择我们组件的设置值
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);


        mPaint.setColor(mTitleTextColor);
        canvas.drawText(mTitleText,getWidth()/2-mBound.width()/2,getHeight()/2-mBound.height()/2,mPaint);
    }

   /* 重写之前先了解MeasureSpec的specMode,一共三种类型：

    EXACTLY：一般是设置了明确的值或者是MATCH_PARENT

    AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT

    UNSPECIFIED：表示子布局想要多大就多大，很少使用*/
    //我们重写这个方法后就会根据我们的测量值选择大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height ;
        if (widthMode == MeasureSpec.EXACTLY)
        {
            width = widthSize;
        } else
        {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            float textWidth = mBound.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        } else
        {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            float textHeight = mBound.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }



        setMeasuredDimension(width, height);
    }
}
