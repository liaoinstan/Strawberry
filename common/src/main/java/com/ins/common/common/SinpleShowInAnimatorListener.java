package com.ins.common.common;

import android.animation.Animator;
import android.view.View;

/**
 * Created by Administrator on 2018/1/11.
 */

public class SinpleShowInAnimatorListener implements Animator.AnimatorListener {
    private View view;

    public SinpleShowInAnimatorListener(View view) {
        this.view = view;
    }

    @Override
    public void onAnimationStart(Animator animator) {
        if (view != null) view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationEnd(Animator animator) {

    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}
