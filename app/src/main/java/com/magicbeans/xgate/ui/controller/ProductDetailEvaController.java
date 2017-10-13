package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.view.bundleimgview.BundleImgEntity;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.eva.Eva;
import com.magicbeans.xgate.bean.eva.EvaWrap;
import com.magicbeans.xgate.databinding.LayProductdetailAttrBinding;
import com.magicbeans.xgate.databinding.LayProductdetailEvaBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.activity.EvaListActivity;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterEva;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterProductAttr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ProductDetailEvaController implements View.OnClickListener {

    private Context context;
    private LayProductdetailEvaBinding binding;
    private RecycleAdapterEva adapter;
    private String prodId;

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
        binding.btnMore.setOnClickListener(this);
    }

    public void initData(String prodId) {
        this.prodId = prodId;
        netProductReview(prodId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_more:
                EvaListActivity.start(context, prodId);
                break;
        }
    }

    private void netProductReview(String prodId) {
        Map<String, Object> param = new NetParam()
                .put("prodId", prodId)
                .put("page", 1)
                .build();
        NetApi.NI().netProductReview(param).enqueue(new STCallback<EvaWrap>(EvaWrap.class) {
            @Override
            public void onSuccess(int status, EvaWrap bean, String msg) {
                List<Eva> evas = bean.getProducts();
                if (!StrUtil.isEmpty(evas)) {
                    adapter.getResults().clear();
                    adapter.getResults().addAll(evas);
                    adapter.notifyDataSetChanged();

                    binding.btnMore.setText("查看全部评价" + bean.getTotal() + "个");
                }
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
