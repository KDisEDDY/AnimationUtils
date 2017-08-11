package project.ljy.animationutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import widget.CanvasView;

public class CanvasTestActivity extends AppCompatActivity {

    private CanvasView canvasView;

    int functionCode = CanvasView.CODE_SHADER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_test);
        canvasView = (CanvasView) findViewById(R.id.canvas);
    }

    public void click(View view){
       if(functionCode == CanvasView.CODE_NORMALSTATE){
           functionCode = CanvasView.CODE_SCALE;
           canvasView.paint(functionCode);
       } else  if(functionCode == CanvasView.CODE_SCALE){
           functionCode = CanvasView.CODE_NORMALSTATE;
           canvasView.paint(functionCode);
       } else if(functionCode == CanvasView.CODE_SHADER){
           functionCode = CanvasView.CODE_SHADER;
           canvasView.paint(functionCode);
       } else {
           functionCode = CanvasView.CODE_NORMALSTATE;
           canvasView.paint(functionCode);
       }
    }


}
