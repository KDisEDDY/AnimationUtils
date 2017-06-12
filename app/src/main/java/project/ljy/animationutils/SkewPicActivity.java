package project.ljy.animationutils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;

public class SkewPicActivity extends Activity {

    ImageView mFaceImg ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skew_pic);
        mFaceImg = (ImageView) findViewById(R.id.iv_face);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        Matrix matrix = new Matrix();
        matrix.setSkew(0.267f,0);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_face).copy(Bitmap.Config.ARGB_8888,false);
        Bitmap afterBitmap = Bitmap.createBitmap(bitmap.getWidth()
                + (int) (bitmap.getWidth() * 0.267f), bitmap.getHeight()
                + (int) (bitmap.getHeight() * 0), bitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        canvas.drawBitmap(bitmap,matrix,new Paint());
        mFaceImg.setImageBitmap(afterBitmap);
    }
}
