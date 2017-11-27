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
    }

    public static ToobarTansColorHelper getInstance() {
        return new ToobarTansColorHelper();
    }

    private Context context;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private int heightMax;
    private int colorStart;
    private int colorEnd;
    private float cutPoint = 0.7f;  //阈值（当拖拽百分比超过该值的时候会给外部控件一次机会回调来调整UI）

    private OnPointListener onPointListener;

    public ToobarTansColorHelper with(RecyclerView recyclerView, Toolbar toolbar) {
        this.toolbar = toolbar;
        this.recyclerView = recyclerView;
        initBase();
        return this;
    }

    public ToobarTansColorHelper onPointCallback(OnPointListener onPointListener) {
        this.onPointListener = onPointListener;
        return this;
    }

    private void initBase() {
        this.context = recyclerView.getContext();
//        this.heightMax = Resources.getSystem().getDisplayMetrics().heightPixels / 3;
        this.heightMax = DensityUtil.dp2px(200);
        this.colorStart = Color.parseColor("#002f76b8");
        this.colorEnd = ContextCompat.getColor(context, R.color.am_blue);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    /**
     * 根据来拉距离计算百分比[0,1] -> [未拉动,拉满]
     */
    private float caculAlpha(int scrollY) {
        float y = Math.abs(scrollY);
        if (y <= 0) {
            return 0;
        } else if (y >= heightMax) {
            return 1;
        } else {
            return y / heightMax;
        }
    }

    private void setToolbar(float lv) {
        //得到 颜色 估值器
        ArgbEvaluator evaluator = new ArgbEvaluator();
        //根据positionOffset得到渐变色，因为positionOffset本身为0~1之间的小数所以无需多做处理了
        int colorTrans = (int) evaluator.evaluate(lv, colorStart, colorEnd);
        toolbar.setBackgroundColor(colorTrans);
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

    //记录RecyclerView的scrollY
    private int scrollY;
    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            scrollY -= dy;
            float lv = caculAlpha(scrollY);
            setToolbar(lv);
            setPointCallback(lv);
        }
    };

    public interface OnPointListener {
        void onPoint(boolean upOrDown);
    }
}
