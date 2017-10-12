package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.view.bundleimgview.BundleImgEntity;
import com.magicbeans.xgate.bean.Eva;
import com.magicbeans.xgate.bean.common.KeyValue;
import com.magicbeans.xgate.databinding.LayProductdetailAttrBinding;
import com.magicbeans.xgate.databinding.LayProductdetailEvaBinding;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterEva;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterProductAttr;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ProductDetailEvaController {

    private Context context;
    private LayProductdetailEvaBinding binding;
    private RecycleAdapterEva adapter;

    public ProductDetailEvaController(LayProductdetailEvaBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
    }

    public void initCtrl() {
        adapter = new RecycleAdapterEva(context);
        adapter.setNeedRecomment(false);
        binding.recycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.recycle.addItemDecoration(new ItemDecorationDivider(context));
        binding.recycle.setNestedScrollingEnabled(false);
        binding.recycle.setAdapter(adapter);
    }

    public void initData() {
//        netGetSale();
        adapter.getResults().clear();
        adapter.getResults().add(new Eva(new ArrayList<BundleImgEntity>() {{
            add(new BundleImgEntity());
            add(new BundleImgEntity());
        }}));
        adapter.getResults().add(new Eva(new ArrayList<BundleImgEntity>() {{
            add(new BundleImgEntity());
            add(new BundleImgEntity());
            add(new BundleImgEntity());
        }}));
        adapter.notifyDataSetChanged();
    }

//    private void netGetSale() {
//        Map<String, Object> param = new NetParam()
//                .build();
//        NetApi.NI().netHomeSaleList(param).enqueue(new STCallback<ProductWrap>(ProductWrap.class) {
//            @Override
//            public void onSuccess(int status, ProductWrap bean, String msg) {
//                adapter.getResults().clear();
//                adapter.getResults().addAll(bean.getProductList());
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onError(int status, String msg) {
//                ToastUtil.showToastShort(msg);
//            }
//        });
//    }
}
