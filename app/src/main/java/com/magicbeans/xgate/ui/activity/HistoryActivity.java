package com.magicbeans.xgate.ui.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.StrUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.data.db.manager.HistoryTableManager;
import com.magicbeans.xgate.databinding.ActivityHistoryBinding;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterHistory;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import java.util.List;

public class HistoryActivity extends BaseAppCompatActivity implements OnRecycleItemClickListener {

    private ActivityHistoryBinding binding;
    private RecycleAdapterHistory adapter;

    //分页查询参数
    private int page = 1;
    private int pageCount = 30;

    public static void start(Context context) {
        Intent intent = new Intent(context, HistoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history);
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
        adapter = new RecycleAdapterHistory(this);
        adapter.setOnItemClickListener(this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recycler.setAdapter(adapter);
        binding.spring.setHeader(new AliHeader(this, false));
        binding.spring.setFooter(new AliFooter(this, false));
        binding.spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                querryData(true);
            }

            @Override
            public void onLoadmore() {
                querryData(false);
            }
        });
        binding.loadingview.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }

    private void initData() {
        querryData(true);
    }

    private void querryData(final boolean isReFresh) {
        //下拉刷新回到第一页，上拉加载不断叠加页码
        page = isReFresh ? 1 : page + 1;
        LiveData<List<Product>> productsLiveData = HistoryTableManager.getInstance().querryLimit(page, pageCount);
        productsLiveData.observeForever(new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                if (!StrUtil.isEmpty(products)) {
                    if (isReFresh) {
                        adapter.getResults().clear();
                        adapter.getResults().addAll(products);
                    } else {
                        adapter.getResults().addAll(products);
                    }
                    adapter.notifyDataSetChanged();
                    if (isReFresh) binding.loadingview.showOut();
                } else {
                    if (isReFresh) binding.loadingview.showLackView();
                }
                binding.spring.onFinishFreshAndLoad();
            }
        });
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
        Product product = adapter.getResults().get(viewHolder.getLayoutPosition());
        ProductDetailActivity.start(this, product.getProdID());
    }
}
