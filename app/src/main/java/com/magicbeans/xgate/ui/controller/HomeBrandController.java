package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.view.View;

import com.magicbeans.xgate.R;
import com.magicbeans.xgate.databinding.LayHomeBrandBinding;
import com.magicbeans.xgate.ui.activity.SectionActivity;

/**
 * Created by Administrator on 2017/10/11.
 */

public class HomeBrandController implements View.OnClickListener{

    private Context context;
    private LayHomeBrandBinding binding;

    public HomeBrandController(LayHomeBrandBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
    }

    public void initCtrl() {
        binding.btnMore.setOnClickListener(this);
    }

    public void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_more:
                SectionActivity.start(context);
                break;
        }
    }
}
