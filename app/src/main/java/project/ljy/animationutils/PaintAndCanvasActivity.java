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
             50f,50f,0.0f,1f
        };
        int[] colors = new int[]{
                Color.parseColor("#CCCCCC"), Color.parseColor("#555555"), Color.parseColor("#888888"),
                Color.parseColor("#101010")
        };
        mPieView.initPaint(false,24);
        mPieView.setmAnimationTime(3000);
        mPieView.setAndCalculateData(data,colors);
        mPieView.show();

    }

    private void init(){
        mPieView = (PieView) findViewById(R.id.pv_myview);
    }
}
