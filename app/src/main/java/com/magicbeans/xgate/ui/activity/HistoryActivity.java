package com.magicbeans.xgate.ui.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
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
import com.magicbeans.xgate.bean.common.TestBean;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.data.db.manager.HistoryTableManager;
import com.magicbeans.xgate.databinding.ActivityFavoBinding;
import com.magicbeans.xgate.databinding.ActivityHistoryBinding;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterFavo;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterHistory;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import java.util.Collections;
import java.util.List;

public class HistoryActivity extends BaseAppCompatActivity implements OnRecycleItemClickListener {

    private ActivityHistoryBinding binding;

    private RecycleAdapterHistory adapter;

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
                initData();
            }
        });
    }

    private void initData() {
        LiveData<List<Product>> productsLiveData = HistoryTableManager.getInstance().queryAll();
        productsLiveData.observeForever(new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                if (!StrUtil.isEmpty(products)) {
                    //将集合倒叙排列，因为最新插入的浏览记录应该显示在第一条
                    Collections.reverse(products);
                    adapter.getResults().clear();
                    adapter.getResults().addAll(products);
                    adapter.notifyDataSetChanged();
                    binding.loadingview.showOut();
                } else {
                    binding.loadingview.showLackView();
                }
            }
        });
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
        Product product = adapter.getResults().get(viewHolder.getLayoutPosition());
        ProductDetailActivity.start(this, product.getProdID());
    }
}
