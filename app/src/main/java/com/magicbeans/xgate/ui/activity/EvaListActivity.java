package com.magicbeans.xgate.ui.activity;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.eva.Eva;
import com.magicbeans.xgate.bean.eva.EvaWrap;
import com.magicbeans.xgate.data.DataRepository;
import com.magicbeans.xgate.databinding.ActivityEvalistBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterEva;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.viewmodel.EvaListViewModel;

import java.util.List;
import java.util.Map;

public class EvaListActivity extends BaseAppCompatActivity {

    private ActivityEvalistBinding binding;
    private RecycleAdapterEva adapter;
    private EvaListViewModel viewModel;

    private String prodId;

    public static void start(Context context, String prodId) {
        Intent intent = new Intent(context, EvaListActivity.class);
        intent.putExtra("prodId", prodId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_evalist);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        prodId = getIntent().getStringExtra("prodId");
        viewModel = new EvaListViewModel(prodId);
        viewModel.resetResults();//开始加载数据
    }

    private void initView() {
    }

    private void initCtrl() {
        adapter = new RecycleAdapterEva(this);
        adapter.setNeedRecomment(false);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recycler.addItemDecoration(new ItemDecorationDivider(this));
        binding.recycler.setAdapter(adapter);
        binding.loadingview.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.resetResults();
            }
        });
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
        viewModel.getResults().observe(this, new Observer<List<Eva>>() {
            @Override
            public void onChanged(@Nullable List<Eva> evas) {
                adapter.getResults().clear();
                adapter.getResults().addAll(evas);
                adapter.notifyDataSetChanged();
            }
        });
        viewModel.loadingViewStatus.observe(this, new Observer<EvaListViewModel.LoadingStatus>() {
            @Override
            public void onChanged(@Nullable EvaListViewModel.LoadingStatus status) {
                switch (status) {
                    case NONE:
                        binding.loadingview.showOut();
                        break;
                    case LOADING:
                        binding.loadingview.showLoadingView();
                        break;
                    case EMPTY:
                        binding.loadingview.showLackView();
                        break;
                    case ERROR:
                        binding.loadingview.showFailView();
                        break;
                }
            }
        });
    }

    private void initData() {
    }
}