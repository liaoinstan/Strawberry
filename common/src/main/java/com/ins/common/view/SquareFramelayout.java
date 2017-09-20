package com.ins.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2016/8/10.
 */
public class SquareFramelayout extends FrameLayout {
    public SquareFramelayout(Context context) {
        super(context);
    }

    public SquareFramelayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareFramelayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
