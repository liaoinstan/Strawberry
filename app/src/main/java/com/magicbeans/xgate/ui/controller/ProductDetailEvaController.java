package com.magicbeans.xgate.ui.controller;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.utils.ListUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.eva.Eva;
import com.magicbeans.xgate.bean.eva.EvaWrap;
import com.magicbeans.xgate.databinding.LayProductdetailEvaBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.activity.EvaListActivity;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterEva;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ProductDetailEvaController extends BaseController<LayProductdetailEvaBinding> implements View.OnClickListener {

    private RecycleAdapterEva adapter;
    private String prodId;

    public ProductDetailEvaController(LayProductdetailEvaBinding binding, String prodId) {
        super(binding);
        this.prodId = prodId;
        initCtrl();
        initData();
    }

    private void initCtrl() {
        adapter = new RecycleAdapterEva(context);
        binding.recycle.setNestedScrollingEnabled(false);
        binding.recycle.addItemDecoration(new ItemDecorationDivider(context));
        binding.recycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.recycle.setAdapter(adapter);
        adapter.setNeedRecomment(false);
        binding.btnMore.setOnClickListener(this);
    }

    private void initData() {
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
                    adapter.getResults().addAll(ListUtil.getFirst(evas, 5));
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
