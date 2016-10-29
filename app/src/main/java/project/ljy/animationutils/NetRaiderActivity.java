package project.ljy.animationutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import widget.RaiderView;

public class NetRaiderActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_raider);
        relativeLayout = (RelativeLayout) findViewById(R.id.rl_rootLayout);
        RaiderView raiderView = new RaiderView(this, null, 0);
        if (relativeLayout != null) {
            relativeLayout.removeAllViews();
            relativeLayout.addView(raiderView);
        }
    }
}
