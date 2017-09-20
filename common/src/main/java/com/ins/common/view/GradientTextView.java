package com.ins.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/2/16.
 */

public class GradientTextView extends TextView {

    public GradientTextView(Context context) {
        super(context);
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GradientTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        CharSequence text = getText();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if(!TextUtils.isEmpty(text)&&text.length()<getMaxEms()){
                super.onDraw(canvas);
                return;
            }
        }
        Paint mPaint = getPaint();
        //获取字体颜色并计算渐变色
        int textColor = getCurrentTextColor();
        int endColor = textColor & 0x00ffffff; //透明度设为0:（颜色值高8位设置为0）
        LinearGradient mLinearGradient = new LinearGradient(0, 0, getMeasuredWidth(), 0, new int[]{textColor, endColor}, new float[]{0.5f, 1f}, Shader.TileMode.REPEAT);
        mPaint.setShader(mLinearGradient);
        super.onDraw(canvas);
    }
}
