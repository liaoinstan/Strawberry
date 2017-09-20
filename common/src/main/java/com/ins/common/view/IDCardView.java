package com.ins.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/8/10.
 */
public class IDCardView extends TextView {

    //身份证高宽比
    public static final float LV = 85.6f / 54.0f;

    public IDCardView(Context context) {
        super(context);
    }

    public IDCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IDCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (size / LV), mode);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
