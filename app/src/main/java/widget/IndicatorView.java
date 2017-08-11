package widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Path;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Title: IndicatorView
 * Description:带动效Indicator
 * Copyright: Copyright (c) 2014-2016 gjfax.com
 * Company: 广金所
 * Author: 刘加彦
 * Date: 2017/8/9
 * Version: 1.0
 */

public class IndicatorView extends LinearLayout implements ViewPager.OnPageChangeListener,  View.OnAttachStateChangeListener  {

    private Path mPath = null ;
    private int mWidth = 0;
    private int mHeight = 0;

    private ViewPager mPager = null;
    private int mPageCount = 0;
    private int currentPage = -1;
    private int previousPage = -1;
    private boolean pageChanging;
    private float previousOffset = 0;
    private int previousState = 0;  //0：不移动；1：左滑；2：右滑

    private float[] fractions = null;
    private PointView[] pointViews = null;
    private boolean isAttachedToWindow;


    public IndicatorView(Context context) {
        super(context);
        initPath();
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPath();
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPath();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i = 0 ; i < fractions.length ; i++){

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(mWidth != 0 && mHeight != 0){
            if(mPager != null){

            }
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);


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
        initPointState();
        requestLayout();
    }

    private void initPath(){
        mPath = new Path();
    }

    private void initPointState(){
        currentPage = 0;
        previousPage = 0;
        fractions = new float[mPageCount];
        pointViews = new PointView[mPageCount];
        for(int i = 0 ; i < fractions.length ; i++){
            if(i == 0){
                fractions[i] = 1f;
            } else {
                fractions[i] = 0f;
            }
            pointViews[i] = new PointView(getContext());
            addView(pointViews[i]);
        }
        measure(MeasureSpec.AT_MOST,MeasureSpec.AT_MOST);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int currentPosition = pageChanging ? previousPage : currentPage;
        //规避掉左滑时最后一个positionOffset为0的情况
        if(Math.abs(positionOffset - previousOffset) > 0.999){
            positionOffset = 1f;
        }
        if(positionOffset - previousOffset > 0){
            //左滑
            //根据position的左边的点判断之前的操作是否为右滑,规避掉currentPosition = 0 时的情况
            if(currentPosition - 1 != -1 && fractions[currentPosition - 1] > 0){
                setJoiningFraction(currentPosition,positionOffset);
                setJoiningFraction(currentPosition - 1 , 1 - positionOffset);
            } else {
                setJoiningFraction(currentPosition ,1 - positionOffset);
                setJoiningFraction(currentPosition + 1 ,positionOffset);
            }
            previousState = 1;
        } else if(positionOffset - previousOffset < 0){
            //右滑
            //根据position的左边的点判断之前的操作是否为右滑,规避掉currentPosition = pageCount 时的情况
            if(currentPosition + 1 < mPageCount && fractions[currentPosition + 1] > 0){
                setJoiningFraction(currentPosition ,1 - positionOffset);
                setJoiningFraction(currentPosition + 1 , positionOffset);
            } else {
                setJoiningFraction(currentPosition ,positionOffset);
                setJoiningFraction(currentPosition - 1 , 1 - positionOffset);
            }
            previousState = 2;
        } else {
            //不移动

            previousState = 0;
        }
        previousOffset = positionOffset;
    }

    @Override
    public void onPageSelected(int position) {
        if (isAttachedToWindow) {
            // this is the main event we're interested in!
            setSelectedPage(position);
        } else {
            // when not attached, don't animate the move, just store immediately

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setSelectedPage(int now) {
        if (now == currentPage) return;

        pageChanging = true;
        previousPage = currentPage;
        currentPage = now;
        final int steps = Math.abs(now - previousPage);

        if (steps > 1) {
            if (now > previousPage) {
                for (int i = 0; i < steps; i++) {
                    setJoiningFraction(previousPage + i, 1f);
                }
            } else {
                for (int i = -1; i > -steps; i--) {
                    setJoiningFraction(previousPage + i, 1f);
                }
            }
        }
    }

    private void setJoiningFraction(int dot, float fraction) {
        if (dot < fractions.length) {

            if (dot == 1) {
                //Log.d("PageIndicator", "dot 1 fraction:\t" + fraction);
            }

            fractions[dot] = fraction;
        }
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        isAttachedToWindow = true;
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        isAttachedToWindow = false;
    }
}
