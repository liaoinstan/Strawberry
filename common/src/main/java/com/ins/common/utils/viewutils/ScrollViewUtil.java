package com.ins.common.utils.viewutils;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewParent;
import android.widget.ScrollView;

import com.ins.common.utils.L;

/**
 * Created by Administrator on 2017/12/20.
 */

public class ScrollViewUtil {

    //#### 滚动到顶部 ####

    public static void scrollToTop(ScrollView scrollView) {
        if (scrollView == null) return;
        scrollView.scrollTo(0, 0);
    }

    public static void scrollToTop(final NestedScrollView scrollView) {
        if (scrollView == null) return;
        scrollView.scrollTo(0, 0);
    }

    //#### 滚动到指定位置 ####

    public static void scrollTo(ScrollView scrollView, int y) {
        if (scrollView == null) return;
        scrollView.scrollTo(0, y);
    }

    public static void scrollTo(NestedScrollView scrollView, int y) {
        if (scrollView == null) return;
        scrollView.scrollTo(0, y);
    }

    //#### 滚动到某个内部的view ####

    /**
     * 滚动到指定view，这个view必须在Scrollview内部
     * offset 偏移量，默认情况0，如果希望滚动距离比实际计算距离更多或更少，这置这个偏移量来改变
     *
     * @param scrollView
     * @param viewin
     * @param offset
     */

    public static void scrollTo(ScrollView scrollView, View viewin, int offset) {
        if (scrollView == null || viewin == null) return;
        int hight = cacuHightByView(scrollView, viewin) + offset;
        scrollView.scrollTo(0, hight);
    }


    public static void scrollTo(NestedScrollView scrollView, View viewin, int offset) {
        if (scrollView == null || viewin == null) return;
        int hight = cacuHightByView(scrollView, viewin) + offset;
        scrollView.scrollTo(0, hight);
    }


    private static int cacuHightByView(ScrollView scrollView, View viewin) {
        int topAll = viewin.getTop();
        ViewParent p = viewin.getParent();
        while (p != null) {
            topAll += ((View) p).getTop();
            if (p instanceof NestedScrollView) {
                break;
            }
            p = p.getParent();
        }
        return topAll;
    }

    private static int cacuHightByView(NestedScrollView scrollView, View viewin) {
        int topAll = viewin.getTop();
        ViewParent p = viewin.getParent();
        while (p != null) {
            topAll += ((View) p).getTop();
            if (p instanceof NestedScrollView) {
                break;
            }
            p = p.getParent();
        }
        return topAll;
    }
}
