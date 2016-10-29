package widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import project.ljy.animationutils.R;


/**
 * Title: CheckView
 * Description:
 * Copyright: Copyright (c) 2014-2016 gjfax.com
 * Company: 广金所
 * Author: 刘加彦
 * Date: 2016/6/30
 * Version: 1.0
 * need to update: 添加了插值器和触发方法，根据代码增加插值器接口
 */
public class CheckView extends BasePaintView {

    private static final int STATE_NOTGOU = 0;
    private static final int STATE_GOU = 1;
    private static final int STATE_WAIT = -1;

    /**起始帧数*/
    private static final int START_PICTURE = 0;
    /**帧数总量*/
    private static final int NUMBER_PICTURE = 13;

    private int state_animation  = STATE_WAIT;

    /**用于动画的自定义属性，获取时第几帧*/
    public static final String VALUE_FRAME = "count_pic";

    /**打钩动画*/
    private ObjectAnimator takeGouAnimator;

    /**取消打钩动画*/
    private ObjectAnimator takeNotGouAnimator;

    /**插值器*/
    private Interpolator interpolator;

    /**view的显示区域*/
    Rect view_rect ;

    /**图片的宽高*/
    int picWidth = 0;
    int picHeight = 0;

    Bitmap bitmap_pic;

    /**每次绘制时的起始X坐标*/
    int cal_width = 0;

    /**显示的图片宽度*/
    int view_picWidth = 0;

    /**view的宽高*/
    int layout_width = 0;
    int layout_height =0;

    /**图片计数*/
    int count_pic = 1;

    public CheckView(Context context) {
        this(context,null);
    }

    public CheckView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CheckView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        calculateImage();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = getmPaint();
        if(paint != null && canvas != null && bitmap_pic != null){
            canvas.translate(layout_width/2,layout_width/2);
            canvas.drawBitmap(bitmap_pic,new Rect(cal_width,0,cal_width + view_picWidth,picHeight),view_rect,paint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        layout_width = w;
        layout_height = h;
        view_rect = new Rect( 0-layout_width/2 ,0-layout_height/2 ,layout_width/2 , layout_height/2);   //根据view的大小设置显示区域的大小
    }

    /**
     * 计算出需要截取的图片区域
     */
    private void calculatePictureRect(int frame_pic){
        if(state_animation == STATE_NOTGOU && count_pic > START_PICTURE){                                    //展示反向动画状态
            state_animation = STATE_NOTGOU;
            count_pic = frame_pic;
            cal_width -= view_picWidth ;
        }
        else if(state_animation == STATE_NOTGOU && count_pic == START_PICTURE){                //反向动画完成
            state_animation = STATE_WAIT;
            count_pic = frame_pic;
            cal_width -= view_picWidth ;
        }
        else if( state_animation == STATE_WAIT){                                                //初始状态或打完勾的状态
            if(count_pic == START_PICTURE){
                state_animation = STATE_GOU;
                cal_width = 0;
            }
            else{
                state_animation = STATE_NOTGOU;
            }
        }
        else if(state_animation == STATE_GOU && count_pic < NUMBER_PICTURE){                    //展示动画状态
            state_animation = STATE_GOU;
            if(cal_width >= picWidth - view_picWidth){
                cal_width = picWidth - view_picWidth;
            }
            else {
                cal_width += view_picWidth;
            }
            count_pic = frame_pic ;
        }
        else if(state_animation == STATE_GOU && count_pic == NUMBER_PICTURE){                   //打勾动画完成
            state_animation = STATE_WAIT;
            count_pic = frame_pic ;
        }
        invalidate();
    }

    /**
     * 获取需要截取的图片大小和截取部分的宽高
     */
    private void calculateImage(){
            bitmap_pic = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.checkmark);
        if(bitmap_pic != null){
            picWidth = bitmap_pic.getWidth();
            picHeight = bitmap_pic.getHeight();
            view_picWidth = picWidth / NUMBER_PICTURE;
        }
    }

    /**开启打勾动画*/
    public void takeGou(){
        if(takeGouAnimator == null){
            takeGouAnimator = ObjectAnimator.ofInt(this , VALUE_FRAME , START_PICTURE , NUMBER_PICTURE);
            if(interpolator != null){
                takeGouAnimator.setInterpolator(interpolator);
            }
            else {
                takeGouAnimator.setInterpolator(new LinearInterpolator());
            }
        }
        takeGouAnimator.start();
    }

    public void takeNotGou(){
        if(takeNotGouAnimator == null){
            takeNotGouAnimator = ObjectAnimator.ofInt(this , VALUE_FRAME , NUMBER_PICTURE , START_PICTURE);
            if(interpolator != null) {
                takeNotGouAnimator.setInterpolator(interpolator);
            }
            else {
                takeNotGouAnimator.setInterpolator(new LinearInterpolator());
            }
            }
        takeNotGouAnimator.start();
    }

    public int getCount_pic() {
        return count_pic;
    }

    public void setCount_pic(int count_pic) {
        this.count_pic = count_pic;
        calculatePictureRect(count_pic);

    }

    public boolean isChecked(){
        return count_pic == NUMBER_PICTURE;
    }

    public void setInterpolator(Interpolator interpolator){
        this.interpolator = interpolator;
    }
}
