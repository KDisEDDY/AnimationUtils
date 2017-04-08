package widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
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

    private TextView mCountTimeTxt = null;

    private LogicCountView mLCView;

    private Context mCot = null;

    private CountDownTimerUtil timerUtil = null;

    private OnCountFinshListener mCountFinishListener = null;

    private OnTickListener mOnTickListener = null;

    private int mTick = 0;

    private int mResumeTime = 0;

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
        mCountTimeTxt = (TextView) view.findViewById(R.id.tv_count_timer);
        mLCView = (LogicCountView) view.findViewById(R.id.view_count_circle);
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

    public void countTime(final int second){
        timerUtil = new CountDownTimerUtil(second*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(mTick == 3){
                    mCountTimeTxt.setText("2/3");
                    mOnTickListener.onTick(mTick);
                } else if(mTick == 6){
                    mCountTimeTxt.setText("3/3");
                    mOnTickListener.onTick(mTick);
                    Log.i("ResumeTime","resumeTime : " + mResumeTime);
                    mLCView.resetAnimation(new Animation7_15());
                }
                ++ mTick;
                mResumeTime-- ;
            }

            @Override
            public void onFinish() {
                mCountFinishListener.onFinish();
                Log.i("ResumeTime","resumeTime : " + mResumeTime);
                mOnTickListener.onTick(second);
            }
        };
        mResumeTime = second;
        mLCView.setmResumeTime(mResumeTime);
        timerUtil.start();
        mLCView.resetAnimation(new Animation1_3());
    }

    public void cancelCount(){
        if(timerUtil != null){
            timerUtil.cancel();
            mLCView.resetAnimation(new AnimationFinal());
        }
    }

    public void setmCountFinishListener(OnCountFinshListener mCountFinishListener) {
        this.mCountFinishListener = mCountFinishListener;
    }

    public void setOnTickListener(OnTickListener mOnTickListener){
        this.mOnTickListener = mOnTickListener;
    }

    public interface OnTickListener{
        void onTick(int tick);
    }

    public interface OnCountFinshListener{
        void onFinish();
    }
}
