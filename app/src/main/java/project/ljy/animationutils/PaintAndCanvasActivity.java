package project.ljy.animationutils;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import widget.PieView;

public class PaintAndCanvasActivity extends AppCompatActivity {

    private PieView mPieView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_and_canvas);
        init();
        float[] data = new float[]{
             -778.0f,-746.67f,0.0f,4270.15f
        };
        int[] colors = new int[]{
                Color.parseColor("#CCCCCC"), Color.parseColor("#555555"), Color.parseColor("#888888"),
                Color.parseColor("#101010")
        };
        mPieView.initPaint(false,18);
        mPieView.setmAnimationTime(3000);
        mPieView.setAndCalculateData(data,colors);
        mPieView.show();

    }

    private void init(){
        mPieView = (PieView) findViewById(R.id.pv_myview);
    }
}
