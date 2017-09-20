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


/**
 */

public class DotTextView extends AppCompatTextView {

    private int color_dot;
    private int color_select;
    private int color_unselect;

    private GradientDrawable bkdrawble;

    public DotTextView(Context context) {
        this(context, null);
    }

    public DotTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DotTextView);
        color_dot = typedArray.getColor(R.styleable.DotTextView_color_dot, 0);
        color_select = typedArray.getColor(R.styleable.DotTextView_color_select, 0);
        color_unselect = typedArray.getColor(R.styleable.DotTextView_color_unselect, 0);
        typedArray.recycle();
        bkdrawble = new GradientDrawable();
        bkdrawble.setShape(GradientDrawable.RECTANGLE);
        bkdrawble.setColor(color_dot);
        bkdrawble.setCornerRadius(0);//圆角 0
        bkdrawble.setSize(DensityUtil.dp2px(getContext(), 3), DensityUtil.dp2px(getContext(), 12));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setCompoundDrawablesWithIntrinsicBounds(bkdrawble, null, null, null);

        setDotColor(color_dot);
        if (color_select != 0 && isSelected()) setSelected(true);
        if (color_unselect != 0 && !isSelected()) setSelected(false);
    }

    public void setDotColor(int color) {
        bkdrawble.setColor(color);
    }

    public void setDotColorSrc(int colorSrc) {
        setDotColor(ContextCompat.getColor(getContext(), colorSrc));
    }

    public void setAllColor(int color) {
        bkdrawble.setColor(color);
        setTextColor(color);
    }

    public void setAllColorSrc(int colorSrc) {
        setAllColor(ContextCompat.getColor(getContext(), colorSrc));
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected){
            setAllColor(color_select);
        }else {
            setTextColor(color_unselect);
            setDotColor(Color.TRANSPARENT);
        }
    }
}