package project.ljy.animationutils;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

public class IndexActivity extends AppCompatActivity {

    private final static int ANIM_ROUTE = 1;
    private final static int ANIM_ROUTE_MOVEX = 2;
    private final static int ANIM_XML = 3;
    ImageView redBallImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initView();
        getViewPosition(redBallImg);
        setAnimation(ANIM_XML);
    }

    private void initView(){
        redBallImg = (ImageView) findViewById(R.id.img_redball);
    }

    /**设置控件的动画*/
    private void setAnimation(int type){
        switch(type){
            case ANIM_ROUTE:
                PropertyValuesHolder route_pro = PropertyValuesHolder.ofFloat("rotationY",0.0f,360.0f);
                ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(redBallImg,route_pro);
                objectAnimator.setRepeatCount(3);
                objectAnimator.start();
                break;
            //添加了插值器
            case ANIM_ROUTE_MOVEX:
                Keyframe k1 = Keyframe.ofFloat(0.0f,redBallImg.getX());
                Keyframe k2 = Keyframe.ofFloat(1.0f,redBallImg.getX() + 100f);
                Keyframe k3 = Keyframe.ofFloat(2.0f,redBallImg.getX() + 150f);
                PropertyValuesHolder movex_pro = PropertyValuesHolder.ofKeyframe("x",k1,k2,k3);
                PropertyValuesHolder route_pro2 = PropertyValuesHolder.ofFloat("rotationY",0.0f,360.0f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(redBallImg,movex_pro,route_pro2);
                objectAnimator2.setRepeatCount(2);
                objectAnimator2.setInterpolator(new DecelerateInterpolator());
                objectAnimator2.start();
                break;
            //xml读取不了animator的动画资源
            case ANIM_XML:
                try{
                    AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(
                            this, R.animator.anim_redball);
                    set.setTarget(redBallImg);
                    set.start();
                }
                catch (Resources.NotFoundException e){
                    e.printStackTrace();
                }
                break;
            default:break;
        }
    }

    /**redball复位*/
    private void resetRedBall(){

    }

    private int[] getViewPosition(View view){
        int[] location = new int[2];
        return location;
    }

    public void click(View view){
        Toast.makeText(IndexActivity.this, "click", Toast.LENGTH_SHORT).show();
    }
}
