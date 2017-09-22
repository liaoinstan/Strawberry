package com.ins.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.ins.common.utils.ScreenUtil;

/**
 * Created by Administrator on 2017/9/22.
 */

public class SlidingMenu extends HorizontalScrollView {

    private static final float radio = 0.4f;//菜单占屏幕宽度比
    private final int mScreenWidth;
    private final int mMenuWidth;
    private boolean once = true;

    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScreenWidth = ScreenUtil.getScreenWidth(context);
        mMenuWidth = (int) (mScreenWidth * radio);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        setHorizontalScrollBarEnabled(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (once) {
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            wrapper.getChildAt(0).getLayoutParams().width = mScreenWidth;
            wrapper.getChildAt(1).getLayoutParams().width = mMenuWidth;
            once = false;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if (Math.abs(scrollX) > mMenuWidth / 2) {
                    this.smoothScrollTo(mMenuWidth, 0);
                } else {
                    this.smoothScrollTo(0, 0);
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }
}
