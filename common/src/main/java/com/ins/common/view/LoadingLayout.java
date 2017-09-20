package com.ins.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.ins.common.R;
import com.ins.common.helper.LoadingViewHelper;
import com.ins.common.utils.L;

/**
 * Created by liaoinstan on 2017/7/19.
 * 加载视图布局，提供统一的方法管理加载中，加载失败，空数据视图
 * 该View只提供加载的展示过程（不处理项目业务逻辑），业务逻辑由自己外部处理
 * 要进行视图切换需要提前设置几种视图（目前：加载中，加载失败，空数据视图）
 * 设置可以使用set方法设置 {@link #setLoadingViewSrc(int)}...也可以在布局文件中用属性设置 app:layout_loading="@layout/layout_loading"...
 * 这些视图不会在设置的时候加载，只会在需要展示时动态进行加载以减少性能消耗
 *
 * 如果要设置点击事件，使用{@link #setOnRefreshListener(OnClickListener)}
 */

public class LoadingLayout extends FrameLayout {

    private static final String TAG = "LoadingLayout";

    private Context context;
    private View showin;

    private int loadingViewSrc;
    private int lackViewSrc;
    private int failViewSrc;

    private OnClickListener onRefreshListener;

    public LoadingLayout(Context context) {
        this(context, null);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingLayout);
        loadingViewSrc = typedArray.getResourceId(R.styleable.LoadingLayout_layout_loading, 0);
        lackViewSrc = typedArray.getResourceId(R.styleable.LoadingLayout_layout_lack, 0);
        failViewSrc = typedArray.getResourceId(R.styleable.LoadingLayout_layout_fail, 0);
        typedArray.recycle();
    }

    ////////////////  get & set /////////////////


    public void setLoadingViewSrc(int loadingViewSrc) {
        this.loadingViewSrc = loadingViewSrc;
    }

    public void setLackViewSrc(int lackViewSrc) {
        this.lackViewSrc = lackViewSrc;
    }

    public void setFailViewSrc(int failViewSrc) {
        this.failViewSrc = failViewSrc;
    }

    public void setOnRefreshListener(OnClickListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    /////////////////////////////////

    public void showLoadingView() {
        if (loadingViewSrc != 0) {
            showin = LoadingViewHelper.showin(this, loadingViewSrc, showin);
        } else {
            L.e(TAG, "没有设置loadingViewSrc");
        }
    }

    public void showFailView() {
        if (failViewSrc != 0) {
            showin = LoadingViewHelper.showin(this, failViewSrc, showin, onRefreshListener);
        } else {
            L.e(TAG, "没有设置failViewSrc");
        }
    }

    public void showLackView() {
        if (failViewSrc != 0) {
            showin = LoadingViewHelper.showin(this, lackViewSrc, showin, onRefreshListener);
        } else {
            L.e(TAG, "没有设置lackViewSrc");
        }
    }

    public void showOut() {
        LoadingViewHelper.showout(this, showin);
    }
}
