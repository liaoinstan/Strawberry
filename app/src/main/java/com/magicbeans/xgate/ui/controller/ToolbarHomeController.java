package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ins.common.entity.Image;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.view.BannerView2;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.banner.BannerWrap;
import com.magicbeans.xgate.databinding.LayHomeBannerboardBinding;
import com.magicbeans.xgate.databinding.LayToobbarHomeBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.activity.ScanActivity;
import com.magicbeans.xgate.ui.activity.SearchActivity;
import com.magicbeans.xgate.ui.activity.WebActivity;

import java.util.List;
import java.util.Map;

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
                ToastUtil.showToastLong("建设中...");
                break;
        }
    }
}
