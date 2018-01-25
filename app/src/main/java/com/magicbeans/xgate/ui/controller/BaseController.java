package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

/**
 * Created by Administrator on 2018/1/11.
 * Controller 基类 主要封装一些通用方法，和DataBinding连用
 */

public class BaseController<T extends ViewDataBinding> {
    protected Context context;
    protected T binding;

    public BaseController(T binding) {
        this.binding = binding;
        context = binding.getRoot().getContext();
    }

    public View getRoot() {
        return binding.getRoot();
    }

    public void showLoadingDialog() {
        if (context instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) context).showLoadingDialog();
        }
    }

    public void hideLoadingDialog() {
        if (context instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) context).hideLoadingDialog();
        }
    }

    public void dismissLoadingDialog() {
        if (context instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) context).dismissLoadingDialog();
        }
    }
}
