package com.ins.common.utils;

import android.widget.EditText;

/**
 * EditText常用方法工具类
 */
public class EditTextUtil {

    /**
     * 为EditText设置文字并将光标调整到最后
     */
    public static void setTextWithSelectionAtLast(EditText editText, String text) {
        editText.setText(text);
        editText.setSelection(editText.getText().length());
    }

    public static void disableEditText(EditText editText){
        editText.setFocusable(false);
    }

    public static void enableEditText(EditText editText){
        editText.setFocusable(true);
    }

}
