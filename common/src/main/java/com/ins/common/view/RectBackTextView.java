package com.ins.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.ins.common.R;
import com.ins.common.utils.DensityUtil;

public class RectBackTextView extends AppCompatTextView {

    private int color_bk = Color.parseColor("#4cc791");

    private GradientDrawable bkdrawble;

    public RectBackTextView(Context context) {
        this(context, null);
    }

    public RectBackTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectBackTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RectBackTextView);
        color_bk = typedArray.getColor(R.styleable.RectBackTextView_bk_color, color_bk);
        typedArray.recycle();
        bkdrawble = new GradientDrawable();
        bkdrawble.setShape(GradientDrawable.RECTANGLE);
        bkdrawble.setColor(color_bk);
        bkdrawble.setCornerRadius(DensityUtil.dp2px(context, 2));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setBackground(bkdrawble);
    }

    public void setColor(int color) {
        bkdrawble.setColor(color);
    }

    public void setColorSrc(int colorSrc) {
        bkdrawble.setColor(ContextCompat.getColor(getContext(), colorSrc));
    }

}