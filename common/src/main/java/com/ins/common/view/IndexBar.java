package com.ins.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ins.common.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * liaoinstan
 * IndexBar是SideBar的装饰者，代理了SideBar的对外接口，如果需要控制被装饰者SideBar，使用getSideBar()方法获取实例
 * IndexBar在SideBar的基础上实现了选中文字的显示和动画
 */

public class IndexBar extends ViewGroup {

    private int mHeight, mWidth;
    private Context context;
    private Paint mPaint;
    private float centerY;
    private String tag = "";
    private boolean isShowTag;
    private int position;
    //被装饰者
    private SideBar sideBar;
    //可配置项
    private float circleRadius = 40;
    private float textSize = 33;
    //一组默认颜色，用于滑动时显示，可以设置setColors()
    private List<Integer> colors = new ArrayList<Integer>() {{
        add(Color.parseColor("#EC5745"));
        add(Color.parseColor("#377caf"));
        add(Color.parseColor("#4ebcd3"));
        add(Color.parseColor("#6fb30d"));
        add(Color.parseColor("#FFA500"));
        add(Color.parseColor("#bf9e5a"));
    }};

    private int getColor(int seed) {
        return colors.get(seed % colors.size());
    }

    public IndexBar(Context context) {
        this(context, null);
    }

    public IndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setWillNotDraw(false);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtil.dp2px(context, 30), ViewGroup.LayoutParams.WRAP_CONTENT);
        sideBar = new SideBar(context);
        sideBar.setLayoutParams(layoutParams);
        sideBar.addOnIndexChangeListener(new SideBar.OnIndexChangeListenerEx() {
            @Override
            public void onActionDown() {
            }

            @Override
            public void onActionUp() {
                setTagStatus(false);
            }

            @Override
            public void onIndexChanged(float centerY, String tag, int position) {
                setDrawData(centerY, tag, position);
            }
        });
        addView(sideBar);
        if (isInEditMode()) {
            setDrawData(200, "C", 3);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        childWidth = sideBar.getMeasuredWidth();
        //把SideBar排列到最右侧
        sideBar.layout((mWidth - childWidth), 0, mWidth, mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //MeasureSpec封装了父View传给子View的布局要求
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (wMode) {
            case MeasureSpec.EXACTLY:
                mWidth = wSize;
                break;
            case MeasureSpec.AT_MOST:
                mWidth = wSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        switch (hMode) {
            case MeasureSpec.EXACTLY:
                mHeight = hSize;
                break;
            case MeasureSpec.AT_MOST:
                mHeight = hSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    private int childWidth;

    /**
     * @param centerY  要绘制的圆的Y坐标
     * @param tag      要绘制的字母Tag
     * @param position 字母Tag所在位置
     */
    private void setDrawData(float centerY, String tag, int position) {
        this.position = position;
        this.centerY = centerY;
        this.tag = tag;
        isShowTag = true;
        invalidate();
    }

    /**
     * 通过标志位来控制是否来显示圆
     *
     * @param isShowTag 是否显示圆
     */
    private void setTagStatus(boolean isShowTag) {
        this.isShowTag = isShowTag;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isShowTag) {
            //根据位置来不断变换Paint的颜色
            mPaint.setColor(getColor(position));
            //绘制圆和文字
            canvas.drawCircle((mWidth - childWidth) / 2, centerY, DensityUtil.dp2px(context, circleRadius), mPaint);
            mPaint.setColor(Color.WHITE);
            mPaint.setTextSize(DensityUtil.dp2px(context, textSize));
            canvas.drawText(tag, (mWidth - childWidth - mPaint.measureText(tag)) / 2, centerY - (mPaint.ascent() + mPaint.descent()) / 2, mPaint);
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return super.generateLayoutParams(p);
    }

    //################### 对外方法 #####################

    public SideBar getSideBar() {
        return sideBar;
    }

    //################## get & set ####################

    public float getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(float circleRadius) {
        this.circleRadius = circleRadius;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public List<Integer> getColors() {
        return colors;
    }

    public void setColors(List<Integer> colors) {
        this.colors = colors;
    }

    //################## sideBar 代理方法 ####################

    public void addOnIndexChangeListener(SideBar.OnIndexChangeListener listener) {
        sideBar.addOnIndexChangeListener(listener);
    }

    public void setIndexStr(String indexStr) {
        sideBar.setIndexStr(indexStr);
    }

    public int getColorText() {
        return sideBar.getColorText();
    }

    public void setColorText(int colorText) {
        sideBar.setColorText(colorText);
    }

    public int getColorTextHot() {
        return sideBar.getColorTextHot();
    }

    public void setColorTextHot(int colorTextHot) {
        sideBar.setColorTextHot(colorTextHot);
    }

    public int getColorBk() {
        return sideBar.getColorBk();
    }

    public void setColorBk(int colorBk) {
        sideBar.setColorBk(colorBk);
    }

    public int getColorBkHot() {
        return sideBar.getColorBkHot();
    }

    public void setColorBkHot(int colorBkHot) {
        sideBar.setColorBkHot(colorBkHot);
    }
}
