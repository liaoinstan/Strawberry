package com.magicbeans.xgate.ui.controller;

import android.content.Context;

import com.magicbeans.xgate.databinding.LayHomeClassBinding;
import com.magicbeans.xgate.databinding.LayHomeHotBinding;

/**
 * Created by Administrator on 2017/10/11.
 */

public class HomeClassController {

    private Context context;
    private LayHomeClassBinding binding;

    public HomeClassController(LayHomeClassBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
    }

    public void initCtrl() {
    }

    public void initData() {
    }
}
