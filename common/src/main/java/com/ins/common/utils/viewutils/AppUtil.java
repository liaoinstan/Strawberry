package com.ins.common.utils.viewutils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by Administrator on 2018/1/23.
 * app相关工具
 */

public class AppUtil {
    //复制内容到剪切板
    public static void copyText(Context context, String text) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText("text", text);
        cm.setPrimaryClip(myClip);
    }
}
