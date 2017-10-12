package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ins.common.common.GridSpacingItemDecoration;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.DensityUtil;
import com.magicbeans.xgate.databinding.LayHomeRecommendBinding;
import com.magicbeans.xgate.ui.activity.ProductDetailActivity;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterRecomment;

/**
 * Created by Administrator on 2017/10/11.
 */

public class HomeRecommendController {

    private Context context;
    private LayHomeRecommendBinding binding;
    private RecycleAdapterRecomment adapter;

    public HomeRecommendController(LayHomeRecommendBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
    }

    public void initCtrl() {
        adapter = new RecycleAdapterRecomment(context);
        binding.recycle.setNestedScrollingEnabled(false);
        binding.recycle.setLayoutManager(new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false));
        binding.recycle.addItemDecoration(new GridSpacingItemDecoration(2, DensityUtil.dp2px(4), GridLayoutManager.VERTICAL, false));
        binding.recycle.setAdapter(adapter);

        binding.tab.addTab(binding.tab.newTab().setText("热门精选"));
        binding.tab.addTab(binding.tab.newTab().setText("基础护肤"));
        binding.tab.addTab(binding.tab.newTab().setText("时尚彩妆"));
        binding.tab.addTab(binding.tab.newTab().setText("品牌香水"));
    }

    public void initData() {
        adapter.netGetRecommend();
    }
}
