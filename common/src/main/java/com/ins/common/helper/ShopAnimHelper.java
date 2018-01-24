package com.ins.common.helper;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.ins.common.R;
import com.ins.common.common.BezierTypeEvaluator;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.GlideUtil;

/**
 * 加入购物车飞入效果动画，贝塞尔曲线
 */
public class ShopAnimHelper {

    private ShopAnimHelper() {
    }

    public static synchronized ShopAnimHelper newInstance() {
        return new ShopAnimHelper();
    }

    ///////////////////////////////////////////////////////////

    //计算开始结束点坐标，快速开始一个飞车动画
    public void quickStart(final View startView, final View endView, final ViewGroup root, String imgUrl) {

        int imageViewWidth = DensityUtil.dp2px(30);
        int imageViewHight = DensityUtil.dp2px(30);
        int[] startPosition = new int[2];
        int[] endPosition = new int[2];
        int[] parentPosition = new int[2];
        startView.getLocationInWindow(startPosition);
        endView.getLocationInWindow(endPosition);
        root.getLocationInWindow(parentPosition);

        //贝塞尔起始点
        PointF startF = new PointF();
        //贝塞尔结束点
        PointF endF = new PointF();
        //贝塞尔控制点
        PointF controllF = new PointF();

        startF.x = startPosition[0] - parentPosition[0] + startView.getWidth() / 2 - imageViewWidth / 2;
        startF.y = startPosition[1] - parentPosition[1] + startView.getHeight() / 2 - imageViewHight / 2;
        endF.x = endPosition[0] - parentPosition[0] + endView.getWidth() / 2 - imageViewWidth / 2;
        endF.y = endPosition[1] - parentPosition[1] + endView.getHeight() / 2 - imageViewHight / 2;
        controllF.x = (endF.x + startF.x) / 2;
        controllF.y = startF.y * 0.5f;

        final ImageView imageView = new ImageView(startView.getContext());
        root.addView(imageView);
        imageView.getLayoutParams().width = imageViewWidth;
        imageView.getLayoutParams().height = imageViewHight;
        imageView.setVisibility(View.VISIBLE);
        imageView.setX(startF.x);
        imageView.setY(startF.y);
        GlideUtil.loadImg(imageView, R.drawable.none, imgUrl);

        play(root, imageView, startF, endF, controllF);
    }

    /**
     * 开始执行一个基于二阶贝塞尔曲线的属性动画
     *
     * @param root      动画根布局
     * @param view      执行动画的view
     * @param startF    开始点
     * @param endF      结束点
     * @param controllF 控制点
     */
    public void play(final ViewGroup root, final View view, PointF startF, PointF endF, PointF controllF) {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BezierTypeEvaluator(controllF), startF, endF);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                view.setX(pointF.x);
                view.setY(pointF.y);
            }
        });
        valueAnimator.setDuration(400);
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if (listener != null) listener.setAnimStart();
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                root.removeView(view);
                if (listener != null) listener.setAnimEnd();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                root.removeView(view);
                if (listener != null) listener.setAnimEnd();
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        valueAnimator.start();
    }

    //################ 监听回调 ##################

    AnimListener listener;

    public void setOnAnimListener(AnimListener listener) {
        this.listener = listener;
    }

    public interface AnimListener {
        void setAnimStart();

        void setAnimEnd();
    }
}
