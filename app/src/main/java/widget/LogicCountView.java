package widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import project.ljy.animationutils.R;

/**
 * Title: LogicCountView
 * Description:
 * Copyright: Copyright (c) 2014-2016 gjfax.com
 * Company: 广金所
 * Author: 刘加彦
 * Date: 2017/4/6
 * Version: 1.0
 */
public class LogicCountView extends View {


    private int mPaintColor = R.color.colorAccent;

    private float mPiantSize = 14.0f;

    /**画笔工具*/
    private Paint mPaint = null;

    /**保存绘制状态的变量*/
    int[] centerPoint = null;
    private RectF mRectF = null;

    private int mResumeTime = 0;

    private ObjectAnimator mAnimatorController ;

    /**用于动画的自定义属性，当前角度*/
    public static final String VALUE_ANGLE = "mCurrentSweepAngle";

    /**当前的角度*/
    private float mCurrentSweepAngle = 0.0f;

    public LogicCountView(Context context) {
        this(context,null);
    }

    public LogicCountView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LogicCountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context , AttributeSet attrs){
        TypedArray t = null;
        try {
            t = context.obtainStyledAttributes(attrs, R.styleable.LogicCountView);
            mPaintColor = t.getColor(R.styleable.LogicCountView_paintColor,mPaintColor);
            mPiantSize = t.getDimension(R.styleable.LogicCountView_paintSize,mPiantSize);
        } finally {
            if (t != null) {
                t.recycle();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(centerPoint != null){
            canvas.translate(centerPoint[0],centerPoint[1]);
            canvas.rotate(-90);
            canvas.drawArc(mRectF,0,mCurrentSweepAngle,false,mPaint);
        }
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int RectWidth = Math.min(w,h) -60;
        centerPoint = new int[]{
                w/2,h/2
        };
        //计算出饼状图的外包正方形的区域
        mRectF = new RectF(- RectWidth/2, - RectWidth/2, RectWidth/2 , RectWidth/2);
        mPaint = new Paint();
        mPaint.setColor(mPaintColor);
        mPaint.setStrokeWidth(mPiantSize);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    public void setmResumeTime(int mResumeTime) {
        this.mResumeTime = mResumeTime;
    }

    public void resetAnimation(AnimationClass animationClass){
        if(mAnimatorController != null){
            mAnimatorController.cancel();
        }
        mAnimatorController = animationClass.getAnimation(this,mResumeTime,mCurrentSweepAngle,VALUE_ANGLE);
        mAnimatorController.start();
    }

    /**动画属性的get方法*/
    public float getMCurrentSweepAngle() {
        return mCurrentSweepAngle;
    }

    /**动画属性的set方法*/
    public void setMCurrentSweepAngle(float mCurrentSweepAngle) {
        this.mCurrentSweepAngle = mCurrentSweepAngle;
        invalidate();
    }

}
