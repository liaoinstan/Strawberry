package com.ins.common.utils;

import android.support.design.widget.TabLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by liaoinstan on 2017/7/18.
 * TabLayout 常用工具方法
 */

public class TabLayoutUtil {

    //下面方法的重载
    public static void setTab(TabLayout tab, int position, String name) {
        setTab(tab, position, name, false);
    }

    //在TabLayout的指定position处添加一条数据name，如果该item已存在则修改，不存在则新增，该方法需要用在动态改变TabLayout item数量的情况下
    public static void setTab(TabLayout tab, int position, String name, boolean isSelect) {
        TabLayout.Tab t;
        if (tab.getTabAt(position) != null) {
            t = tab.getTabAt(position);
            t.setText(name);
            if (isSelect) t.select();
        } else {
            t = tab.newTab();
            t.setText(name);
            tab.addTab(t, position);
            if (isSelect) t.select();
        }
    }

    //设置选中item 无需担心position是否越界
    public static void setTabCurrent(TabLayout tab, int position) {
        if (tab.getTabAt(position) != null) {
            tab.getTabAt(position).select();
        }
    }

    //设置一个item 无需担心position是否越界
    public static void removeTabAt(TabLayout tab, int position) {
        if (tab.getTabAt(position) != null) {
            tab.removeTabAt(position);
        }
    }

    //把TabLayout的下划线宽度强制为文字宽度
    public static void reflex(final TabLayout tabLayout) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    int dp10 = DensityUtil.dp2px(10);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
//                            TextPaint paint = mTextView.getPaint();
//                            width = (int) paint.measureText(mTextView.getText().toString());
                            width = mTextView.getMeasuredWidth();
                        }
                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = DensityUtil.dp2px(50f);
                        params.rightMargin = DensityUtil.dp2px(50f);
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void fix(TabLayout tab) {
        try {
            Class<?> tablayout = tab.getClass();
            Field tabStrip = tablayout.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
            LinearLayout ll_tab = (LinearLayout) tabStrip.get(tab);
            for (int i = 0; i < ll_tab.getChildCount(); i++) {
                View child = ll_tab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                params.width = DensityUtil.dp2px(50f);
//                params.leftMargin = DensityUtil.dp2px(20f);
//                params.rightMargin = DensityUtil.dp2px(20f);
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
