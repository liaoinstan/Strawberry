package com.ins.common.common;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/7/11.
 */

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;
    private boolean includeEdge;
    private boolean reverseLayout;
    private int orientation = GridLayoutManager.VERTICAL;

    public GridSpacingItemDecoration(int spanCount, int spacing, int orientation, boolean includeEdge) {
        this(spanCount, spacing, orientation, includeEdge, false);
    }

    public GridSpacingItemDecoration(int spanCount, int spacing, int orientation, boolean includeEdge, boolean reverseLayout) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.orientation = orientation;
        this.includeEdge = includeEdge;
        this.reverseLayout = reverseLayout;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if (includeEdge) {
            if (orientation == GridLayoutManager.VERTICAL) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.top = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.bottom = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.left = spacing;
                }
                outRect.right = spacing; // item bottom
            }
        } else {
            if (orientation == GridLayoutManager.VERTICAL) {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    if (!reverseLayout) {
                        outRect.top = spacing; // item top
                    } else {
                        outRect.bottom = spacing; // item top
                    }
                }
            } else {
                outRect.top = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.bottom = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    if (!reverseLayout) {
                        outRect.left = spacing; // item top
                    } else {
                        outRect.right = spacing; // item top
                    }
                }
            }
        }
    }
}
