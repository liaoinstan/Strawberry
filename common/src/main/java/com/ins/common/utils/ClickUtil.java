package com.ins.common.utils;

/**
 * Created by liaoinstan on 2016/12/5.
 * 点击处理：防止重复点击
 * 一般在点击事件首行加入下面代码就可以了
 * if (ClickUtil.isFastDoubleClick()) return;
 */

public class ClickUtil {

    //最短间隔时间
    private static int clipTime = 800;
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < clipTime) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static boolean isFastDoubleClick(int clipTime) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < clipTime) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
