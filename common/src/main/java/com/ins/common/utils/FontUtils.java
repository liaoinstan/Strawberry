package com.ins.common.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by liaoinstan on 2016/8/8 0008.
 * 字体工具类，设置粗体、自定义字体等
 */
public class FontUtils {

    //设置兰亭细字体，路径自己配置
    public static void font_ltx(final Context context, final View root) {
        applyFont(context, root, "fonts/ltx.ttf");
    }

    public static void applyFont(final Context context, final View root, final String fontName) {
        try {
            if (root instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) root;
                for (int i = 0; i < viewGroup.getChildCount(); i++)
                    applyFont(context, viewGroup.getChildAt(i), fontName);
            } else if (root instanceof TextView)
                ((TextView) root).setTypeface(Typeface.createFromAsset(context.getAssets(), fontName));
        } catch (Exception e) {
            L.e("Font", String.format("Error occured when trying to apply %s font for %s view", fontName, root));
            e.printStackTrace();
        }
    }

    //设置粗体
    public static void boldText(TextView textView) {
        if (textView != null) {
            textView.getPaint().setFakeBoldText(true);
        }
    }

    //设置粗体，可以传入view，但只有TextView会生效
    public static void boldText(View view) {
        if (view instanceof TextView) {
            boldText((TextView) view);
        }
    }
}
