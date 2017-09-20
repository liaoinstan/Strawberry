package com.ins.common.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.ins.common.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * liaoinstan
 * 粘性顶部
 * 设置一列Item的tag集合
 * 数据集合：
 * 例：{"A","A","A","B","B","C"}
 * 那么如上集合在position 为 0,3,5 的位置上方分别有 A,B,C 的粘性顶部
 */

public class ItemDecorationSortStickTop extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private List<String> tags;
    private int dividerHeight = 80;
    private Context mContext;
    private final Rect mBounds = new Rect();
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

    //从tags集合中查询某个tag是不同类型的第几个，例集合{A,A,A,B,B,C}，那么A->1 ,B->2 ,C->3
    //需要这个字段的唯一作用就是通过Index从颜色集合里取出对应颜色
    private int getPositionByArr(String tag) {
        for (int i = 0; i < tags.size(); i++) {
            if (tag.equals(tags.get(i))) {
                return i;
            }
        }
        return 0;
    }

    public ItemDecorationSortStickTop(Context context) {
        this(context, null);
    }

    public ItemDecorationSortStickTop(Context context, List<Integer> colors) {
        this.mContext = context;
        if (colors != null) {
            this.colors = colors;
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null) {
            return;
        }
        canvas.save();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewLayoutPosition();
            if (tags == null || tags.size() == 0 || tags.size() <= position || position < 0) {
                continue;
            }
            if (position == 0) {
                //第一条数据有bar
                drawTitleBar(canvas, parent, child, tags.get(position));
            } else if (position > 0) {
                if (TextUtils.isEmpty(tags.get(position))) continue;
                //与上一条数据中的tag不同时，该显示bar了
                if (!tags.get(position).equals(tags.get(position - 1))) {
                    drawTitleBar(canvas, parent, child, tags.get(position));
                }
            }
        }
        canvas.restore();
    }

    /**
     * 绘制bar
     *
     * @param canvas Canvas
     * @param parent RecyclerView
     * @param child  ItemView
     */
    private void drawTitleBar(Canvas canvas, RecyclerView parent, View child, String tag) {
        final int left = 0;
        final int right = parent.getWidth();
        //返回一个包含Decoration和Margin在内的Rect
        parent.getDecoratedBoundsWithMargins(child, mBounds);
        final int top = mBounds.top;
        final int bottom = mBounds.top + Math.round(ViewCompat.getTranslationY(child)) + dividerHeight;
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(left, top, right, bottom, mPaint);
        //根据位置不断变换Paint的颜色
        mPaint.setColor(getColor(getPositionByArr(tag)));
        mPaint.setTextSize(40);
        canvas.drawCircle(DensityUtil.dp2px(mContext, 42.5f), bottom - dividerHeight / 2, 35, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText(tag, DensityUtil.dp2px(mContext, 42.5f), bottom - dividerHeight / 3, mPaint);
    }

//    @Override
//    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
//        //用来绘制悬浮框
//        int position = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
//        if (tags == null || tags.size() == 0 || tags.size() <= position || position < 0) {
//            return;
//        }
//        final int bottom = parent.getPaddingTop() + dividerHeight;
//        mPaint.setDotColor(Color.WHITE);
//        canvas.drawRect(parent.getLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + dividerHeight, mPaint);
//        ColorUtil.setPaintColor(mPaint, tagsStr.indexOf(tags.get(position)));
//        mPaint.setTextSize(40);
//        canvas.drawCircle(DensityUtil.dp2px(mContext, 42.5f), bottom - dividerHeight / 2, 35, mPaint);
//        mPaint.setDotColor(Color.WHITE);
//        canvas.drawText(tags.get(position), DensityUtil.dp2px(mContext, 42.5f), bottom - dividerHeight / 3, mPaint);
//    }

    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (tags == null || tags.size() == 0) {
            return;
        }
        //用来绘制悬浮框
        LinearLayoutManager manager = ((LinearLayoutManager) (parent.getLayoutManager()));
        int position = manager.findFirstVisibleItemPosition();
        if (tags.size() <= position || position < 0) {
            return;
        }
        int first = manager.findFirstCompletelyVisibleItemPosition();
        View child = manager.findViewByPosition(first);
        if (first == 0) {
            return;
        }
        int transLate = 0;
        //与上一条数据中的tag不同时，该显示bar了
        if (!tags.get(first).equals(tags.get(first - 1))) {
            if (child.getTop() < dividerHeight * 2 && child.getTop() > 80) {
                transLate = child.getTop() - dividerHeight * 2;
            }
        }

        final int top = parent.getPaddingTop() + transLate;
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(parent.getLeft(), top, parent.getRight() - parent.getPaddingRight(), top + dividerHeight, mPaint);
        mPaint.setColor(getColor(getPositionByArr(tags.get(position))));
        mPaint.setTextSize(40);
        canvas.drawCircle(DensityUtil.dp2px(mContext, 42.5f), top + dividerHeight / 2, 35, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText(tags.get(position), DensityUtil.dp2px(mContext, 42.5f), top + dividerHeight * 2 / 3, mPaint);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (tags == null || tags.size() == 0 || tags.size() <= position || position < 0) {
            super.getItemOffsets(outRect, view, parent, state);
            return;
        }
        if (position == 0) {
            //第一条数据有bar
            outRect.set(0, dividerHeight, 0, 0);
        } else if (position > 0) {
            if (TextUtils.isEmpty(tags.get(position))) return;
            //与上一条数据中的tag不同时，该显示bar了
            if (!tags.get(position).equals(tags.get(position - 1))) {
                outRect.set(0, dividerHeight, 0, 0);
            }
        }
    }

    //###################  get & set  ####################

    public List<Integer> getColors() {
        return colors;
    }

    public void setColors(List<Integer> colors) {
        this.colors = colors;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getDividerHeight() {
        return dividerHeight;
    }

    public void setDividerHeight(int dividerHeight) {
        this.dividerHeight = dividerHeight;
    }
}
