package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
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
import com.magicbeans.xgate.databinding.ActivityAddressBinding;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterAddress;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

public class AddressActivity extends BaseAppCompatActivity implements RecycleAdapterAddress.OnAddressBtnClickListener {


    private ActivityAddressBinding binding;
    private RecycleAdapterAddress adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, AddressActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address);
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
        adapter = new RecycleAdapterAddress(this);
        adapter.setOnAddressBtnClickListener(this);
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
            }
        });
    }

    private void initData() {
        adapter.getResults().clear();
        adapter.getResults().add(new Address());
        adapter.getResults().add(new Address());
        adapter.getResults().add(new Address());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                AddressAddActivity.start(this);
                break;
        }
    }

    @Override
    public void onDelClick(RecycleAdapterAddress.Holder holder) {
        DialogSure.showDialog(this, "确认要删除该地址？", new DialogSure.CallBack() {
            @Override
            public void onSure() {
            }
        });
    }

    @Override
    public void onEditClick(RecycleAdapterAddress.Holder holder) {

    }
}
