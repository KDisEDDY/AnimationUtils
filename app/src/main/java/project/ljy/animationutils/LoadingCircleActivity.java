package project.ljy.animationutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import widget.CountView;

public class LoadingCircleActivity extends AppCompatActivity {

    CountView mCountView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_circle_activiy);
        mCountView = (CountView) findViewById(R.id.v_count);
        if(mCountView != null){
            mCountView.countTime(15);
            mCountView.setmCountFinishListener(new CountView.OnCountFinshListener() {
                @Override
                public void onFinish() {
//                    Toast.(LoadingCircleActivity.this, "the count is finished", Toast.LENGTH_SHORT).show();
                    Log.d("ddd","the count is finished");
                }
            });
        }
    }
}
