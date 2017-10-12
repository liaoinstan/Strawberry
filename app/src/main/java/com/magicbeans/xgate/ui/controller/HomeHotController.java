package com.magicbeans.xgate.ui.controller;

import android.content.Context;

import com.magicbeans.xgate.databinding.LayHomeBrandBinding;
import com.magicbeans.xgate.databinding.LayHomeHotBinding;

/**
 * Created by Administrator on 2017/10/11.
 */

public class HomeHotController {

    private Context context;
    private LayHomeHotBinding binding;

    public HomeHotController(LayHomeHotBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
    }

    public void initCtrl() {
    }

    public void initData() {
    }
}
