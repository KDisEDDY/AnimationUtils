package project.ljy.animationutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import widget.CheckView;

public class PictureActivity extends AppCompatActivity {

    private CheckView checkView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        checkView = (CheckView) findViewById(R.id.checkview);
        checkView.setInterpolator(new DecelerateInterpolator());
        checkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.checkview){
                    CheckView checkView = (CheckView) v;
                    if(checkView.isChecked()){
                        checkView.takeNotGou();
                    }
                    else {
                        checkView.takeGou();
                    }
                }
            }
        });
    }


}
