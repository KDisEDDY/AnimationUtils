package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Title: RaiderView
 * Description:
 * Copyright: Copyright (c) 2014-2016 gjfax.com
 * Company: 广金所
 * Author: 刘加彦
 * Date: 2016/7/5
 * Version: 1.0
 */
public class RaiderView extends BasePaintView {

    /**控件宽高*/
    private int layout_width = 0;
    private int layout_height = 0;

    /**雷达图的边数*/
    private int bian = 6;

    /**雷达图的最长的边*/
    private int redius = 0;

    /**中心点位置*/
    private int CenterX = 0;
    private int CenterY = 0;
    private float angle = (float) (Math.PI*2/bian);

    /**当前控件的paint*/
    private Paint viewPaint = null;

    private Path path = null;

    private boolean isFirstLoad = true;

    public RaiderView(Context context) {
        super(context);
        initPaint();
    }

    public RaiderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public RaiderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(viewPaint != null && isFirstLoad){
            drawPolygon(canvas,viewPaint);
            isFirstLoad = false;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        layout_width = w;
        layout_height = h;
        CenterX = layout_width / 2;
        CenterY = layout_height / 2;
        calculateRedius();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        //测量控件宽度
        setMeasuredDimension(measure(widthMeasureSpec,true),measure(heightMeasureSpec,false));
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 计算长宽
     * @param measureSpec  测量尺寸
     * @param isWidth  标记是否是宽
     * @return
     */
    private int measure(int measureSpec, boolean isWidth) {
        int result;
        int mode = View.MeasureSpec.getMode(measureSpec);
        int size = View.MeasureSpec.getSize(measureSpec);
        int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
        if (mode == View.MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
            result += padding;
            if (mode == View.MeasureSpec.AT_MOST) {
                if (isWidth) {
                    result = Math.max(result, size);
                } else {
                    result = Math.min(result, size);
                }
            }
        }
        return result;
    }

    private void calculateRedius(){
        redius = (int) (Math.min(layout_width, layout_height)/2*0.9);

    }

    /**
     * 绘制正多边形
     */
    private void drawPolygon(Canvas canvas, Paint paint){
        if(path == null){
            path = new Path();
        }
        float r = redius/(bian-1);      //r是蜘蛛丝之间的间距
        for(int i=1;i<bian;i++){        //中心点不用绘制
            float curR = r*i;           //当前半径
            path.reset();
            for(int j=0;j<bian;j++){
                if(j==0){
                    path.moveTo(CenterX+curR,CenterY);
                }else{
                    //根据半径，计算出蜘蛛丝上每个点的坐标
                    float x = (float) (CenterX+curR* Math.cos(angle*j));
                    float y = (float) (CenterY+curR* Math.sin(angle*j));
                    path.lineTo(x,y);
                }
            }
            path.close();//闭合路径
            canvas.drawPath(path, paint);
        }
        drawlines(canvas,paint);
        drawLineText(canvas,paint);
    }

    private void drawlines(Canvas canvas , Paint paint){
        if(path == null){
            path = new Path();
        }
        for (int i = 0; i < bian; i++) {
            path.reset();
            path.moveTo(CenterX,CenterY);
            float pointX = (float) (redius* Math.cos(angle * i) + CenterX);
            float pointY = (float) (redius * Math.sin(angle * i) + CenterY);
            path.lineTo(pointX,pointY);
            canvas.drawPath(path,paint);
        }
    }

    private void drawLineText(Canvas canvas , Paint paint){
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        String[] titles = new String[]{
          "a999","b11","c2222","d43r","e22222222222222222","f33333333333333"
        };
        for(int i=0;i<bian;i++){
            float x = (float) (CenterX+(redius+fontHeight/2)* Math.cos(angle*i));
            float y = (float) (CenterY+(redius+fontHeight/2)* Math.sin(angle*i));
            if(angle*i>=0&&angle*i<= Math.PI/2){//第4象限
                canvas.drawText(titles[i], x,y,paint);
            }else if(angle*i>=3* Math.PI/2&&angle*i<= Math.PI*2){//第3象限
                canvas.drawText(titles[i], x,y,paint);
            }else if(angle*i> Math.PI/2&&angle*i<= Math.PI){//第2象限
                float dis = paint.measureText(titles[i]);//文本长度
                canvas.drawText(titles[i], x-dis,y,paint);
            }else if(angle*i>= Math.PI&&angle*i<3* Math.PI/2){//第1象限
                float dis = paint.measureText(titles[i]);//文本长度
                canvas.drawText(titles[i], x-dis,y,paint);
            }
        }
    }

    private void initPaint(){
        viewPaint = getmPaint();
        if(viewPaint != null){
            viewPaint.setStyle(Paint.Style.STROKE);
        }
    }
}
