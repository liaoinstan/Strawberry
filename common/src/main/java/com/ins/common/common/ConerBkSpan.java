package com.ins.common.common;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.ReplacementSpan;

import com.bumptech.glide.load.engine.Resource;
import com.ins.common.utils.App;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.L;

/**
 * Created by liaoinstan on 2017/7/23.
 * 一个自定义Span，用在SpanableString中，提供矩形圆角的背景
 */

public class ConerBkSpan extends ReplacementSpan {

    private int padding_h;
    private int padding_v;
    private int padding_draw;
    private float coner;

    private int color_bk;
    private int color_text;

    public ConerBkSpan(int color_bk, int color_text) {
        this.color_bk = color_bk;
        this.color_text = color_text;
        //初始化默认值
        this.padding_h = DensityUtil.dp2px(App.getContext(), 12);
        this.padding_v = DensityUtil.dp2px(App.getContext(), 3);
        this.coner = DensityUtil.dp2px(App.getContext(), 3);
        this.padding_draw = DensityUtil.dp2px(App.getContext(), 7);
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        RectF rect = new RectF(x, top - padding_v, x + measureText(paint, text, start, end) + padding_h * 2, y + padding_v * 2);
        paint.setColor(color_bk);
        canvas.drawRoundRect(rect, coner, coner, paint);
        paint.setColor(color_text);
        canvas.drawText(text, start, end, x + padding_h, y, paint);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return Math.round(measureText(paint, text, start, end) + padding_h * 2 + padding_draw);
    }

    private float measureText(Paint paint, CharSequence text, int start, int end) {
        return paint.measureText(text, start, end);
    }
}
