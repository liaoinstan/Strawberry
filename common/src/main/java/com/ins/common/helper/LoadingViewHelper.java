package com.ins.common.helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * liaoinstan
 * 加载视图处理帮助类
 *
 * 使用：
 * showin & showout
 * 通过控制视图的显示隐藏来实现加载过程中的视图遮挡和动画
 * listView 有 emptyView RecyclerView也有非官方的实现，但是不要这样做
 * 加载数据的视图可能出现任何一个页面，不要让这样普遍的功能随便用跟某一个view关联起来，局限性太大
 * TODO：可能要继续的：
 * TODO：封装一个LoadingViewGroup继承自FramLayout或ViewGroup，内部实现空视图加载视图等将会是最佳方案
 * TODO：封装思路：同一性，拓展性，高度可定制
 *
 * update: 2017.7.19
 * 目前简单实现了一个LoadingLayout {@link com.ins.common.view.LoadingLayout}
 * 在项目中暂时使用这种方案，有待后期优化
 *
 * update: 2017.9.1
 * 已不再直接使用，现在使用更加易用的封装{@link com.ins.common.view.LoadingLayout}
 */
public class LoadingViewHelper {

    public static View showin(ViewGroup root, int src, View preview, View.OnClickListener listener) {
        View showin = showin(root,src,preview,true);
        showin.setOnClickListener(listener);
        return showin;
    }

    public static View showin(ViewGroup root, int src, View preview, boolean needHide, View.OnClickListener listener) {
        View showin = showin(root,src,preview,needHide);
        showin.setOnClickListener(listener);
        return showin;
    }

    public static View showin(ViewGroup root, int src, View preview) {
        return showin(root,src,preview,true);
    }

    /**
     * showin 是否隐藏背景
     */
    public static View showin(ViewGroup root, int src, View preview, boolean needHide) {
        if (preview!=null) {
            root.removeView(preview);
        }
        if (root == null) {
            return null;
        }

        //设置lack
        View loadingView = LayoutInflater.from(root.getContext()).inflate(src, root, false);

        //隐藏其余项目
        if (needHide) {
            int count = root.getChildCount();
            for (int i = 0; i < count; i++) {
                root.getChildAt(i).setVisibility(View.GONE);
            }
        }
        //添加lack
        root.addView(loadingView);

        return loadingView;
    }

    /**
     * out
     */
    public static void showout(ViewGroup root, View viewin) {
        if (root == null || viewin == null) {
            return;
        }
        //删除中心视图
        root.removeView(viewin);
        //显示其余项目
        int count = root.getChildCount();
        for (int i = 0; i < count; i++) {
            if (root.getChildAt(i).getVisibility() != View.VISIBLE)
                root.getChildAt(i).setVisibility(View.VISIBLE);
        }
    }
}
