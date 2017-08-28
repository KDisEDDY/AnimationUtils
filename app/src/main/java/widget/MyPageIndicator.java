package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * ClassName  MyPageIndicator
 * Create By ChenHao On 2017/8/28 0028 上午 10:05
 * Description:
 */

public class MyPageIndicator extends View implements ViewPager.OnPageChangeListener,
        View.OnAttachStateChangeListener{

    private boolean isAttachedToWindow;

    private ViewPager mViewPager;

    private int mPointRadius;

    private int mPageCount;

    private int mGap;

    private Paint mPaint;

    private int mLeftPosition;

    private float mCurrentFraction;

    public MyPageIndicator(Context context) {
        this(context,null);
    }

    public MyPageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyPageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);

        mPointRadius = 18;
        mGap = 48;

        if (mViewPager != null) {
            mPageCount = mViewPager.getAdapter().getCount();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mLeftPosition = position;
        mCurrentFraction = 1- positionOffset;
        postInvalidate();
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onViewAttachedToWindow(View v) {
        isAttachedToWindow = true;
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        isAttachedToWindow = false;
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        if (mViewPager != null) {
            mPageCount = mViewPager.getAdapter().getCount();
            mViewPager.addOnPageChangeListener(this);
        }

//        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mViewPager == null || mPageCount == 0) return;

        Log.e("sss",mCurrentFraction+"----"+mLeftPosition);
        //画leftPosition部分的
        float startAngle01 = 90;
        float sweepAngle01 = 180;
        int left01 = getPaddingLeft() + 2 * mLeftPosition * mPointRadius + mGap * mLeftPosition;
        int right01 = left01 + 2 * mPointRadius;
        int top01 = getPaddingTop();

        int bottom01 = top01 + 2 * mPointRadius;
        RectF rect = new RectF(left01, top01, right01, bottom01);
        canvas.drawArc(rect, startAngle01, sweepAngle01, true, mPaint);

        int temp = (int) (2*mPointRadius*mCurrentFraction+right01);
        canvas.drawRect(left01+mPointRadius,top01,2*mPointRadius*mCurrentFraction+left01+mPointRadius,bottom01,mPaint);

        float startAngle02 = -90;
        float sweepAngle02 = 180;
        int left02 = (int) (2*mPointRadius*mCurrentFraction+left01);
        int right02 = left02 + 2*mPointRadius;
        int top02 = getPaddingTop();
        int bottom02 = top02 + 2 * mPointRadius;
        RectF rect1 = new RectF(left02, top02, right02, bottom02);
        canvas.drawArc(rect1, startAngle02, sweepAngle02, true, mPaint);

        if (mLeftPosition != mPageCount ) {
            //画右侧的点
            float startAngle03 = 90;
            float sweepAngle03 = 180;
            int left03 = right02+mGap;
            int right03 = left03 + 2 * mPointRadius;
            int top03 = getPaddingTop();
            int bottom03 = top03 + 2 * mPointRadius;
            RectF rect3 = new RectF(left03, top03, right03, bottom03);
            canvas.drawArc(rect3, startAngle03, sweepAngle03, true, mPaint);

            canvas.drawRect(left03+mPointRadius,top03,2*mPointRadius*(1-mCurrentFraction)+left03+mPointRadius,bottom03,mPaint);

            float startAngle04 = -90;
            float sweepAngle04 = 180;
            int left04 = (int) (2*mPointRadius*(1-mCurrentFraction)+left03);
            int right04 = left04 + 2 * mPointRadius;
            int top04 = getPaddingTop();
            int bottom04 = top04 + 2 * mPointRadius;
            RectF rect4 = new RectF(left04, top04, right04, bottom04);
            canvas.drawArc(rect4, startAngle04, sweepAngle04, true, mPaint);

            for (int i = 0; i < mPageCount; i++) {
                if (i == mLeftPosition || i == mLeftPosition + 1) {
                    continue;
                }
                if (i < mLeftPosition) {
                    float cx = getPaddingLeft() + (i * 2 + 1) * mPointRadius + i * mGap;
                    float cy = getPaddingTop() + mPointRadius;
                    canvas.drawCircle(cx, cy, mPointRadius, mPaint);
                    Log.e("fff", "cx" + cx);
                } else if(i >mLeftPosition + 1){
                    float cx = right04 + (i-mLeftPosition-1)*mGap+(i-mLeftPosition-2)*2*mPointRadius+mPointRadius;
                    float cy = getPaddingTop() + mPointRadius;
                    canvas.drawCircle(cx, cy, mPointRadius, mPaint);
                    Log.e("fff", "cx1--" + cx+"--"+right04+"--"+mPointRadius+"--"+getRequiredWidth());

                }
            }
        }
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
        return getPaddingTop() + mPointRadius * 2 + getPaddingBottom();
    }

    private int getRequiredWidth() {
        return mPageCount * mPointRadius * 2 + (mPageCount - 1) * mGap + 2 * mPointRadius;
    }

    private int getDesiredWidth() {
        return getPaddingLeft() + getRequiredWidth() + getPaddingRight();
    }
}
