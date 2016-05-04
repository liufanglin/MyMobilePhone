package com.shopex.phone.phone.library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.library.toolbox.StringUtils;
import com.shopex.phone.phone.library.toolbox.TimeUtils;
import com.shopex.phone.phone.utils.ChartComparator;

import java.util.Collections;
import java.util.List;


/**
 * 坐标轴使用的View
 */


public class GridChart extends View {

    /**
     * 默认XY轴字体大小
     **/
    public static final int DEFAULT_AXIS_TITLE_SIZE = R.dimen.vp_chart_text_size;

    /**
     * 默认XY坐标轴颜色
     */
    private static final int DEFAULT_AXIS_COLOR = R.color.xy_color;


    /**
     * 默认纬线数
     */
    public static final int DEFAULT_LATITUDE_NUM = 3;

    /**
     * 默认经线数
     */
    public static int DEFAULT_LOGITUDE_NUM = 7;

    /**
     * 默认边框的颜色
     */
    public static final int DEFAULT_BORDER_COLOR = R.color.xy_color;
    public static final int DEFAULT_LOWER_TEXT_HEIGHT = 70;

    /** 默认虚线效果 */
//	private final PathEffect DEFAULT_DASH_EFFECT = new DashPathEffect(new float[] { 5, 5, 5, 5 }, 1);


    // /////////////属性////////////////
    /**
     * 背景色
     */
    private int mBackGround;

    /**
     * 坐标轴XY颜色
     */
    private int mAxisColor;

    /**
     * 经纬线颜色
     */
    private int mLongiLatitudeColor;



    /**
     * 边线色
     */
    private int mBorderColor;



    private static final int BIG_CIRCLE_SIZE=14;


    private float longitudeSpacing;
    private float latitudeSpacing;

    private List<ChartData> mPointData;
    private float mXSpace;
    private float mYSpace;

    private OnClickListen mOnclickListen;

    private float touchX;

    private int viewHeight;
    private int viewWidth;
    private Canvas mCanvas;
    private int mCurrentNumber;
    private boolean isOnTouch = true;

    private float adgeSpace = 50.0f;

    private String mViewTag;

    private int longitudeNumber;
    private final int TOP_ADGE=20;


    public GridChart(Context context) {
        super(context);
        init();
    }

    public GridChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public GridChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }



    public void setmPointData(List<ChartData> mPointData) {
        this.mPointData = mPointData;
    }

    //获取当前点的位置
    public int getmCurrentNumber() {
        return mCurrentNumber;
    }

    public void setmCurrentNumberAndTouch(int mCurrentNumber, boolean isOnTouch) {
        this.mCurrentNumber = mCurrentNumber;
        this.isOnTouch = isOnTouch;
    }

    private void init() {
        mAxisColor = DEFAULT_AXIS_COLOR;
        mLongiLatitudeColor = DEFAULT_AXIS_COLOR;
        mBorderColor = DEFAULT_BORDER_COLOR;
        longitudeNumber=DEFAULT_LOGITUDE_NUM;

    }

    public void setLongitudeNumber(int longitudeNumber) {
        this.longitudeNumber = longitudeNumber;
    }

    public void setmOnclickListen(OnClickListen mOnclickListen) {
        this.mOnclickListen = mOnclickListen;
    }

    public void setmViewTag(String mViewTag) {
        this.mViewTag = mViewTag;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mCanvas = canvas;
        if (null == mPointData || mPointData.size() == 0) {
            return;
        }
        viewHeight = getHeight();
        viewWidth = getWidth();



        adgeSpace=getResources().getDimensionPixelSize(R.dimen.vp_chart_adgeSpace_size);

        longitudeSpacing = (viewWidth - 2 * adgeSpace) / (float)(longitudeNumber-1);

        System.out.println("longitudeSpacing====>"+longitudeSpacing);

        float mDataSpace= (float)(Collections.max(mPointData,new ChartComparator()).getMoney()- Collections.min(mPointData, new ChartComparator()).getMoney());
        Log.i("mDataSpace", mDataSpace + "");
        if(mDataSpace!=0){
            mYSpace = (float)(viewHeight - DEFAULT_LOWER_TEXT_HEIGHT) / mDataSpace;

        }
        mXSpace = longitudeSpacing;

//        mUperChartHeight = latitudeSpacing * (DEFAULT_UPER_LATITUDE_NUM + 1);

        // 绘制边框
        drawBorders();

        // 绘制经线
        drawLongitudes(longitudeSpacing);

        drawLowerText(mPointData.get(0).getMyData(), mPointData.get(mPointData.size() - 1).getMyData(), mViewTag);

        drawSelectPoint();
        drawPointAndLine();


        // 绘制纬线
//        drawLatitudes(canvas, viewHeight, viewWidth, latitudeSpacing);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Rect rect = new Rect();
        // getGlobalVisibleRect(rect);
//        float x = event.getRawX();
//        float y = event.getRawY();

        float touchY = 0;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                touchX = event.getX();
                touchY = event.getY();
                if (touchX < 2 || touchX > getWidth()) {
                    return false;
                }
                if (touchY < 2 || touchY > getHeight()) {
                    return false;
                }
                isOnTouch = true;
                postInvalidate();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                break;

            default:
                break;
        }

        return true;

    }


    /**
     * 绘制边框
     */
    private void drawBorders() {
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(mBorderColor));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(1);
        // canvas.drawLine(1, 1, viewWidth - 1, 1, paint);
        // canvas.drawLine(1, 1, 1, viewHeight - 1, paint);
        // canvas.drawLine(viewWidth - 1, viewHeight - 1, viewWidth - 1, 1,
        // paint);
//        mCanvas.drawLine(viewWidth-adgeSpace, 2, viewWidth-adgeSpace, getHeight() - DEFAULT_LOWER_TEXT_HEIGHT, paint);
//        mCanvas.drawLine(adgeSpace, 2, adgeSpace, getHeight() - DEFAULT_LOWER_TEXT_HEIGHT, paint);
        mCanvas.drawLine(0, 0, viewWidth, 0, paint);
        mCanvas.drawLine(0, viewHeight - DEFAULT_LOWER_TEXT_HEIGHT, viewWidth, viewHeight - DEFAULT_LOWER_TEXT_HEIGHT, paint);
    }

    /**
     * 绘制经线
     */
    private void drawLongitudes(float longitudeSpacing) {
        Paint paint = new Paint();
        // paint.setStyle(Style.STROKE);
        // paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(mLongiLatitudeColor));
        // paint.setPathEffect(mDashEffect);
        for (int i = 0; i < longitudeNumber; i++) {
            if (i == 0) {
                mCanvas.drawLine(adgeSpace, 2, adgeSpace, getHeight() - DEFAULT_LOWER_TEXT_HEIGHT, paint);
            } else {
                mCanvas.drawLine(adgeSpace + longitudeSpacing * i, 2, adgeSpace + longitudeSpacing * i, getHeight() - DEFAULT_LOWER_TEXT_HEIGHT, paint);
            }

        }

    }

    /**
     * 绘制纬线
     */
    private void drawLatitudes(float latitudeSpacing) {
        Paint paint = new Paint();
        paint.setColor(mLongiLatitudeColor);
        paint.setAntiAlias(true);
        // paint.setStrokeWidth(1);
        paint.setStyle(Style.STROKE);


        for (int i = 1; i <= DEFAULT_LATITUDE_NUM; i++) {
            mCanvas.drawLine(1, latitudeSpacing * i,
                    viewWidth, latitudeSpacing * i, paint);
        }

    }


    private void drawLowerText(String start, String end, String currentTag) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTextSize(getResources().getDimensionPixelSize(DEFAULT_AXIS_TITLE_SIZE));
        mCanvas.drawText(start, adgeSpace - (start.length() * getResources().getDimensionPixelSize(DEFAULT_AXIS_TITLE_SIZE)) / 4.0f, viewHeight - DEFAULT_LOWER_TEXT_HEIGHT / 2+getResources().getDimensionPixelSize(DEFAULT_AXIS_TITLE_SIZE)/2, paint);
        mCanvas.drawText(end, adgeSpace + (longitudeNumber - 1) * mXSpace - (end.length() * getResources().getDimensionPixelSize(DEFAULT_AXIS_TITLE_SIZE)) / 4.0f, viewHeight - DEFAULT_LOWER_TEXT_HEIGHT / 2+getResources().getDimensionPixelSize(DEFAULT_AXIS_TITLE_SIZE)/2, paint);
        mCanvas.drawText(currentTag, viewWidth / 2 - (4 * getResources().getDimensionPixelSize(DEFAULT_AXIS_TITLE_SIZE)) / 2.0f-(2 * getResources().getDimensionPixelSize(DEFAULT_AXIS_TITLE_SIZE)) / 4.0f, viewHeight - DEFAULT_LOWER_TEXT_HEIGHT / 2+getResources().getDimensionPixelSize(DEFAULT_AXIS_TITLE_SIZE)/2, paint);
    }

    private void drawPointAndLine() {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        for (int i = 0; i < mPointData.size(); i++) {
            paint.setAlpha(250);
            float mPointHigh = (float) (viewHeight - DEFAULT_LOWER_TEXT_HEIGHT - mYSpace * mPointData.get(i).getMoney());
            if (mPointHigh < BIG_CIRCLE_SIZE+TOP_ADGE) {
                mPointHigh = BIG_CIRCLE_SIZE+TOP_ADGE;
            }
            mCanvas.drawCircle(adgeSpace + i * mXSpace, mPointHigh, 5, paint);// 小圆
            paint.setAlpha(100);
            if (i < mPointData.size() - 1) {
                float mNextPointHigh = (float) (viewHeight - DEFAULT_LOWER_TEXT_HEIGHT - mYSpace * mPointData.get(i + 1).getMoney());
                if (mNextPointHigh < BIG_CIRCLE_SIZE+TOP_ADGE) {
                    mNextPointHigh = BIG_CIRCLE_SIZE+TOP_ADGE;
                }
                mCanvas.drawLine(adgeSpace + i * mXSpace, mPointHigh, adgeSpace + (i + 1) * mXSpace, mNextPointHigh, paint);
            }


        }

    }

    public interface OnClickListen {
        void onClick(String date);
    }

    private void drawSelectPoint() {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
//        paint.setStrokeWidth(3);
        paint.setAlpha(100);
        if (isOnTouch) {
            int mNumber = (int)Math.abs(Math.rint((touchX - adgeSpace) / mXSpace));
            mCurrentNumber = mNumber;
        }
        if (mPointData.size() > mCurrentNumber) {
            float mSelectPointHigh = (float) (viewHeight - DEFAULT_LOWER_TEXT_HEIGHT - mYSpace * mPointData.get(mCurrentNumber).getMoney());
            if (mSelectPointHigh < BIG_CIRCLE_SIZE+TOP_ADGE) {
                mSelectPointHigh = BIG_CIRCLE_SIZE+TOP_ADGE;
            }
            mCanvas.drawCircle(adgeSpace + mCurrentNumber * mXSpace, mSelectPointHigh, BIG_CIRCLE_SIZE, paint);// 大圆

            try {
                mOnclickListen.onClick(mPointData.get(mCurrentNumber).getMyData()+ ":"+mPointData.get(mCurrentNumber).getMoney()+"KB");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



}
