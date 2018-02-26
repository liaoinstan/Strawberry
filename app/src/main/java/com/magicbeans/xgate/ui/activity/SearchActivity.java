package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.ins.common.common.GridSpacingItemDecoration;
import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.DensityUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.address.AddressWrap;
import com.magicbeans.xgate.bean.common.TestBean;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.databinding.ActivitySearchBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.net.nethelper.NetAddressHelper;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterLable;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterSearchHistory;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.dialog.SearchPopupWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchActivity extends BaseAppCompatActivity implements SearchPopupWindow.OnSearchKeyClickListener {

    private ActivitySearchBinding binding;
    private RecycleAdapterLable adapterSearchHot;
    private RecycleAdapterSearchHistory adapterSearchHistory;

    private SearchPopupWindow searchPopupWindow;

    public static void start(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
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
        searchPopupWindow = new SearchPopupWindow(this);
        searchPopupWindow.setOnSearchKeyClickListener(this);
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

        binding.editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initData() {
        adapterSearchHot.queryData();

        adapterSearchHistory.getResults().clear();
        adapterSearchHistory.getResults().add(new TestBean("面膜"));
        adapterSearchHistory.getResults().add(new TestBean("口红"));
        adapterSearchHistory.getResults().add(new TestBean("洗面奶"));
        adapterSearchHistory.getResults().add(new TestBean("化妆品"));
        adapterSearchHistory.getResults().add(new TestBean("粉底"));
        adapterSearchHistory.notifyDataSetChanged();
    }

    private void search(String searchText) {
        if (TextUtils.isEmpty(searchText)) {
            searchPopupWindow.dismiss();
        } else {
            netAutoComplete(searchText);
        }
    }

    @Override
    public void onKeyClick(String key, int position) {
        ProductActivity.startSearch(this, key);
        searchPopupWindow.dismiss();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                onBackPressed();
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

    @Override
    public void onBackPressed() {
        if (searchPopupWindow.isShowing()) {
            searchPopupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    //自动填充接口
    public void netAutoComplete(String searchText) {
        Map<String, Object> param = new NetParam()
                .put("term", searchText)
                .build();
        NetApi.NI().netAutoComplete(param).enqueue(new STCallback<List<String>>(new TypeToken<List<String>>() {
        }.getType()) {
            @Override
            public void onSuccess(int status, List<String> texts, String msg) {
                //只有在搜索框中有文字时才显示搜索结果
                if (!TextUtils.isEmpty(binding.editSearch.getText())) {
                    searchPopupWindow.setResults(texts);
                    searchPopupWindow.showPopupWindow(toolbar);
                }
            }

            @Override
            public void onError(int status, String msg) {
            }
        });
    }


}
