package com.ins.common.interfaces;

import android.support.v7.widget.RecyclerView;

/**
 * Created by liaoinstan on 2016/6/12 0012.
 * RecyclerView 的item点击事件接口，设置在adapter中
 * 其实是为ViewHolder的itemView添加了一个OnClickListener在里面回调了该接口
 *
 * 为什么不把onItemClick封装在BaseAdapter中？
 * 是值得考虑的，但是封装BaseAdapter会让你的Adapter变得难以辨认(?)，这么简单的代码就直接写出来吧，过渡封装会让接手你项目的人难以理解
 */
public interface OnRecycleItemClickListener {
    void onItemClick(RecyclerView.ViewHolder viewHolder);
}
