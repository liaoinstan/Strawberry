package com.ins.common.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


/**
 * author 边凌
 * date 2016/7/4 0004 11:23
 * desc ${Toast工具类}
 */
@SuppressWarnings("unused")
public final class ToastUtil {
    private static boolean debug;
    private static final int NONE = -1;
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static int sLayoutRes = NONE;
    private static int sTextViewId = NONE;

    private ToastUtil() {
        throw new UnsupportedOperationException();
    }

    public static void init(Application application) {
        context = application.getApplicationContext();
    }

    public static void setDebug(boolean debug) {
        ToastUtil.debug = debug;
    }

    //////////////// show 方法 及其重载 //////////////////

    public static void showToastLong(String msg) {
        showToast(context, msg, Toast.LENGTH_LONG, false);
    }

    @SuppressWarnings("WeakerAccess")
    public static void showToastShort(String msg) {
        showToast(context, msg, Toast.LENGTH_SHORT, false);
    }

    public static void showToastShortDebug(String msg) {
        if (debug) {
            showToast(context, msg, Toast.LENGTH_SHORT, false);
        }
    }

    public static void showToastShort(String msg, boolean needStyle) {
        showToast(context, msg, Toast.LENGTH_SHORT, needStyle);
    }

    @SuppressWarnings("WeakerAccess")
    public static void showToast(Context mContext, String text, int duration, boolean needStyle) {

        Toast mToast;
        if (needStyle && sLayoutRes != NONE && sTextViewId != NONE) {
            View inflate = LayoutInflater.from(mContext).inflate(sLayoutRes, null, false);
            TextView textView = (TextView) inflate.findViewById(sTextViewId);
            textView.setText(text);
            mToast = new Toast(mContext);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setDuration(duration);
            mToast.setView(inflate);
        } else {
            mToast = Toast.makeText(mContext, text, duration);
        }

        mToast.show();
    }

    /**
     * 为Toast设置布局id和文字id
     *
     * @param layoutRes  自定义toast的布局文件
     * @param textViewId 该布局文件中用于展示Toast文字信息的textView的id
     */
    public static void setStyle(@LayoutRes int layoutRes, @IdRes int textViewId) {
        ToastUtil.sLayoutRes = layoutRes;
        ToastUtil.sTextViewId = textViewId;
    }
}