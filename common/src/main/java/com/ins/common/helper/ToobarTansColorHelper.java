package com.ins.common.helper;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.ins.common.R;
import com.ins.common.utils.DensityUtil;

/**
 * Created by Administrator on 2017/8/9.
 */

public class ToobarTansColorHelper {

    private ToobarTansColorHelper() {
        //默认值
        this.colorStart = Color.parseColor("#002f76b8");
        this.colorEnd = Color.parseColor("#2f76b8");
        this.heightMax = DensityUtil.dp2px(200);
    }

    private Context context;
    private Toolbar toolbar;
    private int heightMax;
    private int colorStart;
    private int colorEnd;
    private float cutPoint = 0.7f;  //阈值（当拖拽百分比超过该值的时候会给外部控件一次机会回调来调整UI）

    private OnPointListener onPointListener;

    public static ToobarTansColorHelper with(Toolbar toolbar) {
        ToobarTansColorHelper INSTANCE = new ToobarTansColorHelper();
        INSTANCE.toolbar = toolbar;
        INSTANCE.context = toolbar.getContext();
        return INSTANCE;
    }

    public ToobarTansColorHelper initColorStart(int colorStart) {
        this.colorStart = colorStart;
        return this;
    }

    public ToobarTansColorHelper initColorEnd(int colorEnd) {
        this.colorEnd = colorEnd;
        return this;
    }

    public ToobarTansColorHelper initMaxHeight(int heightMax) {
        this.heightMax = heightMax;
        return this;
    }

    public ToobarTansColorHelper onPointCallback(OnPointListener onPointListener) {
        this.onPointListener = onPointListener;
        return this;
    }

    public void start(int scroll) {
        float lv = caculAlpha(scroll);
        setToolbar(lv);
        setPointCallback(lv);
    }

    /**
     * 根据来拉距离计算百分比[0,1] -> [未拉动,拉满]
     */
    private float caculAlpha(int scroll) {
        float lv;
        if (scroll <= 0) {
            lv = 0;
        } else if (scroll >= heightMax) {
            lv = 1;
        } else {
            lv = (float) scroll / heightMax;
        }
        return lv;
    }

    private void setToolbar(float lv) {
        //得到 颜色 估值器
        ArgbEvaluator evaluator = new ArgbEvaluator();
        //根据positionOffset得到渐变色，因为positionOffset本身为0~1之间的小数所以无需多做处理了
        int colorTrans = (int) evaluator.evaluate(lv, colorStart, colorEnd);
        if (toolbar != null) toolbar.setBackgroundColor(colorTrans);
    }

    private float lastLv;

    private void setPointCallback(float lv) {
        if (onPointListener != null) {
            if (lastLv < cutPoint && lv >= cutPoint) {
                onPointListener.onPoint(true);
            } else if (lastLv >= cutPoint && lv < cutPoint) {
                onPointListener.onPoint(false);
            }
        }
        lastLv = lv;
    }

    public interface OnPointListener {
        void onPoint(boolean upOrDown);
    }
}
