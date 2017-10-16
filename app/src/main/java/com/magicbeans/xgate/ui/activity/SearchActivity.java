package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ins.common.common.GridSpacingItemDecoration;
import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.DensityUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.common.TestBean;
import com.magicbeans.xgate.databinding.ActivitySearchBinding;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterLable;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterSearchHistory;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

public class SearchActivity extends BaseAppCompatActivity {

    private ActivitySearchBinding binding;
    private RecycleAdapterLable adapterSearchHot;
    private RecycleAdapterSearchHistory adapterSearchHistory;

    public static void start(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
//        if (AppData.App.getUser() != null) {
//            Intent intent = new Intent(context, SuggestActivity.class);
//            context.startActivity(intent);
//        } else {
//            LoginActivity.start(context);
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        setToolbar(false);
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
        adapterSearchHot = new RecycleAdapterLable(this);
        binding.recycleHot.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recycleHot.addItemDecoration(new GridSpacingItemDecoration(1, DensityUtil.dp2px(8), GridLayoutManager.HORIZONTAL, false));
        binding.recycleHot.setAdapter(adapterSearchHot);

        adapterSearchHistory = new RecycleAdapterSearchHistory(this);
        binding.recycleHistory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recycleHistory.setAdapter(adapterSearchHistory);
    }

    private void initData() {
        adapterSearchHot.getResults().clear();
        adapterSearchHot.getResults().add(new TestBean("面膜"));
        adapterSearchHot.getResults().add(new TestBean("口红"));
        adapterSearchHot.getResults().add(new TestBean("洗面奶"));
        adapterSearchHot.getResults().add(new TestBean("化妆品"));
        adapterSearchHot.getResults().add(new TestBean("粉底"));
        adapterSearchHot.notifyDataSetChanged();

        adapterSearchHistory.getResults().clear();
        adapterSearchHistory.getResults().add(new TestBean("面膜"));
        adapterSearchHistory.getResults().add(new TestBean("口红"));
        adapterSearchHistory.getResults().add(new TestBean("洗面奶"));
        adapterSearchHistory.getResults().add(new TestBean("化妆品"));
        adapterSearchHistory.getResults().add(new TestBean("粉底"));
        adapterSearchHistory.notifyDataSetChanged();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                finish();
                break;
            case R.id.btn_clear:
                DialogSure.showDialog(this, "确定要清除搜索记录？", new DialogSure.CallBack() {
                    @Override
                    public void onSure() {
                    }
                });
                break;
        }
    }
}
