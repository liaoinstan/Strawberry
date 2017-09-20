package com.ins.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.ins.common.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * liaoinstan
 * 侧边栏（筛选A-Z条目，快速定位列表位置）
 * SideBar只提供A-Z条目的选择和处理对应事件，具体选择后的表现，由外部处理，通过添加OnIndexChangeListener可以捕获SideBar的事件回调
 * <p>
 * 一般和{@link IndexBar}连用，IndexBar实现了UI展示层的外部逻辑
 * 如果对UI效果有定制要求，可直接使用SideBar
 */

public class SideBar extends View {

    private int height;
    private int width;
    private Paint paint;
    private int singleHeight;
    private Context context;
    private int MARGIN_BOTTOM = 10;
    private int MARGIN_TOP = 10;
    private String indexStr = "ABCDEFGHIJKLMNOPQRSTUVWXY#";
    private int currentPosition = -1;

    //可配置项
    private int colorText = Color.parseColor("#77000000");          //文字颜色
    private int colorTextHot = Color.parseColor("#fff08519");       //文字选中状态颜色
    private int colorBk = Color.parseColor("#00000000");             //背景色
    private int colorBkHot = Color.parseColor("#10000000");         //背景选中时颜色

    public SideBar(Context context) {
        this(context, null);
    }

    public SideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setColor(colorText);
        paint.setTextSize(35);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //根据总高度计算上下边距（越高边距越大，越挤边距越小）
        //经过大量测试，上下边距取总高度的10%是比较合理的，但是弹出输入法后挤压页面依然有细微的文件重叠，这个规则还有待优化
        MARGIN_BOTTOM = (int) (DensityUtil.px2dp(context, h) / 10);
        MARGIN_TOP = MARGIN_BOTTOM;
        //导航栏居中显示，减去上下边距
        width = w;
        height = h - DensityUtil.dp2px(context, MARGIN_BOTTOM + MARGIN_TOP);
        singleHeight = height / indexStr.length();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < indexStr.length(); i++) {
            if (i == currentPosition) {
                paint.setColor(colorTextHot);
            } else {
                paint.setColor(colorText);
            }
            String textTag = indexStr.substring(i, i + 1);
            float xPos = (width - paint.measureText(textTag)) / 2;
            canvas.drawText(textTag, xPos, singleHeight * (i + 1) + DensityUtil.dp2px(context, MARGIN_TOP), paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor(colorBkHot);
                invalidate();
                callOnActionDown();
            case MotionEvent.ACTION_MOVE:
                //滑动 event.getY()得到在父View中的Y坐标，通过和总高度的比例再乘以字符个数总长度得到按下的位置
                currentPosition = (int) ((event.getY() - getTop() - DensityUtil.dp2px(context, MARGIN_TOP)) / height * indexStr.toCharArray().length);
                if (currentPosition >= 0 && currentPosition < indexStr.length()) {
                    invalidate();
                    String tag = String.valueOf(indexStr.toCharArray()[currentPosition]);
                    callOnIndexChanged(event.getY(), tag, currentPosition);
                }
                break;
            case MotionEvent.ACTION_UP:
                setBackgroundColor(colorBk);
                currentPosition = -1;
                invalidate();
                callOnActionUp();
                break;
        }
        return true;
    }

    //################## 接口回调 ####################

    private void callOnIndexChanged(float centerY, String tag, int position) {
        if (listeners != null) {
            for (OnIndexChangeListener listener : listeners) {
                listener.onIndexChanged(centerY, tag, position);
            }
        }
    }

    private void callOnActionUp() {
        if (listeners != null) {
            for (OnIndexChangeListener listener : listeners) {
                if (listener instanceof OnIndexChangeListenerEx) {
                    ((OnIndexChangeListenerEx) listener).onActionUp();
                }
            }
        }
    }

    private void callOnActionDown() {
        if (listeners != null) {
            for (OnIndexChangeListener listener : listeners) {
                if (listener instanceof OnIndexChangeListenerEx) {
                    ((OnIndexChangeListenerEx) listener).onActionDown();
                }
            }
        }
    }

    //################### 对外方法 ####################

    public void setIndexStr(String indexStr) {
        if (TextUtils.isEmpty(indexStr)) return;
        this.indexStr = indexStr;
        singleHeight = height / indexStr.length();
        invalidate();
    }

    //################## get & set ####################

    public int getColorText() {
        return colorText;
    }

    public void setColorText(int colorText) {
        this.colorText = colorText;
    }

    public int getColorTextHot() {
        return colorTextHot;
    }

    public void setColorTextHot(int colorTextHot) {
        this.colorTextHot = colorTextHot;
    }

    public int getColorBk() {
        return colorBk;
    }

    public void setColorBk(int colorBk) {
        this.colorBk = colorBk;
    }

    public int getColorBkHot() {
        return colorBkHot;
    }

    public void setColorBkHot(int colorBkHot) {
        this.colorBkHot = colorBkHot;
    }

    //################## 对外接口 ####################

    private List<OnIndexChangeListener> listeners = new ArrayList();

    public interface OnIndexChangeListener {
        void onIndexChanged(float centerY, String tag, int position);
    }

    public interface OnIndexChangeListenerEx extends OnIndexChangeListener {
        void onActionDown();

        void onActionUp();
    }

    public void addOnIndexChangeListener(OnIndexChangeListener listener) {
        this.listeners.add(listener);
    }

}
