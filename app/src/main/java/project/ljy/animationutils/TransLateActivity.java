package project.ljy.animationutils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import utils.ViewUtil;

public class TransLateActivity extends AppCompatActivity {

    protected LinearLayout llContent;
    protected Button btnAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_trans_late);
        initView();
    }

    private void initView() {
        llContent = (LinearLayout) findViewById(R.id.ll_content);
        btnAnimation = (Button) findViewById(R.id.btn_animation);
        btnAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimation();
            }
        });
    }

    private void setAnimation() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(llContent, "X", 0, -ViewUtil.getScreenDispaly(this)[0]);
        animator1.setDuration(500);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(llContent, "X", ViewUtil.getScreenDispaly(this)[0], 0);
        animator2.setDuration(500);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animator1,animator2);
        animatorSet.start();
    }
}
