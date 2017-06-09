package project.ljy.animationutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/** 测试转场动画的跳转界面 */
public class OPT2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opt2);
    }

    //让返回键也执行动画
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0,R.anim.slide_in_bottom_2);
    }
}
