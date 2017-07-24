package widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;

import project.ljy.animationutils.R;


/**
 * Title: WaveView
 * Description:波浪纹效果
 * Copyright: Copyright (c) 2014-2016 gjfax.com
 * Company: 广金所
 * Author: 刘加彦
 * Date: 2017/7/24
 * Version: 1.0
 */

public class WaveView extends BasePaintView {


    private Paint mPaint = null;
    private Path mPath = null;
    //波长
    private int mWaveLength = 0;
    private int mHeight = 0;

    // 波浪线的点以及控制点
    private int[] startPoint = new int[2];
    private int[] mControlPoint1 = new int[2];
    private int[] mPoint1 = new int[2];
    private int[] mControlPoint2 = new int[2];
    private int[] mPoint2 = new int[2];
    private int[] mControlPoint3 = new int[2];
    private int[] mPoint3 = new int[2];
    private int[] mControlPoint4 = new int[2];
    private int[] mPoint4 = new int[2];

    ValueAnimator mStartPointAnimator = null;

    private boolean mIsInit = false;

    public WaveView(Context context) {
        super(context);
        initPaint();
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量控件宽度
        setMeasuredDimension(measure(widthMeasureSpec,true),measure(heightMeasureSpec,false));
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("sizeChange" , "w:" + w + ",h:" + h);
        mWaveLength = w;
        mHeight = h;
        startPoint[0] = - mWaveLength;
        startPoint[1] = mHeight / 2;

        mControlPoint1[0] = - mWaveLength *  3 / 4;
        mControlPoint1[1] = mHeight * 5 / 8;
        mPoint1[0] = -mWaveLength / 2;
        mPoint1[1] = mHeight / 2;

        mControlPoint2[0] = - mWaveLength / 4 ;
        mControlPoint2[1] = mHeight * 3/ 8;
        mPoint2[0] = 0;
        mPoint2[1] = mHeight / 2;

        mControlPoint3[0] = mWaveLength / 4 ;
        mControlPoint3[1] = mHeight * 5/ 8;
        mPoint3[0] = mWaveLength / 2;
        mPoint3[1] = mHeight / 2;

        mControlPoint4[0] = mWaveLength * 3/ 4 ;
        mControlPoint4[1] = mHeight * 3 / 8;
        mPoint4[0] = mWaveLength ;
        mPoint4[1] = mHeight / 2;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        mStartPointAnimator = ValueAnimator.ofInt(startPoint[0] , 0).setDuration(2000);
        mStartPointAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int moveX = (int)animation.getAnimatedValue();
                startPoint[0] = moveX;
                startPoint[1] = mHeight / 2;
                mControlPoint1[0] = - mWaveLength *  3 / 4 + moveX;
                mControlPoint1[1] = mHeight * 5 / 8;
                mPoint1[0] = -mWaveLength / 2 + moveX;
                mPoint1[1] = mHeight / 2;

                mControlPoint2[0] = - mWaveLength / 4 + moveX;
                mControlPoint2[1] = mHeight * 3/ 8;
                mPoint2[0] = moveX;
                mPoint2[1] = mHeight / 2;

                mControlPoint3[0] = mWaveLength / 4 + moveX;
                mControlPoint3[1] = mHeight * 5/ 8;
                mPoint3[0] = mWaveLength / 2 + moveX;
                mPoint3[1] = mHeight / 2;

                mControlPoint4[0] = mWaveLength * 3/ 4 + moveX;
                mControlPoint4[1] = mHeight * 3 / 8;
                mPoint4[0] = mWaveLength + moveX;
                mPoint4[1] = mHeight / 2;
                invalidate();
            }
        });
        mStartPointAnimator.setRepeatCount(Animation.INFINITE);
        mStartPointAnimator.start();
        mIsInit = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!mIsInit)
            return;
        mPath.reset();
        mPath.lineTo(startPoint[0] , startPoint[1]);
        mPath.quadTo(mControlPoint1[0],mControlPoint1[1],mPoint1[0],mPoint1[1]);
        mPath.quadTo(mControlPoint2[0],mControlPoint2[1],mPoint2[0],mPoint2[1]);
        mPath.quadTo(mControlPoint3[0],mControlPoint3[1],mPoint3[0],mPoint3[1]);
        mPath.quadTo(mControlPoint4[0],mControlPoint4[1],mPoint4[0],mPoint4[1]);
        mPath.lineTo(mPoint4[0],getMeasuredHeight());
        mPath.lineTo(startPoint[0],getMeasuredHeight());
        mPath.lineTo(startPoint[0],startPoint[1]);
        canvas.drawPath(mPath, mPaint);
        Log.i("draw", "-----------------------" + "startWidth :" + mPoint4[0] + ",startHeight:" + mPoint4[1]);
    }

    private void initPaint(){
        mPaint = getmPaint();
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        mPath = new Path();
    }

    /**
     * 计算长宽
     * @param measureSpec  测量尺寸
     * @param isWidth  标记是否是宽
     * @return
     */
    private int measure(int measureSpec, boolean isWidth) {
        int result;
        int mode = View.MeasureSpec.getMode(measureSpec);
        int size = View.MeasureSpec.getSize(measureSpec);
        int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
        if (mode == View.MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
            result += padding;
            if (mode == View.MeasureSpec.AT_MOST) {
                if (isWidth) {
                    result = Math.max(result, size);
                } else {
                    result = Math.min(result, size);
                }
            }
        }
        return result;
    }
}
