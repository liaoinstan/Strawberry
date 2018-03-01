package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.address.Address;
import com.magicbeans.xgate.databinding.ActivityAddressBinding;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterAddress;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

public class AddressActivity extends BaseAppCompatActivity implements OnRecycleItemClickListener {


    private ActivityAddressBinding binding;
    private RecycleAdapterAddress adapter;
    private boolean forResult;

    public static void start(Context context) {
        start(context, false);
    }

    public static void startForResult(Context context) {
        start(context, true);
    }

    private static void start(Context context, boolean forResult) {
        Intent intent = new Intent(context, AddressActivity.class);
        intent.putExtra("forResult", forResult);
        context.startActivity(intent);
    }

    @Override
    public void onCommonEvent(EventBean event) {
        switch (event.getEvent()) {
            case EventBean.EVENT_REFRESH_ADDRESSLIST:
                adapter.netGetAddressList(true, true);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address);
        setToolbar();
        registEventBus();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        forResult = getIntent().getBooleanExtra("forResult", false);
    }

    private void initView() {
    }

    private void initCtrl() {
        adapter = new RecycleAdapterAddress(this);
        adapter.setLoadingLayout(binding.loadingview);
        adapter.setSpringView(binding.spring);
        if (forResult) adapter.setOnItemClickListener(this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recycler.setAdapter(adapter);
        binding.spring.setHeader(new AliHeader(this, false));
        binding.spring.setFooter(new AliFooter(this, false));
        binding.spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                adapter.netGetAddressList(true, false);
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
                adapter.netGetAddressList(true, true);
            }
        });
    }

    private void initData() {
        adapter.netGetAddressList(true, true);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                AddressAddActivity.start(this);
                break;
        }
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
        Address address = adapter.getResults().get(viewHolder.getLayoutPosition());
        EventBean eventBean = new EventBean(EventBean.EVENT_GET_ADDRESS);
        eventBean.put("address", address);
        EventBus.getDefault().post(eventBean);
        finish();
    }
}
