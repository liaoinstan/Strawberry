package com.ins.common.utils;

import android.view.View;

/**
 * Created by liaoinstan on 2017/6/29.
 */

public class ViewUtil {
    /**
     * 测量View的宽高
     */
    public static void measureWidthAndHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
    }
}
