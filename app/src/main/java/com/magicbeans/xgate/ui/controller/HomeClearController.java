package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ins.common.common.GridSpacingItemDecoration;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.bean.product.ProductWrap;
import com.magicbeans.xgate.databinding.LayHomeClearBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.activity.ProductDetailActivity;
import com.magicbeans.xgate.ui.activity.SaleActivity;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterHomeSingle;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/11.
 */

public class HomeClearController implements View.OnClickListener {

    private Context context;
    private LayHomeClearBinding binding;
    private RecycleAdapterHomeSingle adapter;

    public HomeClearController(LayHomeClearBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
        initCtrl();
        initData();
    }

    public void initCtrl() {
        adapter = new RecycleAdapterHomeSingle(context);
        adapter.setOnItemClickListener(onRecycleItemClickListener);
        binding.recycle.setNestedScrollingEnabled(false);
        binding.recycle.setLayoutManager(new GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false));
        binding.recycle.addItemDecoration(new GridSpacingItemDecoration(2, DensityUtil.dp2px(7), GridLayoutManager.HORIZONTAL, true));
        binding.recycle.setAdapter(adapter);

        binding.btnMore.setOnClickListener(this);
    }

    public void initData() {
        netGetClear();
    }

    private OnRecycleItemClickListener onRecycleItemClickListener = new OnRecycleItemClickListener() {
        @Override
        public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
            Product product = adapter.getResults().get(position);
            ProductDetailActivity.start(context, product.getProdID());
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_more:
                SaleActivity.start(context, SaleActivity.TYPE_CLEAR);
                break;
        }
    }

    private void netGetClear() {
        Map<String, Object> param = new NetParam()
                .build();
        NetApi.NI().netHomeClearList(param).enqueue(new STCallback<ProductWrap>(ProductWrap.class) {
            @Override
            public void onSuccess(int status, ProductWrap bean, String msg) {
                adapter.getResults().clear();
                adapter.getResults().addAll(bean.getProductList());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
