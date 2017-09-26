package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.Order;
import com.magicbeans.xgate.ui.adapter.PagerAdapterEva;
import com.magicbeans.xgate.ui.adapter.PagerAdapterOrder;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

public class EvaActivity extends BaseAppCompatActivity {

    private TabLayout tab;
    private ViewPager pager;
    private PagerAdapterEva adapterPager;

    private String[] titles = new String[]{"全部", "有图"};

    public static void start(Context context) {
//        if (AppData.App.getUser() != null) {
//            Intent intent = new Intent(context, OrderActivity.class);
//            context.startActivity(intent);
//        } else {
//            LoginActivity.start(context);
//        }
        Intent intent = new Intent(context, EvaActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eva);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        tab = (TabLayout) findViewById(R.id.tab);
        pager = (ViewPager) findViewById(R.id.pager);
    }

    private void initCtrl() {
        adapterPager = new PagerAdapterEva(getSupportFragmentManager(), titles);
        pager.setAdapter(adapterPager);
        tab.setupWithViewPager(pager);
    }

    private void initData() {
    }
}
