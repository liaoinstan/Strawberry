package com.ins.common.utils;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

/**
 * by liaoinstan
 * 手机震动工具类
 * 使用必须添加权限：<uses-permission android:name="android.permission.VIBRATE" />
 */
public class VibratorUtil {

    /**
     * @param  ：震动的时长，单位是毫秒
     */
    public static void vibrate(Context context, long milliseconds) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    /**
     * @param pattern：自定义震动模式，数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
     * @param isRepeat：是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */
    public static void vibrate(Context context, long[] pattern, boolean isRepeat) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }
}
