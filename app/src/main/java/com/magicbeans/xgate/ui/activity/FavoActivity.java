package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.Address;
import com.magicbeans.xgate.bean.TestBean;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterAddress;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterFavo;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

public class FavoActivity extends BaseAppCompatActivity {

    private LoadingLayout loadingLayout;
    private SpringView springView;
    private RecyclerView recycler;
    private RecycleAdapterFavo adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, FavoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favo);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        loadingLayout = (LoadingLayout) findViewById(R.id.loadingview);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        springView = (SpringView) findViewById(R.id.spring);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterFavo(this);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);
        springView.setHeader(new AliHeader(this, false));
        springView.setFooter(new AliFooter(this, false));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        springView.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        springView.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
        loadingLayout.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void initData() {
        adapter.getResults().clear();
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.notifyDataSetChanged();
    }
}