package com.ins.common.common;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by liaoinstan on 2016/4/14.
 * 可以直接给RecyclerView设置item点击监听，不用在adapter中添加了
 * 但是缺点是只能到item被点击，但不知道是哪个view被点击，适合比较单一的需求
 */
public class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener{
    private GestureDetectorCompat mGestureDetector;
    private RecyclerView recyclerView;

    public OnRecyclerItemClickListener(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
        mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(),new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child!=null) {
                RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
                onItemClick(vh);
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child!=null) {
                RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
                onLongClick(vh);
            }
        }
    }

    public void onLongClick(RecyclerView.ViewHolder vh){}
    public void onItemClick(RecyclerView.ViewHolder vh){}
}
