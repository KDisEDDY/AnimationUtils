package widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import project.ljy.animationutils.R;
import utils.CountDownTimerUtil;

/**
 * Title: CountView
 * Description:
 * Copyright: Copyright (c) 2014-2016 gjfax.com
 * Company: 广金所
 * Author: 刘加彦
 * Date: 2016/10/12
 * Version: 1.0
 */
public class CountView extends RelativeLayout {

    private RelativeLayout mRlayout = null;

    private CountCircleView mCountCircleView = null;

    private TextView mCountTimeTxt = null;

    private Context mCot = null;

    private CountDownTimerUtil timerUtil = null;

    private OnCountFinshListener mCountFinishListener = null;

    public CountView(Context context) {
        super(context);
        mCot = context;
        init();
    }

    public CountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCot = context;
        init();
    }

    public CountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCot = context;
        init();
    }

    private void init(){
        View view = LayoutInflater.from(mCot).inflate(R.layout.layout_count_circle,this);
        mRlayout = (RelativeLayout) view.findViewById(R.id.rl_rootLayout);
        mCountCircleView = (CountCircleView) view.findViewById(R.id.view_count_circle);
        mCountTimeTxt = (TextView) view.findViewById(R.id.tv_count_timer);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //调用ViewGroup类中测量子类的方法
        measureChildren(MeasureSpec.AT_MOST, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
            mRlayout.layout(0,0,mRlayout.getMeasuredWidth(),mRlayout.getMeasuredHeight());
    }

    public void countTime(int second){
        if(mCountCircleView != null){
            mCountCircleView.setmCount(second);
        }
        timerUtil = new CountDownTimerUtil(second*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mCountTimeTxt.setText(String.valueOf(millisUntilFinished / 1000));
                mCountCircleView.startAnimation();
            }

            @Override
            public void onFinish() {
                mCountTimeTxt.setText("0");
                mCountCircleView.startAnimation();
                if(mCountFinishListener != null){
                    mCountFinishListener.onFinish();
                }
            }
        };
        timerUtil.start();
    }

    public void cancelCount(){
        if(timerUtil != null){
            timerUtil.cancel();
        }
    }

    public void setmCountFinishListener(OnCountFinshListener mCountFinishListener) {
        this.mCountFinishListener = mCountFinishListener;
    }

    public interface OnCountFinshListener{
        void onFinish();
    }
}
