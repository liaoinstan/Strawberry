package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.google.gson.reflect.TypeToken;
import com.ins.common.interfaces.OnRecycleItemClickListenerEx;
import com.ins.common.ui.dialog.DialogSure;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.category.Cate1;
import com.magicbeans.xgate.bean.search.SearchHistory;
import com.magicbeans.xgate.data.cache.DataCache;
import com.magicbeans.xgate.databinding.ActivitySearchBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterLable;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterSearchHistory;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.dialog.SearchPopupWindow;

import java.util.List;
import java.util.Map;

public class SearchActivity extends BaseAppCompatActivity implements SearchPopupWindow.OnSearchKeyClickListener, OnRecycleItemClickListenerEx {

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
        adapterSearchHot.setOnItemClickListener(this);
        binding.recycleHot.setNestedScrollingEnabled(false);
        binding.recycleHot.setLayoutManager(ChipsLayoutManager.newBuilder(this).setOrientation(ChipsLayoutManager.HORIZONTAL).build());
        binding.recycleHot.setAdapter(adapterSearchHot);

        adapterSearchHistory = new RecycleAdapterSearchHistory(this);
        adapterSearchHistory.setOnItemClickListener(this);
        binding.recycleHistory.setNestedScrollingEnabled(false);
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
        binding.editSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    String key = binding.editSearch.getText().toString();
                    if (!TextUtils.isEmpty(key)) {
                        startActivityAndFinish(key);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void initData() {
        adapterSearchHot.queryData();
        adapterSearchHistory.queryData();
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
        startActivityAndFinish(key);
        searchPopupWindow.dismiss();
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int position) {
        if (adapter instanceof RecycleAdapterLable) {
            Cate1 cate1 = adapterSearchHot.getResults().get(viewHolder.getLayoutPosition());
            startActivityAndFinish(cate1);
            finish();
        } else if (adapter instanceof RecycleAdapterSearchHistory) {
            SearchHistory searchHistory = adapterSearchHistory.getResults().get(viewHolder.getLayoutPosition());
            startActivityAndFinish(searchHistory);
        }
    }

    private void startActivityAndFinish(String key) {
        ProductActivity.startSearch(this, key);
        DataCache.getInstance().putSeachHistory(new SearchHistory(key));
        finish();
    }

    private void startActivityAndFinish(Cate1 cate1) {
        ProductActivity.startCategroy(this, cate1);
        DataCache.getInstance().putSeachHistory(new SearchHistory(cate1));
        finish();
    }

    private void startActivityAndFinish(SearchHistory searchHistory) {
        switch (searchHistory.getHistoryType()) {
            case SearchHistory.TYPE_STRING:
                startActivityAndFinish(searchHistory.getSearchKey());
                break;
            case SearchHistory.TYPE_CATE1:
                startActivityAndFinish(searchHistory.getCate1());
                break;
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                binding.editSearch.setText("");
                onBackPressed();
                break;
            case R.id.btn_clear:
                DialogSure.showDialog(this, "确定要清除搜索记录？", new DialogSure.CallBack() {
                    @Override
                    public void onSure() {
                        DataCache.getInstance().removeSeachHistory();
                        adapterSearchHistory.queryData();
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
