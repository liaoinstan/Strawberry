package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.ins.common.common.ItemDecorationDivider;
import com.magicbeans.xgate.bean.common.KeyValue;
import com.magicbeans.xgate.databinding.LayProductdetailAttrBinding;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterProductAttr;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ProductDetailAttrController {

    private Context context;
    private LayProductdetailAttrBinding binding;
    private RecycleAdapterProductAttr adapter;

    public ProductDetailAttrController(LayProductdetailAttrBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
    }

    public void initCtrl() {
        adapter = new RecycleAdapterProductAttr(context);
        binding.recycle.setNestedScrollingEnabled(false);
        binding.recycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.recycle.addItemDecoration(new ItemDecorationDivider(context));
        binding.recycle.setAdapter(adapter);
    }

    public void initData() {
//        netGetSale();
        adapter.getResults().clear();
        adapter.getResults().add(new KeyValue("功效", "抗氧化 保湿 净化排毒 舒缓 清爽"));
        adapter.getResults().add(new KeyValue("适合肤质", "干性肌肤 混合肤质"));
        adapter.getResults().add(new KeyValue("品牌", "Aesop/伊索"));
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
