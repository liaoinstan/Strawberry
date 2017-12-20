package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import com.ins.common.utils.FontUtils;
import com.ins.common.utils.ToastUtil;
import com.ins.common.utils.viewutils.TabLayoutUtil;
import com.ins.common.utils.viewutils.TextViewUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.databinding.LayToobbarHomeBinding;
import com.magicbeans.xgate.databinding.LayToobbarProductdetailBinding;
import com.magicbeans.xgate.ui.activity.ScanActivity;
import com.magicbeans.xgate.ui.activity.SearchActivity;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ToolbarProdDetailController {

    private Context context;
    private LayToobbarProductdetailBinding binding;

    public ToolbarProdDetailController(LayToobbarProductdetailBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
        initCtrl();
        initData();
    }

    public void initCtrl() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_back);
        binding.toolbar.bringToFront();
        binding.toolbar.setTitle("");
        ((AppCompatActivity) context).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) context).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void initData() {
    }

    //#########  对外方法 ############

    public void setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener listener) {
        binding.radiogroupHeader.setOnCheckedChangeListener(listener);
    }

    public int getHeight(){
        return binding.getRoot().getHeight();
    }
}
