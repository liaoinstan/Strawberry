package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.Order;
import com.magicbeans.xgate.ui.adapter.PagerAdapterOrder;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

public class OrderActivity extends BaseAppCompatActivity {

    private TabLayout tab;
    private ViewPager pager;
    private PagerAdapterOrder adapterPager;

    private int position;
    private String[] titles = new String[]{"全部", "待付款", "待发货", "待收货", "待评价"};

    public static void start(Context context, int status) {
//        if (AppData.App.getUser() != null) {
//            Intent intent = new Intent(context, OrderActivity.class);
//            context.startActivity(intent);
//        } else {
//            LoginActivity.start(context);
//        }
        int position = 0;
        switch (status) {
            case Order.STATUS_ALL:
                position = 0;
                break;
            case Order.STATUS_UNPAY:
                position = 1;
                break;
            case Order.STATUS_UNOUT:
                position = 2;
                break;
            case Order.STATUS_UNIN:
                position = 3;
                break;
            case Order.STATUS_UNEVA:
                position = 4;
                break;
        }
        Intent intent = new Intent(context, OrderActivity.class);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        if (getIntent().hasExtra("position")){
            position = getIntent().getIntExtra("position",0);
        }
    }

    private void initView() {
        tab = (TabLayout) findViewById(R.id.tab);
        pager = (ViewPager) findViewById(R.id.pager);
    }

    private void initCtrl() {
        adapterPager = new PagerAdapterOrder(getSupportFragmentManager(), titles);
        pager.setAdapter(adapterPager);
        tab.setupWithViewPager(pager);

        pager.setCurrentItem(position, false);
    }

    private void initData() {
    }
}
