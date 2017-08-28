package widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Title: IndicatorView
 * Description:带动效Indicator
 * Copyright: Copyright (c) 2014-2016 gjfax.com
 * Company: 广金所
 * Author: 刘加彦
 * Date: 2017/8/9
 * Version: 1.0
 * todo:onPageScrollStateChanged的值变化要关注
 */

public class IndicatorView extends BasePaintView implements ViewPager.OnPageChangeListener {

    private int mWidth = 0;
    private int mHeight = 0;

    private int mPointWidth = 0;
    private int mPointDiameter = 0;

    private ViewPager mPager = null;
    private int mPageCount = 0;
    private int mLeftPosition = 0;

    private float[] fractions = null;
    private Path[] mPointPaths = null;
    private RectF[] mRectFs = null;
    private Path mCompletePath = null;

    public IndicatorView(Context context) {
        super(context);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if ( mPageCount == 0){
            return;
        }
        mCompletePath.rewind();
        for(int i = 0 ; i < fractions.length ; i++){
            mPointPaths[i].rewind();
            float width = mPointWidth * fractions[i];
            if(width <= mPointWidth / 4){
                width = mPointDiameter;
            }

            if(i == mLeftPosition){
                mRectFs[i] = new RectF(mRectFs[i].left,mRectFs[i].top,mRectFs[i].left + width, mRectFs[i].bottom);
            } else if(i == mLeftPosition + 1) {
                mRectFs[i] = new RectF(mRectFs[i].right - width,mRectFs[i].top,mRectFs[i].right, mRectFs[i].bottom);
            }
            mPointPaths[i].addRoundRect(mRectFs[i],mPointWidth / 4  , mHeight/4 , Path.Direction.CCW);
            mCompletePath.addPath(mPointPaths[i]);
        }
        canvas.drawPath(mCompletePath,getmPaint());
    }

    public void setPager(ViewPager mPager) {
        this.mPager = mPager;
        setPageCount(mPager.getAdapter().getCount());
        mPager.getAdapter().registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                setPageCount(IndicatorView.this.mPager.getAdapter().getCount());
            }
        });
        mPager.addOnPageChangeListener(this);
    }

    private void setPageCount(int pages) {
        mPageCount = pages;
    }

    private void initPointState(){
        fractions = new float[mPageCount];
        mPointPaths = new Path[mPageCount];
        mRectFs = new RectF[mPageCount];
        mCompletePath = new Path();
        mPointWidth = mWidth / mPageCount;
        int pointRight = mPointWidth;
        mPointDiameter = mPointWidth / 4;
        for(int i = 0 ; i < fractions.length ; i++){
            //置第一个点为当前点
            if(i == 0){
                fractions[i] = 1f;
                mRectFs[i] = new RectF(0 , 0 , pointRight ,  mHeight / 4);
            } else {
                fractions[i] = 0f;
                mRectFs[i] = new RectF( pointRight - mPointDiameter, 0 , pointRight , mHeight / 4);
            }
            mPointPaths[i] = new Path();
            pointRight = pointRight + mPointWidth;

        }
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        initPointState();
        requestLayout();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(fractions == null) return ;
        mLeftPosition = position;
        for(int i = 0 ; i < fractions.length;i++){
            if(i == position){
                fractions[i] = 1 - positionOffset;
            } else if(i == position + 1){
                fractions[i] = positionOffset;
            } else {
                fractions[i] = 0f;
            }
        }
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

}
