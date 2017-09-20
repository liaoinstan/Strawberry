package com.ins.common.helper;

import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

/**
 * Created by liaoinstan on 2016/7/26 0026.
 * 使用SwipeRefreshLayout实现下拉刷新的时候，没有上拉加载啊很蛋疼
 * 这个工具类可以为SwipeRefreshLayout添加加载更多的监听
 * 实际上是检测RecyclerView是否滚到底部，是则回调，这种自动加载方式在数据量多的列表中效果意外的好啊
 */
public class SwipeHelper {

    public static void setSwipeListener(final SwipeRefreshLayout swipe, RecyclerView recyclerView, final OnSwiperFreshListener listener) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    boolean b = ViewCompat.canScrollVertically(recyclerView, 1);
                    //recyclerview已不能向下滑动（已滑动到底部）
                    if (!b && !swipe.isRefreshing()) {
                        if (listener != null) listener.onLoadmore();
                    }
                }
            }
        });
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (listener != null) listener.onRefresh();
            }
        });
    }

    public interface OnSwiperFreshListener {
        /**
         * 下拉刷新，回调接口
         */
        void onRefresh();

        /**
         * 上拉加载，回调接口
         */
        void onLoadmore();
    }
}
