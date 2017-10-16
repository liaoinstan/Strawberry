package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.ins.common.common.GridSpacingItemDecoration;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.FocusUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.databinding.ActivityPayresultBinding;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterRecomment;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

public class PayResultActivity extends BaseAppCompatActivity {

    private ActivityPayresultBinding binding;
    private RecycleAdapterRecomment adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, PayResultActivity.class);
        context.startActivity(intent);
//        if (AppData.App.getUser() != null) {
//            Intent intent = new Intent(context, SuggestActivity.class);
//            context.startActivity(intent);
//        } else {
//            LoginActivity.start(context);
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payresult);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
        FocusUtil.focusToTop(toolbar);
    }

    private void initBase() {
    }

    private void initView() {
    }

    private void initCtrl() {
        adapter = new RecycleAdapterRecomment(this);
        binding.recycleRecomment.setNestedScrollingEnabled(false);
        binding.recycleRecomment.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        binding.recycleRecomment.addItemDecoration(new GridSpacingItemDecoration(2, DensityUtil.dp2px(4), GridLayoutManager.VERTICAL, false));
        binding.recycleRecomment.setAdapter(adapter);
    }

    private void initData() {
        adapter.netGetRecommend();
    }
}
