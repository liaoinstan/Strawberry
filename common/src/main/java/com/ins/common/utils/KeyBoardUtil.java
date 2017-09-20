package com.ins.common.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Method;

/**
 * Created by liaoinstan on 2016/11/1.
 * 系统软键盘工具类：弹出、收起、禁用等
 */

public class KeyBoardUtil {

    public static void EnableSysKeyBoard(Activity activity, View editText) {
        // 设置不调用系统键盘
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(editText, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showKeybord(Activity activity) {
        if (activity == null) return;
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开
        if (!isOpen) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void hideKeybord(Activity activity) {
        if (activity == null) return;
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开
        if (isOpen) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
            //下面这种方法也可以
            //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
