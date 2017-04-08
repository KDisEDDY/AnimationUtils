package widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import project.ljy.animationutils.R;


/**
 * Title: MyLoadingView
 * Author: EDDY
 * time: 2016/10/3.
 * Description:
 */
public class CountCircleView extends View {

    private int mPaintColor = R.color.colorAccent;

    private float mPiantSize = 14.0f;

    private Paint mPiant =null;

    private int mWidth = 0;

    private int mHeight = 0;

    private RectF mRectF = null;

    private int[] centerPoint = null;

    private float mCurrentAngle = 0;

    private float mPerAngle = 0;

    /** 倒计时数 */
    private int mCount = 0;

    public CountCircleView(Context context) {
        super(context);
        init(context,null,0);
    }

    public CountCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);
    }

    public CountCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(centerPoint != null){
            canvas.translate(centerPoint[0],centerPoint[1]);
            canvas.rotate(-90);
            canvas.drawArc(mRectF,0,mCurrentAngle,false,mPiant);
        }
    }

    private void init(Context context , AttributeSet attrs, int defStyleAttr){
        TypedArray t = null;
        try {
            t = context.obtainStyledAttributes(attrs, R.styleable.LogicCountView,
                    0, defStyleAttr);
            mPaintColor = t.getColor(R.styleable.LogicCountView_paintColor,mPaintColor);
            mPiantSize = t.getDimension(R.styleable.LogicCountView_paintSize,mPiantSize);
        } finally {
            if (t != null) {
                t.recycle();
            }
        }
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
        mWidth = w;
        mHeight = h;
        mPiant = new Paint();
        mPiant.setColor(mPaintColor);
        mPiant.setStrokeWidth(mPiantSize);
        mPiant.setStyle(Paint.Style.STROKE);
    }

    public void setmCount(int mCount) {
        this.mCount = mCount;
        mPerAngle = 360 / mCount;

    }
    public void startAnimation(){
        invalidate();
        mCurrentAngle += mPerAngle;
        if(mCurrentAngle >= 360){
            mCurrentAngle = 360;
        }
    }
}
