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
import android.widget.LinearLayout;

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

public class IndicatorView extends BasePaintView implements ViewPager.OnPageChangeListener,  View.OnAttachStateChangeListener  {

    private int mWidth = 0;
    private int mHeight = 0;

    private int mPointWidth = 0;
    private int mPointRadius = 0;

    private ViewPager mPager = null;
    private int mPageCount = 0;
    private int currentPage = -1;
    private int previousPage = -1;
    private boolean pageChanging;
    private float previousOffset = 0;
    private int previousState = 0;  //0：不移动；1：左滑；2：右滑；3：左滑恢复原状；4：右滑恢复原状

    private float[] fractions = null;
    private Path[] mPointPaths = null;
    private RectF[] mRectFs = null;
    private Path mCompletePath = null;
    private boolean isAttachedToWindow;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    IndicatorView.this.invalidate();
                    break;
                default:
                    break;
            }
        }
    };

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
                width = mPointRadius;
            }
            if(previousState == 1){
                //左滑,当前点右边压缩
                if(fractions[i] > 0){
                    if(!pageChanging && i == currentPage){
                        mRectFs[i] = new RectF(mRectFs[i].left , mRectFs[i].top,mRectFs[i].left + width,mRectFs[i].bottom);
                    } else if(pageChanging && i == previousPage){
                        mRectFs[i] = new RectF(mRectFs[i].left , mRectFs[i].top,mRectFs[i].left + width,mRectFs[i].bottom);
                    } else {
                        mRectFs[i] = new RectF(mRectFs[i].right - width , mRectFs[i].top,mRectFs[i].right,mRectFs[i].bottom);
                    }
                } else {
                    mRectFs[i] = new RectF(mRectFs[i]);
                }
            } else if(previousState == 2){
                //右滑，当前点左边压缩
                if(fractions[i] > 0){
                    if(!pageChanging && i == currentPage){
                        mRectFs[i] = new RectF(mRectFs[i].right - width , mRectFs[i].top,mRectFs[i].right , mRectFs[i].bottom);
                    } else if(pageChanging && i == previousPage){
                        mRectFs[i] = new RectF(mRectFs[i].right - width , mRectFs[i].top,mRectFs[i].right , mRectFs[i].bottom);
                    } else {
                        mRectFs[i] = new RectF(mRectFs[i].left , mRectFs[i].top,mRectFs[i].left + width,mRectFs[i].bottom);
                    }
                } else {
                    mRectFs[i] = new RectF(mRectFs[i]);
                }
            } else if(previousState == 3){
                //左滑恢复
                if(fractions[i] > 0){
                    if(!pageChanging && i == currentPage){
                        mRectFs[i] = new RectF(mRectFs[i].left , mRectFs[i].top , mRectFs[i].left + width,mRectFs[i].bottom);
                    } else if(pageChanging && i == previousPage){
                        mRectFs[i] = new RectF(mRectFs[i].left , mRectFs[i].top , mRectFs[i].left + width,mRectFs[i].bottom);
                    } else {
                        mRectFs[i] = new RectF(mRectFs[i].right - width , mRectFs[i].top,mRectFs[i].right,mRectFs[i].bottom);
                    }
                } else {
                    mRectFs[i] = new RectF(mRectFs[i]);
                }
            } else if(previousState == 4){
                //右滑恢复
                if(fractions[i] > 0){
                    if(!pageChanging && i == currentPage){
                        mRectFs[i] = new RectF(mRectFs[i].right - width , mRectFs[i].top,mRectFs[i].right , mRectFs[i].bottom);
                    } else if(pageChanging && i == previousPage){
                        mRectFs[i] = new RectF(mRectFs[i].right - width , mRectFs[i].top,mRectFs[i].right , mRectFs[i].bottom);
                    } else {
                        mRectFs[i] = new RectF(mRectFs[i].left , mRectFs[i].top,mRectFs[i].left + width,mRectFs[i].bottom);
                    }
                } else {
                    mRectFs[i] = new RectF(mRectFs[i]);
                }
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
        currentPage = 0;
        previousPage = 0;
        fractions = new float[mPageCount];
        mPointPaths = new Path[mPageCount];
        mRectFs = new RectF[mPageCount];
        mCompletePath = new Path();
        mPointWidth = mWidth / mPageCount;
        int pointRight = mPointWidth;
        mPointRadius = mPointWidth / 4;
        for(int i = 0 ; i < fractions.length ; i++){
            //置第一个点为当前点
            if(i == 0){
                fractions[i] = 1f;
                mRectFs[i] = new RectF(0 , 0 , pointRight ,  mHeight / 4);
            } else {
                fractions[i] = 0f;
                mRectFs[i] = new RectF( pointRight - mPointRadius, 0 , pointRight , mHeight / 4);
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
        Log.i("offset",positionOffset + "");
        boolean lastoffset = false;
        int currentPosition = pageChanging ? previousPage : currentPage;
        //规避掉左滑时最后一个positionOffset为0的情况
        if(Math.abs(positionOffset - previousOffset) > 0.99 && previousOffset > 0.99){
            positionOffset = 1f;
            lastoffset = true;
        }
        //右滑时第一次滑动的情况
        if(positionOffset > 0.9 && Math.abs(positionOffset - previousOffset) > 0.9){
            previousOffset = 1f;
        }
        if(positionOffset - previousOffset > 0){
            //左滑
            //根据position的左边的点判断之前的操作是否为右滑,规避掉currentPosition = 0 时的情况
            if(currentPosition - 1 != -1 && fractions[currentPosition - 1] > 0){
                setJoiningFraction(currentPosition,positionOffset);
                setJoiningFraction(currentPosition - 1 , 1 - positionOffset);
                Log.i("previousFraction","4:curFraction:" + fractions[currentPosition] + "," + "currentPos: " + currentPosition);
                Log.i("previousFraction","4:PreFraction:" + fractions[currentPosition - 1] + "," + "previousPos: " + (currentPosition - 1));
                Log.i("previousState" ,"previousState:" + 4);
                previousState = 4;
            } else {
                setJoiningFraction(currentPosition ,1 - positionOffset);
                setJoiningFraction(currentPosition + 1 ,positionOffset);
                Log.i("previousFraction","1:curFraction:" + fractions[currentPosition] + "," + "currentPos: " + currentPosition);
                Log.i("previousState" ,"previousState:" + 1);
                previousState = 1;
            }

        } else if(positionOffset - previousOffset < 0){
            //右滑
            //根据position的右边的点判断之前的操作是否为左滑,规避掉currentPosition = pageCount 时的情况
            if(currentPosition + 1 < mPageCount && fractions[currentPosition + 1] > 0){
                setJoiningFraction(currentPosition ,1 - positionOffset);
                setJoiningFraction(currentPosition + 1 , positionOffset);
                Log.i("previousState" ,"previousState:" + 3);
                previousState = 3;
            } else {
                Log.i("joinFraction","currentPos:" + positionOffset + ",nextPos:" + (1- positionOffset));
                setJoiningFraction(currentPosition ,positionOffset);
                setJoiningFraction(currentPosition - 1 , 1 - positionOffset);
                Log.i("previousState" ,"previousState:" + 2);
                previousState = 2;
            }

        } else {
            //不移动
            previousState = 0;
        }
        if(lastoffset){
            previousOffset = 0;
        } else {
            previousOffset = positionOffset;
        }
    }

    @Override
    public void onPageSelected(int position) {
//        if (isAttachedToWindow) {
            // this is the main event we're interested in!
        Log.i("selectPos","selectPos:" + position);
            setSelectedPage(position);
//        } else {
//            // when not attached, don't animate the move, just store immediately
//
//        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(state == ViewPager.SCROLL_STATE_IDLE) {
            pageChanging = false;
            Log.i("scrollState" , "state_idle");
        } else if(state == ViewPager.SCROLL_STATE_DRAGGING){
            Log.i("scrollState" , "state_drag");
        } else if(state == ViewPager.SCROLL_STATE_SETTLING){
            Log.i("scrollState" , "state_settl");
        }
    }

    private void setSelectedPage(int now) {
        if (now == currentPage) return;

        pageChanging = true;
        previousPage = currentPage;
        currentPage = now;
        for(int i = 0 ; i < mPageCount ; i++){
            if(i != currentPage && i != previousPage){
                fractions[i] = 0f;
            }
        }
//        final int steps = Math.abs(now - previousPage);
//
//        if (steps > 1) {
//            if (now > previousPage) {
//                for (int i = 0; i < steps; i++) {
//                    setJoiningFraction(previousPage + i, 1f);
//                }
//            } else {
//                for (int i = -1; i > -steps; i--) {
//                    setJoiningFraction(previousPage + i, 1f);
//                }
//            }
//        }
    }

    private void setJoiningFraction(int dot, float fraction) {
        if (dot < fractions.length) {

            if (dot == 1) {
                //Log.d("PageIndicator", "dot 1 fraction:\t" + fraction);
            }

            fractions[dot] = fraction;
          handler.sendMessage(obtainMessage(1));
        }
    }

    private Message obtainMessage(int what){
        Message msg = new Message();
        msg.what = what;
        return msg;
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
