package com.shopex.phone.phone.library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;

/**
 * Created by samsung on 2016/3/24.
 */
public class DemoView extends View {


    public DemoView(Context context) {
        super(context);
    }

    public DemoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DemoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //请求父view要不要拦截事件
    private void requestParentIntertEvent(boolean isIntert){
        ViewParent parent =getParent();
        if (parent!=null){
            parent.requestDisallowInterceptTouchEvent(isIntert);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画圆
      /*  Paint cPaint=new Paint();
        cPaint.setColor(Color.RED);
        //画出的是空心的圆
        cPaint.setStyle(Paint.Style.STROKE);
        //消除锯齿
        cPaint.setAntiAlias(true);
        canvas.drawCircle(100,100,50,cPaint);
        //注意矩形是实心哈似乎空心
//画渐变色的矩形
        Shader mShader = new LinearGradient(0,0,100,100, new int[]{Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW},null,Shader.TileMode.REPEAT);

        ShapeDrawable sd;
        sd=new ShapeDrawable(new RectShape());
        sd.setBounds(20,20,100,100);

        Paint paint=new Paint();
        paint.setColor(Color.YELLOW);
        RectF re=new RectF(100,200,100,200);
        canvas.drawOval(re,paint);
*/


        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        // 消除锯齿
        paint.setAntiAlias(true);
        // 设置画笔的颜色
        paint.setColor(Color.RED);
        // 设置paint的外框宽度
        paint.setStrokeWidth(2);

        // 画一个圆
        canvas.drawCircle(40, 30, 20, paint);
        // 画一个正放形
        canvas.drawRect(20, 70, 70, 120, paint);
        // 画一个长方形
        canvas.drawRect(20, 170, 90, 280, paint);
        // 画一个椭圆
        RectF re = new RectF(20, 230, 100, 333);
        canvas.drawOval(re, paint);

        //画直线
        canvas.drawLine(300, 300, 400, 400, paint);
        String text="wo yeshi zuile ";
        canvas.drawText(text, 400, 500, paint);

        //获取文本所在矩形的宽和高
        Rect rect=new Rect();
        paint.getTextBounds(text,0,text.length(),rect);
        int w=rect.width();
        int y=rect.height();
        Log.i("---w---y",w+"--"+y);





    }
}
