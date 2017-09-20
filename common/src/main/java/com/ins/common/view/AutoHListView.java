package com.ins.common.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class AutoHListView extends ListView {

    static final int ANIMATION_DURATION = 2000;

    private ValueAnimator animator;

    public AutoHListView(Context context) {
        super(context);
        this.setVerticalScrollBarEnabled(false);
    }

    public AutoHListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setVerticalScrollBarEnabled(false);
    }

    public AutoHListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setVerticalScrollBarEnabled(false);
    }

    public int getTotalHeight() {
        ListAdapter mAdapter = this.getAdapter();
        if (mAdapter == null) {
            return -1;
        }
        int totalHeight = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, this);
            mView.measure(
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            // mView.measure(0, 0);
            totalHeight += mView.getMeasuredHeight();
            Log.w("HEIGHT" + i, String.valueOf(totalHeight));
        }
        ViewGroup.LayoutParams params = this.getLayoutParams();
        params.height = totalHeight  + (this.getDividerHeight() * (mAdapter.getCount() - 1));

        return params.height;
    }

    public void show() {
        if (animator!=null && animator.isRunning()){
            animator.cancel();
        }

        int starth = this.getLayoutParams().height;
        int height = getTotalHeight();
        final View v = this;
        v.setVisibility(View.VISIBLE);
        animator = ValueAnimator.ofInt(starth, height);
        animator.setDuration(ANIMATION_DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                v.getLayoutParams().height = value;
                v.setLayoutParams(v.getLayoutParams());
            }
        });
        animator.start();
    }

    public void hide() {
        if (animator!=null && animator.isRunning()){
            animator.cancel();
        }

        int height = this.getLayoutParams().height;
        final View v = this;
        animator = ValueAnimator.ofInt(height, 0);
        animator.setDuration(ANIMATION_DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                if (value == 0) {
                    v.setVisibility(View.GONE);
                }
                v.getLayoutParams().height = value;
                v.setLayoutParams(v.getLayoutParams());
            }
        });
        animator.start();
    }
}