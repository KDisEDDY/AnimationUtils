package widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import project.ljy.animationutils.R;

/**
 * Title: IndicatorView
 * Description:带动效Indicator
 * Copyright: Copyright (c) 2014-2016 gjfax.com
 * Company: 广金所
 * Author: 刘加彦
 * Date: 2017/8/9
 * Version: 1.0
 */

public class IndicatorView extends BasePaintView implements ViewPager.OnPageChangeListener {

    private static final int DEFAULT_DOT_SIZE = 8;                      // dp
    private static final int DEFAULT_POINT_COLOR = R.color.colorAccent;
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
        this(context, null, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context,attrs,defStyleAttr);
    }

    private void initAttr(Context context, AttributeSet attrs,int defStyle){
        float destiny = context.getResources().getDisplayMetrics().density;
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.IndicatorView, defStyle, 0);
        mPointDiameter = a.getDimensionPixelSize(R.styleable.IndicatorView_pointDiameter,(int)(DEFAULT_DOT_SIZE * destiny * 0.5));
        int color = a.getColor(R.styleable.IndicatorView_pointColor,getResources().getColor(DEFAULT_POINT_COLOR));
        getmPaint().setColor(color);
        a.recycle();
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
            mPointPaths[i].addRoundRect(mRectFs[i],mPointWidth / 4  , mHeight , Path.Direction.CCW);
            mCompletePath.addPath(mPointPaths[i]);
        }
        canvas.drawPath(mCompletePath,getmPaint());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int desiredHeight = getDesiredHeight();
        int height;
        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case MeasureSpec.EXACTLY:
                height = MeasureSpec.getSize(heightMeasureSpec);
                break;
            case MeasureSpec.AT_MOST:
                height = Math.min(desiredHeight, MeasureSpec.getSize(heightMeasureSpec));
                break;
            default: // MeasureSpec.UNSPECIFIED
                height = desiredHeight;
                break;
        }

        int desiredWidth = getDesiredWidth();
        int width;
        switch (MeasureSpec.getMode(widthMeasureSpec)) {
            case MeasureSpec.EXACTLY:
                width = MeasureSpec.getSize(widthMeasureSpec);
                break;
            case MeasureSpec.AT_MOST:
                width = Math.min(desiredWidth, MeasureSpec.getSize(widthMeasureSpec));
                break;
            default: // MeasureSpec.UNSPECIFIED
                width = desiredWidth;
                break;
        }
        setMeasuredDimension(width, height);
    }

    private int getDesiredHeight() {
        return getPaddingTop() + mPointDiameter + getPaddingBottom();
    }

    /** 单个点的最长宽度为点直径的4倍 */
    private int getRequiredWidth() {
        mPointWidth = mPointDiameter * 4;
        return mPageCount * mPointWidth;
    }

    private int getDesiredWidth() {
        return getPaddingLeft() + getRequiredWidth() + getPaddingRight();
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
        int pointRight = mPointWidth;
        for(int i = 0 ; i < fractions.length ; i++){
            //置第一个点为当前点
            if(i == 0){
                fractions[i] = 1f;
                mRectFs[i] = new RectF(0 , 0 , pointRight ,  mHeight);
            } else {
                fractions[i] = 0f;
                mRectFs[i] = new RectF( pointRight - mPointDiameter, 0 , pointRight , mHeight);
            }
            mPointPaths[i] = new Path();
            pointRight = pointRight + mPointWidth;

        }
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
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
