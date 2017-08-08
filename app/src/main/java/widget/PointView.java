package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by EDDY on 2017/8/8.
 */

public class PointView extends BasePaintView {

    private RectF mRect ;
    private Path mPath  ;
    private int mWidth = 0;
    private int mHeight = 0;

    public PointView(Context context) {
        super(context);
        initPath();
    }

    public PointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPath();
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
        mRect = new RectF(0,0,w/4,h);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.addRoundRect(mRect,mWidth/4,mHeight, Direction.CCW);
        canvas.drawPath(mPath,getmPaint());
    }
}
