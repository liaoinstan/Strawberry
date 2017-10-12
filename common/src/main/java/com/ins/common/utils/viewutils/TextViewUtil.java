package com.ins.common.utils.viewutils;

import android.graphics.Paint;
import android.widget.TextView;

/**
 * Created by liaoinstan on 2017/10/12.
 */

public class TextViewUtil {

    //设置删除线
    public static void addDelLine(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    //取消删除线
    public static void removeDelLine(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
    }
}
