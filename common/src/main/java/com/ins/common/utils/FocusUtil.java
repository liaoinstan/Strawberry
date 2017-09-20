package com.ins.common.utils;

import android.view.View;

/**
 * Created by liaoinstan on 2017/6/28.
 */

public class FocusUtil {

    //下面代码使焦点回到顶部，如不加刷新后会自动滚到页面最下方
    public static void focusToTop(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }
}
