package com.ins.common.utils;

import android.support.design.widget.TabLayout;

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
}
