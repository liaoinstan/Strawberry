package com.magicbeans.xgate.ui.controller;

import android.support.v7.widget.GridLayoutManager;

import com.ins.common.common.GridSpacingItemDecoration;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.ListUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.bean.product.ProductWrap;
import com.magicbeans.xgate.databinding.LayRecommendBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterRecomment;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/11.
 */

public class CommonRecommendController extends BaseController<LayRecommendBinding> {

    private RecycleAdapterRecomment adapter;
    private int maxCount;

    public CommonRecommendController(LayRecommendBinding binding) {
        this(binding, 0);
    }

    public CommonRecommendController(LayRecommendBinding binding, int maxCount) {
        super(binding);
        this.maxCount = maxCount;
        initCtrl();
        initData();
    }

    private void initCtrl() {
        adapter = new RecycleAdapterRecomment(context);
        binding.recycle.setNestedScrollingEnabled(false);
        binding.recycle.setLayoutManager(new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false));
        binding.recycle.addItemDecoration(new GridSpacingItemDecoration(2, DensityUtil.dp2px(4), GridLayoutManager.VERTICAL, false));
        binding.recycle.setAdapter(adapter);
    }

    private void initData() {
        netRecommendList();
    }

    public void netRecommendList() {
        Map<String, Object> param = new NetParam()
                .build();
        NetApi.NI().netRecommendList(param).enqueue(new STCallback<ProductWrap>(ProductWrap.class) {
            @Override
            public void onSuccess(int status, ProductWrap bean, String msg) {
                adapter.getResults().clear();
                if (maxCount != 0) {
                    adapter.getResults().addAll(ListUtil.getFirst(bean.getProductList(), maxCount));
                } else {
                    adapter.getResults().addAll(bean.getProductList());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
