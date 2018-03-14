package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.view.View;

import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.databinding.LayToobbarHomeBinding;
import com.magicbeans.xgate.ui.activity.ScanActivity;
import com.magicbeans.xgate.ui.activity.SearchActivity;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ToolbarHomeController implements View.OnClickListener{

    private Context context;
    private LayToobbarHomeBinding binding;

    public ToolbarHomeController(LayToobbarHomeBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
        initCtrl();
        initData();
    }

    public void initCtrl() {
        binding.btnLeft.setOnClickListener(this);
        binding.btnRight.setOnClickListener(this);
        binding.textSearch.setOnClickListener(this);
    }

    public void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_search:
                SearchActivity.start(context);
                break;
            case R.id.btn_left:
                ScanActivity.start(context);
                break;
            case R.id.btn_right:
                ToastUtil.showToastShort("建设中,敬请期待...");
                break;
        }
    }
}
