package com.ins.common.utils.others;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;

/**
 * Created by liaoinstan on 15-6-14.
 * 动画工具类 ( APP常用动画封装 )
 *
 * 这个类的方法很多局限性，还有待更好的封装，也许使用某些动画框架更好
 * 目前没有想出一个好的方案
 * 有待完善
 *
 * 问题记录：
 * 用户可能短时间内触发多个动画，也有可能在动画执行完之前再次触发该动画，各个场景的交互结果都要求我们要对动画的执行过程严格控制，不能一个start就不管了
 * 要控制一个动画必须要拥有它的Animator，怎么去管理这些对象？
 */
public class AnimUtil {

    private static ValueAnimator animator;

    public static void show(final View v, int height) {
        show(v, height, null);
    }

    public static void show(final View v, int height, final OnAnimCallBack callBack) {
        Log.e("show", "animator != null:" + (animator != null) + "animator.isRunning():" + (animator != null ? animator.isRunning() : "null"));
        if (animator != null && animator.isRunning()) {
            animator.cancel();
            Log.e("show", "cancel");
        }
        int starth = v.getLayoutParams().height;
        Log.e("show", starth + "->" + height);
        v.setVisibility(View.VISIBLE);
        animator = ValueAnimator.ofInt(starth, height);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                v.getLayoutParams().height = value;
                v.setLayoutParams(v.getLayoutParams());
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (callBack != null) callBack.onAnimEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    public static void hide(final View v) {
        Log.e("hide", "animator != null:" + (animator != null) + "animator.isRunning():" + (animator != null ? animator.isRunning() : "null"));
        if (animator != null && animator.isRunning()) {
            animator.cancel();
            Log.e("hide", "cancel");
        }
        int starth = v.getLayoutParams().height;
        Log.e("hide", starth + "->0");
        animator = ValueAnimator.ofInt(starth, 0);
        animator.setDuration(300);
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

    public interface OnAnimCallBack {
        void onAnimEnd();
    }
}
