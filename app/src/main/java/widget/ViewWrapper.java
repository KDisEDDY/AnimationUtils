package widget;

import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Title: ViewWrapper
 * Description:
 * Copyright: Copyright (c) 2014-2016 gjfax.com
 * Company: 广金所
 * Author: 刘加彦
 * Date: 2017/7/25
 * Version: 1.0
 */


public class ViewWrapper{
    WeakReference<View> view ;
    public ViewWrapper(View view){
        this.view = new WeakReference<>(view);
    }

    public int getWidth() {
        return view.get().getLayoutParams().width;
    }

    public void setWidth(int width) {
        view.get().getLayoutParams().width = width;
        view.get().requestLayout();
    }
}
