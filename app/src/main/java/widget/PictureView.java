package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.RectF;
import android.graphics.drawable.PictureDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Title: PictureView
 * Description:
 * Copyright: Copyright (c) 2014-2016 gjfax.com
 * Company: 广金所
 * Author: 刘加彦
 * Date: 2016/6/30
 * Version: 1.0
 */
public class PictureView extends View {

    private Picture mPicture ;
    public PictureView(Context context) {
        this(context,null);
    }

    public PictureView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PictureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPicture = new Picture();
        recording();
    }

    private void recording() {
        // 开始录制 (接收返回值Canvas)
        Canvas canvas = mPicture.beginRecording(500, 500);
        // 创建一个画笔
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);

        // 在Canvas中具体操作
        // 位移
        canvas.translate(250,250);
        // 绘制一个圆
        canvas.drawCircle(0,0,100,paint);
        mPicture.endRecording();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(canvas != null && mPicture != null){
            Log.e("test" ,mPicture.getWidth() + " " + mPicture.getHeight());
            canvas.drawPicture(mPicture,new RectF(0,0,mPicture.getWidth(),200));
            // 包装成为Drawable
            PictureDrawable drawable = new PictureDrawable(mPicture);
            // 设置绘制区域 -- 注意此处所绘制的实际内容不会缩放
            drawable.setBounds(0,0,250,mPicture.getHeight());
            // 绘制
            drawable.draw(canvas);
        }
    }
}
