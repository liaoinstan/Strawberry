package com.ins.common.utils;

import android.support.v4.view.ViewPager;

/**
 * Created by liaoinstan on 2017/7/13.
 * ViewPager常用方法
 */

public class ViewPagerUtil {

    //ViewPager翻到下一页，无需担心越界
    public static void next(ViewPager viewPager) {
        int position = viewPager.getCurrentItem();
        goPosition(viewPager, position + 1);
    }

    //ViewPager翻到上一页，无需担心越界
    public static void last(ViewPager viewPager) {
        int position = viewPager.getCurrentItem();
        goPosition(viewPager, position - 1);
    }

    //ViewPager翻到指定页，无需担心越界（有翻页动画）
    public static void goPosition(ViewPager viewPager, int position) {
        goPosition(viewPager, position, true);
    }

    //ViewPager翻到指定页，无需担心越界
    //needAnim :是否需要翻页动画
    public static void goPosition(ViewPager viewPager, int position, boolean needAnim) {
        int count = viewPager.getAdapter().getCount();
        if (position >= 0 && position < count) {
            viewPager.setCurrentItem(position, needAnim);
        }
    }


}
