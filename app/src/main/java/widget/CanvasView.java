package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;


/**
 * Title: CanvasView
 * Description:
 * Copyright: Copyright (c) 2014-2016 gjfax.com
 * Company: 广金所
 * Author: 刘加彦
 * Date: 2016/6/29
 * Version: 1.0
 */
public class CanvasView extends BasePaintView {

    private RectF rectF = null;

    private int functionCode = -1;

    public static final int CODE_NORMALSTATE = 0;
    public static final int CODE_TRANSLATE = 1;
    public static final int CODE_SCALE = 2;

    public CanvasView(Context context) {
        this(context,null);
    }

    public CanvasView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        rectF = new RectF(0,-400,400,0);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(canvas != null && super.getmPaint() != null){
                Trans(canvas,getmPaint());
        }
    }

    public void paint(int code){
        functionCode = code;
        invalidate();
    }

    public void Trans(Canvas canvas, Paint paint) {
        switch(functionCode){
            case CODE_NORMALSTATE:
                paint.setStyle(Paint.Style.STROKE);
                canvas.translate(getScreenSize()[0] / 2, getScreenSize()[1] / 2);
                paint.setColor(Color.BLACK);           // 绘制黑色矩形
                canvas.drawRect(rectF,paint);
                break;
            case CODE_TRANSLATE:
                break;
            case CODE_SCALE:
                paint.setStyle(Paint.Style.STROKE);
                canvas.translate(getScreenSize()[0] / 2, getScreenSize()[1] / 2);
                canvas.scale(0.5f,0.5f);                // 画布缩放
                paint.setColor(Color.BLUE);            // 绘制蓝色矩形
                canvas.drawRect(rectF,paint);
                break;
            default:
                break;
        }
    }
}
