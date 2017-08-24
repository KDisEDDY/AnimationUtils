package project.ljy.animationutils;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import widget.IndicatorView;

public class IndicatorActivity extends AppCompatActivity {

    protected ViewPager mPager;
    private List<View> mList;
    private ViewPagerAdapter mAdapter;
    protected IndicatorView mIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_indicator);
        initView();
    }


    private void initView() {
        mPager = (ViewPager) findViewById(R.id.vg_page);
        mIndicatorView = (IndicatorView) findViewById(R.id.view_indicator);
        mList = new ArrayList<>();
        for(int i = 0 ; i < 4; i++){
            View view = LayoutInflater.from(this).inflate(R.layout.layout_bg_item ,null);
            switch (i){
                case 0:
                    ((ImageView)view.findViewById(R.id.iv_page)).setImageResource(R.drawable.ic_face);
                    break;
                case 1:
                    ((ImageView)view.findViewById(R.id.iv_page)).setImageResource(R.drawable.ic_face);
                    break;
                case 2:
                    ((ImageView)view.findViewById(R.id.iv_page)).setImageResource(R.mipmap.ic_launcher);
                    break;
                case 3:
                    ((ImageView)view.findViewById(R.id.iv_page)).setImageResource(R.mipmap.default_head);
                    break;
                default:
                    break;
            }
            mList.add(view);
        }
        mAdapter = new ViewPagerAdapter(this,mList);
        mPager.setAdapter(mAdapter);
        mIndicatorView.setPager(mPager);
    }
}
