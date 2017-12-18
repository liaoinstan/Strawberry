package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.PopBean;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.bean.product.ProductWrap;
import com.magicbeans.xgate.databinding.LayProductlistContentBinding;
import com.magicbeans.xgate.databinding.LayProductlistSortBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.activity.ProductDetailActivity;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterProduct;
import com.magicbeans.xgate.ui.dialog.MyGridPopupWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ProductListContentController implements OnRecycleItemClickListener{

    private Context context;
    private LayProductlistContentBinding binding;

    private RecycleAdapterProduct adapter;
    private RecyclerView.ItemDecoration decorationList;

    private String catgId;
    private String brandID;
    private String typeId;
    private String sort; //producttype, alphabetical, popularity, save, lowerprice
    private int page = 1;

    private MyGridPopupWindow pop_brand;
    private MyGridPopupWindow pop_cate;
    private MyGridPopupWindow pop_skin;

    public ProductListContentController(LayProductlistContentBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
        initCtrl();
        initData();
    }

    public void initCtrl() {
        decorationList = new ItemDecorationDivider(context, ItemDecorationDivider.VERTICAL_LIST);

        adapter = new RecycleAdapterProduct(context);
        adapter.setOnItemClickListener(this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.recycler.setAdapter(adapter);
        binding.recycler.addItemDecoration(decorationList);
        binding.loadingview.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netGetProductList();
            }
        });
        binding.spring.setHeader(new AliHeader(context, false));
        binding.spring.setFooter(new AliFooter(context, false));
        binding.spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.spring.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.spring.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
        binding.loadingview.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    public void initData() {
    }


    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
        ProductDetailActivity.start(context);
    }

    public void setListMode(){
        //设置为列表样式
        binding.recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.recycler.addItemDecoration(decorationList);
        adapter.setGridMode(false);
        adapter.notifyDataSetChanged();
    }

    public void setGridMode(){
        //设置为网格样式
        binding.recycler.setLayoutManager(new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false));
        binding.recycler.removeItemDecoration(decorationList);
        adapter.setGridMode(true);
        adapter.notifyDataSetChanged();
    }

    //当前列表是list状态还是grid状态
    public boolean isGridMode(){
        return binding.recycler.getLayoutManager() instanceof GridLayoutManager;
    }

    public void netGetProductList() {
        Map<String, Object> param = new NetParam()
                .put("catgId", catgId)
                .put("brandId", brandID)
                .put("typeId", typeId)
                .put("page", page)
                .build();
        binding.loadingview.showLoadingView();
        NetApi.NI().netProductList(param).enqueue(new STCallback<ProductWrap>(ProductWrap.class) {
            @Override
            public void onSuccess(int status, ProductWrap bean, String msg) {
                List<Product> products = bean.getProductList();
                if (!StrUtil.isEmpty(products)) {
                    adapter.getResults().clear();
                    adapter.getResults().addAll(products);
                    adapter.notifyDataSetChanged();
                    binding.loadingview.showOut();
                } else {
                    binding.loadingview.showLackView();
                }
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                binding.loadingview.showFailView();
            }
        });
    }

    //############  get & set  ##############

    public String getCatgId() {
        return catgId;
    }

    public void setCatgId(String catgId) {
        this.catgId = catgId;
    }

    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
