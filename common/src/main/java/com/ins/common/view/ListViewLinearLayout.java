package com.ins.common.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;


/**
 * 在有滚动视图嵌套ListView的情况下，用于替代ListView的仿ListView的LinearLayout
 * 该自定义LinearLayout支持随Adapter数据刷新而刷新
 * 不采用重写onMeasure的ListView的原因是那种ListView性能开销较大
 */
public class ListViewLinearLayout extends LinearLayout {
    private BaseAdapter mAdapter;
    private AdapterView.OnItemClickListener onItemClickListener;
    private boolean checkFastClick;
    private boolean withItemClickListener;
    private OnViewItemClickListener onViewItemClickListener;
    private final static long INTERVAL=300;
    private DataSetObserver dataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            removeAllViews();
            bindView();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            removeAllViews();
            bindView();
        }
    };

    public ListViewLinearLayout(Context context) {
        super(context);
    }

    public ListViewLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 为该控件设置Adapter
     *
     * @param adapter               adapter
     * @param withItemClickListener 该属性指定是否为该控件添加默认的{@link AdapterView.OnItemClickListener}
     *                              若为false则不添加，需注意的是若为true,添加监听器的实际实现是通过为子视图设置{@link OnClickListener}
     *                              因此会导致子视图跟布局的{@link View#setOnClickListener(OnClickListener)}方法不生效
     */
    public void setAdapter(BaseAdapter adapter, boolean withItemClickListener) {
        this.withItemClickListener = withItemClickListener;
        /*清理掉之前的子视图*/
        removeAllViews();
        if (mAdapter != null) {
            /*清理掉持有的adapter之前所注册的数据观察者*/
            mAdapter.unregisterDataSetObserver(dataSetObserver);
        }
        mAdapter = adapter;
        /*注册数据观察者，在数据发生变动时，更新视图*/
        mAdapter.registerDataSetObserver(dataSetObserver);
        bindView();
    }

    public void setAdapter(BaseAdapter adapter) {
        setAdapter(adapter, true);
    }

    /**
     * 为{@link ListViewLinearLayout}设置单项点击监听器
     * <p>
     * 注意，在其监听器中拿到的{@link AdapterView parent}必然为null
     */
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        setOnItemClickListener(onItemClickListener,false);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener,boolean checkFastClick){
        this.onItemClickListener = onItemClickListener;
        this.checkFastClick = checkFastClick;
    }

    /**
     * 绑定 mAdapter 中所有的 view
     */
    private void bindView() {
        if (mAdapter == null) {
            return;
        }
        for (int i = 0; i < mAdapter.getCount(); i++) {
            final View v = mAdapter.getView(i, null, null);
            if (withItemClickListener) {
                setItemClickListenerOfChild(i, v);
            }
            addView(v);
        }
    }

    private void setItemClickListenerOfChild(int i, View v) {
        final int position = i;
        v.setOnClickListener(new OnClickListener() {
            private long lastClickTime;
            @Override
            public void onClick(View v) {
                if (checkFastClick){
                    if (isFastClick()) return;
                }

                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(null, v, position, mAdapter.getItemId(position));
                }
                if (onViewItemClickListener != null) {
                    onViewItemClickListener.onItemClick(ListViewLinearLayout.this, v, position, mAdapter.getItemId(position));
                }
            }

            private boolean isFastClick() {
                if (System.currentTimeMillis()-lastClickTime<INTERVAL){
                    return true;
                }
                lastClickTime=System.currentTimeMillis();
                return false;
            }
        });
    }

    @SuppressWarnings("unused")
    public void setOnViewItemClickListener(OnViewItemClickListener onViewItemClickListener) {
        this.onViewItemClickListener = onViewItemClickListener;
    }

    @SuppressWarnings("WeakerAccess")
    public interface OnViewItemClickListener {
        void onItemClick(ViewGroup parent, View view, int position, long id);
    }

}