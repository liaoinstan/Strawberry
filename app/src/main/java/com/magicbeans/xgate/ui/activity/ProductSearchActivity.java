package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;

import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.brand.Brand;
import com.magicbeans.xgate.bean.category.Cate1;
import com.magicbeans.xgate.bean.category.Cate3;
import com.magicbeans.xgate.databinding.ActivityProductBinding;
import com.magicbeans.xgate.databinding.ActivityProductsearchBinding;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.controller.ProductListContentController;
import com.magicbeans.xgate.ui.controller.ProductListSearchController;
import com.magicbeans.xgate.ui.controller.ProductListSortController;

public class ProductSearchActivity extends BaseAppCompatActivity {

    private ActivityProductsearchBinding binding;

    //控制器
    private ProductListSearchController productListSearchController;

    public static void startSearch(Context context, String searchKey) {
        Intent intent = new Intent(context, ProductSearchActivity.class);
        intent.putExtra("searchKey", searchKey);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_productsearch);
        setToolbar(false);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        String searchKey = getIntent().getStringExtra("searchKey");
        //初始化控制器
        productListSearchController = new ProductListSearchController(binding.includeProductlistContent);
        productListSearchController.setSearchFiel(searchKey);
        if (!TextUtils.isEmpty(searchKey)) binding.editSearch.setText(searchKey);
    }

    private void initView() {
    }

    private void initCtrl() {
        binding.editSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    String key = binding.editSearch.getText().toString();
                    if (!TextUtils.isEmpty(key)) {
                        productListSearchController.setSearchFiel(key);
                        productListSearchController.netGetProductList(true);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void initData() {
        productListSearchController.netGetProductList(true);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
                finish();
                break;
            case R.id.btn_right:
                if (productListSearchController.isGridMode()) {
                    productListSearchController.setListMode();
                    binding.btnRight.setSelected(false);
                } else {
                    productListSearchController.setGridMode();
                    binding.btnRight.setSelected(true);
                }
                break;
        }
    }
}
