package com.ins.common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * 常用单位转换的辅助类
 */
public class DensityUtil {
    private DensityUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * dp转px
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * sp转px
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, Resources.getSystem().getDisplayMetrics());
    }
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * px转dp
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / Resources.getSystem().getDisplayMetrics().scaledDensity);
    }
}
