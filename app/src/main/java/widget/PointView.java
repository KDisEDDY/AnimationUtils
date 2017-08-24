package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;

/**
 * Created by EDDY on 2017/8/8.
 */

public class PointView extends BasePaintView {

    private RectF mRectF;
    private Path mPath  ;
    private int mWidth = 0;
    private int mHeight = 0;
    private int mLeft = 0;
    private int mRight = 0;

    //动画方式constant
    public static final int LEFT_EXPAND = 1;
    public static final int LEFT_SCALE = 2;
    public static final int RIGHT_EXPAND = 3;
    public static final int RIGHT_SCALE = 4;

    private int mAnimPatton = 0;

    private int mOffset = 0;

    public PointView(Context context) {
        this(context,null);
    }

    public PointView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPath();
    }

    private void initPath(){
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF = new RectF( 0,0,w,h);
        mLeft = 0;
        mRight = w;
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mAnimPatton == LEFT_EXPAND){

        } else if(mAnimPatton == LEFT_SCALE){

        } else if(mAnimPatton == RIGHT_EXPAND){

        } else if(mAnimPatton == RIGHT_SCALE){

        }
        mPath.addRoundRect(mRectF,mWidth / 4 ,mHeight, Direction.CW);
        canvas.drawPath(mPath,getmPaint());
    }

    public void setOffset(int offset , int mAnimPatton){
        mOffset = offset;
        this.mAnimPatton = mAnimPatton;
        ViewCompat.postInvalidateOnAnimation(PointView.this);
    }
}
