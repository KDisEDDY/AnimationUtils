package widget;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Title: Animation7_15
 * Description:
 * Copyright: Copyright (c) 2014-2016 gjfax.com
 * Company: 广金所
 * Author: 刘加彦
 * Date: 2017/4/8
 * Version: 1.0
 */
public class Animation7_15 implements AnimationClass {
    @Override
    public ObjectAnimator getAnimation(View view, int time, float mStartAngle, String valueStr) {
        return ObjectAnimator.ofFloat(view,valueStr,mStartAngle ,360).setDuration(time * 1000);
    }
}
