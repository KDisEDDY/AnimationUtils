package widget;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import utils.ViewUtil;

/**
 * Title: BasePaintView
 * Description:
 * Copyright: Copyright (c) 2014-2016 gjfax.com
 * Company: 广金所
 * Author: 刘加彦
 * Date: 2016/6/29
 * Version: 1.0
 */
public class BasePaintView extends View {

    public Paint getmPaint() {
        return mPaint;
    }

    public void setmPaint(Paint mPaint) {
        this.mPaint = mPaint;
    }

    public Context getmCot() {
        return mCot;
    }

    public void setmCot(Context mCot) {
        this.mCot = mCot;
    }

    public int[] getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(int[] screenSize) {
        this.screenSize = screenSize;
    }

    private Paint mPaint = null;

    private Context mCot = null;

    private int[] screenSize = null;



    public BasePaintView(Context context) {
        super(context);
    }

    public BasePaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasePaintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCot = context;
        initPanelAndCanvas();
    }

    /**
     * 初始化画笔和画板控件
     * */
    private void initPanelAndCanvas(){
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        screenSize = ViewUtil.getScreenDispaly(mCot);
    }

}
