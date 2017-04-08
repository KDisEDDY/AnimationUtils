package project.ljy.animationutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import widget.CountView;

public class LoadingCircleActivity extends AppCompatActivity {

    CountView mCountView = null;
    TextView textView = null;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_circle_activiy);
        mCountView = (CountView) findViewById(R.id.v_count);
        textView = (TextView) findViewById(R.id.text1);
        textView.setText(Html.fromHtml("投资截止时间 : <font color=\"0xee814d\">2018.02.28 23:00</font> 剩余2天"));
        if(mCountView != null){
            mCountView.setOnTickListener(new CountView.OnTickListener() {
                @Override
                public void onTick(int tick) {
                    if(tick == 3){

                    } else if(tick == 6){

                    }
                }
            });
            mCountView.countTime(15);
        }
        mCountView.setmCountFinishListener(new CountView.OnCountFinshListener() {
            @Override
            public void onFinish() {
              mCountView.cancelCount();
            }
        });
    }
}
