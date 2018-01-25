package com.ins.common.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * NestedScrollView 在外部无法检测其滚动监听，没有类似于scrollView.setOnScrollChangedListener类似的方法
 * 因此要在外部监听其滚动事件，必须添加内部监听器，将onScrollChanged方法的参数暴露出来
 * ObservableNestedScrollView 仅仅只是暴露滚动监听的坐标位置，没有作出任何功能性改变
 */
public class ObservableNestedScrollView extends NestedScrollView {

    private OnScrollChangedListener onScrollChangedListener;

    public ObservableNestedScrollView(Context context) {
        super(context);
        this.setVerticalScrollBarEnabled(false);
    }

    public ObservableNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setVerticalScrollBarEnabled(false);
    }

    public ObservableNestedScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setVerticalScrollBarEnabled(false);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollChangedListener != null)
            onScrollChangedListener.onScrollChanged(l, t, oldl, oldt);
    }

    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        this.onScrollChangedListener = onScrollChangedListener;
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(int x, int y, int oldx, int oldy);
    }
}