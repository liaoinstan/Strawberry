package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.bean.product.ProductWrap;
import com.magicbeans.xgate.databinding.ActivitySaleBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterSale;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import java.util.List;
import java.util.Map;

public class SaleActivity extends BaseAppCompatActivity implements OnRecycleItemClickListener{

    private ActivitySaleBinding binding;
    private RecycleAdapterSale adapter;

    private int page = 1;

    //测试启动
    public static void start(Context context) {
        Intent intent = new Intent(context, SaleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sale);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
    }

    private void initCtrl() {
        adapter = new RecycleAdapterSale(this);
        adapter.setOnItemClickListener(this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recycler.setAdapter(adapter);
        binding.loadingview.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netGetProductList(true);
            }
        });
        binding.spring.setHeader(new AliHeader(this, false));
        binding.spring.setFooter(new AliFooter(this, false));
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
    }

    private void initData() {
        netGetProductList(true);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
        ProductDetailActivity.start(this);
    }

    public void netGetProductList(final boolean showLoading) {
        page = 1;
        Map<String, Object> param = new NetParam()
                .put("page", page)
                .build();
        if (showLoading) showLoadingDialog();
        NetApi.NI().netHomeRecommendList(param).enqueue(new STCallback<ProductWrap>(ProductWrap.class) {
            @Override
            public void onSuccess(int status, ProductWrap bean, String msg) {
                List<Product> products = bean.getProductList();
                if (!StrUtil.isEmpty(products)) {
                    adapter.getResults().clear();
                    adapter.getResults().addAll(products);
                    adapter.notifyDataSetChanged();
                } else {
                }
                if (showLoading) dismissLoadingDialog();
                binding.spring.onFinishFreshAndLoad();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                if (showLoading) dismissLoadingDialog();
                binding.spring.onFinishFreshAndLoad();
            }
        });
    }

    public void netLoadmore() {
        Map<String, Object> param = new NetParam()
                .put("page", page + 1)
                .build();
        NetApi.NI().netHomeRecommendList(param).enqueue(new STCallback<ProductWrap>(ProductWrap.class) {
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
}
