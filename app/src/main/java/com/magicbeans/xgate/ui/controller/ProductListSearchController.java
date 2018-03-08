package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.bean.product.ProductWrap;
import com.magicbeans.xgate.databinding.LayProductlistContentBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.activity.ProductDetailActivity;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterProduct;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.dialog.MyGridPopupWindow;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ProductListSearchController implements OnRecycleItemClickListener {

    private Context context;
    private LayProductlistContentBinding binding;

    private RecycleAdapterProduct adapter;
    private GridLayoutManager layoutManager;

    private String searchFiel;
    private int page = 1;

    public ProductListSearchController(LayProductlistContentBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
        initCtrl();
        initData();
    }

    public void initCtrl() {
        layoutManager = new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false);

        adapter = new RecycleAdapterProduct(context);
        adapter.setOnItemClickListener(this);
        binding.recycler.setLayoutManager(layoutManager);
        binding.recycler.setAdapter(adapter);
        binding.loadingview.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netGetProductList(true);
            }
        });
        binding.spring.setHeader(new AliHeader(context, false));
        binding.spring.setFooter(new AliFooter(context, false));
        binding.spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                netGetProductList(false);
            }

            @Override
            public void onLoadmore() {
                netLoadmore();
            }
        });
        //返回顶部按钮
        binding.fabTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.recycler.smoothScrollToPosition(0);
            }
        });
    }

    public void initData() {
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
        Product product = adapter.getResults().get(position);
        ProductDetailActivity.start(context, product.getProdID());
    }

    public void setListMode() {
        //设置为列表样式
        layoutManager.setSpanCount(1);
        adapter.setGridMode(false);
        adapter.notifyItemRangeChanged(0, adapter.getItemCount());
    }

    public void setGridMode() {
        //设置为网格样式
        layoutManager.setSpanCount(2);
        adapter.setGridMode(true);
        adapter.notifyItemRangeChanged(0, adapter.getItemCount());
    }

    //当前列表是list状态还是grid状态
    public boolean isGridMode() {
        return layoutManager.getSpanCount() == 2;
    }

    public void netGetProductList(final boolean showLoading) {
        page = 1;
        Map<String, Object> param = new NetParam()
                .put("searchFiel", searchFiel)
                .put("page", page)
                .build();
        if (showLoading) ((BaseAppCompatActivity) context).showLoadingDialog();
        NetApi.NI().netProductSearch(param).enqueue(new STCallback<ProductWrap>(ProductWrap.class) {
            @Override
            public void onSuccess(int status, ProductWrap bean, String msg) {
                if (bean == null) onError(0, "服务器异常");
                List<Product> products = bean.getProductList();
                if (!StrUtil.isEmpty(products)) {
                    adapter.getResults().clear();
                    adapter.getResults().addAll(products);
                    adapter.notifyDataSetChanged();
                } else {
                }
                if (showLoading) ((BaseAppCompatActivity) context).dismissLoadingDialog();
                binding.spring.onFinishFreshAndLoad();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                if (showLoading) ((BaseAppCompatActivity) context).dismissLoadingDialog();
                binding.spring.onFinishFreshAndLoad();
            }
        });
    }

    public void netLoadmore() {
        Map<String, Object> param = new NetParam()
                .put("searchFiel", searchFiel)
                .put("page", page + 1)
                .build();
        NetApi.NI().netProductSearch(param).enqueue(new STCallback<ProductWrap>(ProductWrap.class) {
            @Override
            public void onSuccess(int status, ProductWrap bean, String msg) {
                List<Product> products = bean.getProductList();
                if (!StrUtil.isEmpty(products)) {
                    page++;
                    adapter.getResults().addAll(products);
                    adapter.notifyDataSetChanged();
                    binding.spring.onFinishFreshAndLoad();
                } else {
                    binding.spring.onFinishFreshAndLoad();
                }
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                binding.spring.onFinishFreshAndLoad();
            }
        });
    }

    //############  get & set  ##############

    public String getSearchFiel() {
        return searchFiel;
    }

    public void setSearchFiel(String searchFiel) {
        this.searchFiel = searchFiel;
    }
}
