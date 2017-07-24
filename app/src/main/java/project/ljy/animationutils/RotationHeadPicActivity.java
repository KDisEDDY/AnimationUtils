package project.ljy.animationutils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import widget.CircleImageView;

public class RotationHeadPicActivity extends AppCompatActivity {

    ImageView mCircleImageView;
    Button mToNextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation_head_pic);
        mCircleImageView = (ImageView) findViewById(R.id.civ_head_pic);
        mToNextBtn = (Button) findViewById(R.id.toNext);
        mToNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RotationHeadPicActivity.this,OPT2Activity.class));
                overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_bottom);
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        rotateyAnimRun2(mCircleImageView);
    }

    /**
     * 使用AnimatorListenerAdapter
     * @param view
     */
    public void rotateyAnimRun(final View view)
    {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "rotationX", 0.0F,90.0F).setDuration(400);
        final ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "rotationX", 90.0F,180.0F).setDuration(400);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animator1,animator2);
        animator1.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationEnd(Animator animation) {
                ImageView circleImageView = (ImageView) view;
                circleImageView.setImageResource(R.mipmap.ic_launcher);
            }
        });
        animatorSet.start();
    }

    /**
     * 使用AnimatorUpdateListener
     * @param view
     */
    public void rotateyAnimRun2(final View view){
        final ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotationX",0.0F,180.0F).setDuration(800);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            boolean isSet = false;
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatorValue = (float)animation.getAnimatedValue();
                if(animatorValue >= 90.0F && !isSet){
                    isSet = true;
                    CircleImageView circleImageView = (CircleImageView) view;
                    circleImageView.setImageResource(R.mipmap.ic_launcher);
                }
            }
        });
        animator.start();
   }
}
