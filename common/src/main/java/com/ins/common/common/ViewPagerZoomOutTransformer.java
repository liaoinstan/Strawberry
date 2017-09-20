package com.ins.common.common;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * Created by liaoinstan on 2017/3/20.
 * ViewPager滑动特效切换动画 变形器
 *
 * 使用技巧：
 * 1：ViewPager如果希望同时显示左右2页的话，要给viewPager外层容器(一般FrameLayout)添加android:clipChildren="false"属性,如下：
 * <FrameLayout
 *      android:layout_gravity="center"
 *      android:layout_width="360dp"
 *      android:layout_height="200dp"
 *      android:clipChildren="false">
 *      <com.ins.feast.ui.view.ScaleViewPager
 *          android:layout_gravity="center"
 *          android:clipChildren="false"
 *          android:layout_width="200dp"
 *          android:layout_height="match_parent"/>
 *</FrameLayout>
 *
 * 2：ViewPager设置缓存为全部缓存（仿预加载），设置边距margin0，设置
 * viewPager.setOffscreenPageLimit(results.size());
 * viewPager.setPageMargin(0);
 * viewPager.setPageTransformer(true, new ViewPagerZoomOutTransformer(0.9f, 0.9f, 1));
 */
public class ViewPagerZoomOutTransformer implements ViewPager.PageTransformer {

    //最小缩放比例
    private float MIN_SCALE = 0.70f;
    //最小透明比例
    private float MIN_ALPHA = 0.5f;
    //边缘可见的数量(1页显示3个为1，显示5个为2，以此类推)
    private int SHOWCOUNT = 1;

    public ViewPagerZoomOutTransformer() {
    }

    public ViewPagerZoomOutTransformer(float minScale, float minAlpha, int showcount) {
        this.MIN_SCALE = minScale;
        this.MIN_ALPHA = minAlpha;
        this.SHOWCOUNT = showcount;
    }

    public void transformPage(View view, float position) {
        Log.e("liao", position + "");
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();
        if (position < - SHOWCOUNT) { // [-Infinity,-2)
            // This page is way off-screen to the left.
            view.setAlpha(MIN_ALPHA);
            view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);
        } else if (position <= SHOWCOUNT) { // [-2,2]
            // Modify the default slide transition to shrink the page as well
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
//                view.setTranslationX(horzMargin - vertMargin / 2);
                view.setScaleX(1 + (1 - MIN_SCALE) * position / SHOWCOUNT);
                view.setScaleY(1 + (1 - MIN_SCALE) * position / SHOWCOUNT);
            } else {
//                view.setTranslationX(-horzMargin + vertMargin / 2);
                view.setScaleX(1 - (1 - MIN_SCALE) * position / SHOWCOUNT);
                view.setScaleY(1 - (1 - MIN_SCALE) * position / SHOWCOUNT);
            }

            // Scale the page down (between MIN_SCALE and 1)

            // Fade the page relative to its size.
            view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);
            view.setAlpha(MIN_ALPHA);
        }
    }
}
