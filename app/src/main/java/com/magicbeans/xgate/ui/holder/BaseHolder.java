package com.magicbeans.xgate.ui.holder;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2018/2/22.
 */

public class BaseHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public T binding;
    public BaseHolder(T binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
