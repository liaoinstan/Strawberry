package com.ins.common.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.ins.common.common.ConerBkSpan;

/**
 * Created by liaoinstan
 * SpannableString工具类
 * <p>
 * 使用参考：
 * http://www.jianshu.com/p/84067ad289d2
 * <p>
 * new ForegroundColorSpan(Color.parseColor("#0099EE"));        //文字颜色
 * new BackgroundColorSpan(Color.parseColor("#AC00FF30"));      //文字背景
 * new RelativeSizeSpan(1.2f);  //字体大小（相对）
 * new StrikethroughSpan(); //删除线
 * new UnderlineSpan();     //下划线
 * new SuperscriptSpan();   //上标
 * new SubscriptSpan();     //下标
 * new StyleSpan(Typeface.BOLD);ITALIC    //粗体斜体
 * new ImageSpan(drawable); //图片
 * new URLSpan("http://www.jianshu.com/users/dbae9ac95c78"); //超链接
 * new ClickableSpan()      //点击事件，用法特殊，需要实现接口
 * <p>
 * 试试：SpannableStringBuilder
 */
public class SpannableStringUtil {

    //和下面方法功能一致，提供通过颜色资源id来生成SpannableString
    public static SpannableString create(Context context, String[] strs, int[] colorSrcs) {
        int[] colors = new int[colorSrcs.length];
        for (int i = 0; i < colorSrcs.length; i++) {
            colors[i] = ContextCompat.getColor(context, colorSrcs[i]);
        }
        return create(strs, colors);
    }

    //生成一条SpannableString，指定每一段文字和对应颜色，要求strs，colors不为空，且长度一样：不验证自行遵守
    public static SpannableString create(String[] strs, int[] colors) {
        String strall = "";
        for (String str : strs) {
            strall += str;
        }
        SpannableString strSpan = new SpannableString(strall);
        int end = 0;
        for (int i = 0; i < strs.length; i++) {
            int start = end;
            end += strs[i].length();
            strSpan.setSpan(new ForegroundColorSpan(colors[i]), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return strSpan;
    }

    //生成一条SpannableString，指定每一段文字和对应大小，要求strs，size不为空，且长度一样：不验证自行遵守
    public static SpannableString createSize(String[] strs, float[] sizes) {
        String strall = "";
        for (String str : strs) {
            strall += str;
        }
        SpannableString strSpan = new SpannableString(strall);
        int end = 0;
        for (int i = 0; i < strs.length; i++) {
            int start = end;
            end += strs[i].length();
            strSpan.setSpan(new RelativeSizeSpan(sizes[i]), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return strSpan;
    }

    /**
     * 上面方法{@link #create(String[], int[])}的优化版，使用SpannableStringBuilder实现，尚未测试
     */
    public static SpannableStringBuilder createV2(String[] strs, int[] colors) {
        SpannableStringBuilder strSpan = new SpannableStringBuilder("");
        for (int i = 0; i < strs.length; i++) {
            int start = strSpan.length();   //拼接前的长度
            strSpan.append(strs[i]);
            int end = strSpan.length();     //拼接后的长度
            strSpan.setSpan(new ForegroundColorSpan(colors[i]), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        return strSpan;
    }

//    //给Edit设置Hint文字（带图片）
//    private SpannableString makeSearchHint() {
//        SpannableString spannableString = new SpannableString("1 搜索");
//        Drawable drawable = getResources().getDrawable(R.drawable.ic_home_search_edit);
//        drawable.setBounds(0, 0, 42, 42);
//        ImageSpan imageSpan = new ImageSpan(drawable);
//        spannableString.setSpan(imageSpan, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        return spannableString;
//    }

    //给Edit设置Hint文字（带图片）
    public static SpannableString makeImageStartStr(Context context, int src, Rect rect, String text) {
        SpannableString spannableString = new SpannableString(text);
        Drawable drawable = ContextCompat.getDrawable(context, src);
        drawable.setBounds(rect);
        ImageSpan imageSpan = new ImageSpan(drawable);
        spannableString.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString makeConorBkStr(Context context, int colorBkSrc, int colorTextSrc, String title, String content) {
        if (content == null) content = "";
        int colorBk = ContextCompat.getColor(context, colorBkSrc);
        int colorText = ContextCompat.getColor(context, colorTextSrc);
        SpannableString spannableString = new SpannableString(title + content);
        ConerBkSpan roundedBackgroundSpan = new ConerBkSpan(colorBk, colorText);
        spannableString.setSpan(roundedBackgroundSpan, 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    //要使点击事件生效必须给TextView设置如下属性：
    //textView.setMovementMethod(LinkMovementMethod.getInstance());
    //如果要屏蔽点击背景色，如下：
    //textView.setHighlightColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
    public static SpannableString makeClickStr(SpannableString spannableString, int from, int to, View.OnClickListener onClickListener) {
        MyClickableSpan clickableSpan = new MyClickableSpan(onClickListener);
        spannableString.setSpan(clickableSpan, from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static class MyClickableSpan extends ClickableSpan {

        private View.OnClickListener onClickListener;

        public MyClickableSpan(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
            //ds.setColor(color);			//设置可点击文字字体颜色
            ds.clearShadowLayer();
        }

        @Override
        public void onClick(View widget) {
            if (onClickListener != null) onClickListener.onClick(widget);
        }
    }

}
