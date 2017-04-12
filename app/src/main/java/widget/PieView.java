package widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Title: PanelView
 * Author: EDDY
 * time: 2016/6/28.
 * Description: 饼状图
 */
public class PieView extends View {

    /**画笔工具*/
    private Paint mPaint = null;
    /**保存绘制状态的变量*/
    private Bitmap mBitmap = null;
    private Rect mDst = null;
    private Rect mSrc = null;
    int[] centerPoint = null;

    /**提供的数据*/
    private float[] mData = null;
    private int[] mColors = null;

    /**默认动画时间*/
    private final static int SECOND_ANIMATIONTIME_DEFAULT = 2000;

    /**可显示的最小角度比例*/
    private final static float MIN_SWEEPANGLE_RATIO = 0.005f;

    /**是否为圆形*/
    private boolean isCircle = true;

    /**用户设置的动画时间*/
    private int mAnimationTime = SECOND_ANIMATIONTIME_DEFAULT;

    /**根据提供的数据计算出来的角度值*/
    private float[] mSweepAngles = null;
    private float[] mStartAngles = null;

    /**绘制时需要测量的数据值*/
    RectF mRectF = null;

    /**判断是否为第一次加载*/
    private boolean isFirstLoad = true;

    /**判断是否没有数据*/
    private boolean isHasData = false;

    /**用于动画的自定义属性，当前角度*/
    public static final String VALUE_ANGLE = "mCurrentSweepAngle";

    /**当前的角度*/
    private float mCurrentSweepAngle = 0.0f;

    private OnAnimationListener mAnimationListener = null;

    public PieView(Context context) {
        this(context,null);
    }

    public PieView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas mCanvas) {
        super.onDraw(mCanvas);
        if(mCanvas != null && mPaint != null){
            if(isHasData){
                mCanvas.translate(centerPoint[0],centerPoint[1]);
                mCanvas.rotate(-90);
                float drawStartAngle;
                float drawSweepAngle;
                mCanvas.drawBitmap(mBitmap, mSrc, mDst,mPaint);
                if(mData == null){
                    return ;
                }
                for (int i = 0; i < mData.length; i++) {
                    if (mSweepAngles[i] != 0) {
                        mPaint.setColor(mColors[i]);
                        drawStartAngle = mStartAngles[i];
                        drawSweepAngle = mSweepAngles[i];
                        if (mCurrentSweepAngle <= drawStartAngle + drawSweepAngle-0.5f) {
                            drawSweepAngle = mCurrentSweepAngle - drawStartAngle;
                            mCanvas.drawArc(mRectF, drawStartAngle, drawSweepAngle, isCircle, mPaint);
                            return;
                        }
                        mCanvas.drawArc(mRectF, drawStartAngle, drawSweepAngle-0.5f, isCircle, mPaint);
                    }
                }
            }
            else {
                mCanvas.translate(centerPoint[0],centerPoint[1]);
                mCanvas.rotate(-90);
                mPaint.setColor(Color.parseColor("#EFEFEF"));
                mCanvas.drawArc(mRectF,0,360.0f,isCircle,mPaint);
            }
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerPoint = new int[]{
                w/2,h/2
        };
        int RectWidth = Math.min(w,h) -70;
        //计算出饼状图的外包正方形的区域
        mRectF = new RectF(- RectWidth/2, - RectWidth/2, RectWidth/2 , RectWidth/2);
        if(isFirstLoad){
            mBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
            mSrc = new Rect(0,0, mBitmap.getWidth(), mBitmap.getHeight());
            mDst = new Rect(0 - centerPoint[0],0 - centerPoint[1] , centerPoint[0],centerPoint[1] );
            isFirstLoad = false;
        }
    }

    /**
     * 初始化画笔和画板控件
     * @param isCircle 是否为圆
     * @param ovalWidth 圆环大小,单位为dp，当isCircle为圆形时，该参数无效
     **/
    public void initPaint(boolean isCircle,int ovalWidth){
        this.isCircle = isCircle;
        mPaint = new Paint();
        if(!isCircle){
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(dp2px(getContext(),ovalWidth));
        }
        else {
            mPaint.setStyle(Paint.Style.FILL);
        }
        mPaint.setAntiAlias(true);
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

    /**
     * 设置控件的数据并计算出相关的角度值
     * @param data 数值
     * @param colors 数值对应的颜色
     */
    public void setAndCalculateData(float[] data,int[] colors){
        setData(data);
        setmColors(colors);
        calulateAnglesAndTimes();
    }

    /**
     *  设置每块扇形的动画时间
     * @param mAnimationTime 每块扇形的动画时间
     */
    public void setmAnimationTime(int mAnimationTime) {
        this.mAnimationTime = mAnimationTime;
    }

    private void setData(float[] data) {
        this.mData = data;
    }

    private void setmColors(int[] mColors) {
        this.mColors = mColors;
    }

    /**
     * 展示绘制动画
     */
    private void showAnimated(){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this,VALUE_ANGLE,0,360.0f).setDuration(mAnimationTime);
        if(mAnimationListener != null){
            objectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    mAnimationListener.onAnimationEnd();
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
        }
        objectAnimator.start();
    }

    /**
     * 用于展示空数据时的饼状图
     */
    private void showNoneView(){
        invalidate();
    }

    public void show(){
        if(isHasData){
            showAnimated();
        }
        else {
            showNoneView();
        }
    }

    /**
     * 设置绘制时圆的半径，中心点等数据
     * */
    private void calulateAnglesAndTimes(){
        mSweepAngles = new float[mData.length];
        mStartAngles = new float[mData.length];
        float[] currentData = new float[mData.length];
        float startAngle = 0;
        float dataAmount = 0;
        float hasSweepAngle = 0;
        //数据统计并修改负数据为0
        for (int i = 0; i < mData.length ; i++) {
            if(mData[i] <= 0 ){
                mData[i] = 0;
            }
            else {
                isHasData = true;
            }
            dataAmount += mData[i];
        }

        for(int i = 0 ; i < mData.length ;i++){
            if(mData[i] == 0){
                currentData[i] = mData[i];
            }
            else if(MIN_SWEEPANGLE_RATIO > (mData[i]  /dataAmount)){
                currentData[i] = dataAmount * MIN_SWEEPANGLE_RATIO;
            }
            else {
                currentData[i] = mData[i];
            }
        }
        //清空dataAmount，使用修改过的data数据
        dataAmount = 0;
        for(int i = 0 ; i < currentData.length ;i++){
            if(currentData[i] <= 0 ){
                currentData[i] = 0;
            }
            else {
                isHasData = true;
            }
            dataAmount += currentData[i];
        }

        for (int i = 0; i < currentData.length; i++) {
            float sweepAngle ;
            if(currentData[i] == 0){
                sweepAngle = 0;
            }
            else if(MIN_SWEEPANGLE_RATIO > (currentData[i]  /dataAmount)){
                sweepAngle = 360.0f * MIN_SWEEPANGLE_RATIO;
            }
            else {
                sweepAngle = (360.0f - hasSweepAngle ) * currentData[i] / dataAmount;
            }
            mSweepAngles[i] = sweepAngle ;
            if(i == 0 && sweepAngle != 0){
                mStartAngles[i] = 0;
                startAngle += sweepAngle ;
            }
            else if( sweepAngle != 0){
                mStartAngles[i] = startAngle;
                startAngle += sweepAngle ;
            }
            hasSweepAngle += sweepAngle ;
            dataAmount -= currentData[i];
        }
    }

    private int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public void resetCanvasState(){
        if(!isFirstLoad){
            mBitmap = Bitmap.createBitmap(mBitmap.getWidth(),mBitmap.getHeight(),Bitmap.Config.ARGB_8888);
            mCurrentSweepAngle = 0.0f;
            mSweepAngles = null;
            mStartAngles = null;
            mData = null;
            invalidate();
        }
    }

    public void setAnimationListener(OnAnimationListener mAnimationListener) {
        this.mAnimationListener = mAnimationListener;
    }

    public interface OnAnimationListener{
        void onAnimationEnd();
    }
}