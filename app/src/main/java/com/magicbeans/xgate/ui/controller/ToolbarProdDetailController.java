package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import com.magicbeans.xgate.R;
import com.magicbeans.xgate.databinding.LayToobbarProductdetailBinding;
import com.magicbeans.xgate.sharesdk.ShareDialog;

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
        binding.btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ShareDialog(context).show();
            }
        });
    }

    public void initData() {
    }

    //#########  对外方法 ############

    public void setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener listener) {
        binding.radiogroupHeader.setOnCheckedChangeListener(listener);
    }

    public int getHeight() {
        return binding.getRoot().getHeight();
    }

    //根据滚动位置反向设置tab的切换
    public void setTabByScrollHeight(int hightLimit, int scrollY, int oldScrollY) {
        //根据滚动位置反向设置tab切换
        if (scrollY > hightLimit && oldScrollY < hightLimit) {
            binding.radiogroupHeader.check(R.id.radio_recommend);
        } else if (scrollY < hightLimit && oldScrollY > hightLimit) {
            binding.radiogroupHeader.check(R.id.radio_product);
        }
    }
}
