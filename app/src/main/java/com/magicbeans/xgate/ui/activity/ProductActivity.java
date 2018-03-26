package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.brand.Brand;
import com.magicbeans.xgate.bean.category.Cate1;
import com.magicbeans.xgate.bean.category.Cate3;
import com.magicbeans.xgate.databinding.ActivityProductBinding;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.controller.ProductListContentController;
import com.magicbeans.xgate.ui.controller.ProductListSortController;

public class ProductActivity extends BaseAppCompatActivity {

    private ActivityProductBinding binding;

    //控制器
    private ProductListSortController productListSortController;
    private ProductListContentController productListContentController;

    //测试启动
    public static void start(Context context) {
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra("brandID", "288");
        context.startActivity(intent);
    }

    public static void startSearch(Context context, String searchKey) {
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra("brandID", "288");
        intent.putExtra("searchKey", searchKey);
        context.startActivity(intent);
    }

    public static void startCategroy(Context context, Cate1 cate1) {
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra("catgId", cate1.getCatgId());
        context.startActivity(intent);
    }

    public static void startBrand(Context context, Brand brand) {
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra("brandID", brand.getBrandID());
        context.startActivity(intent);
    }

    public static void startType(Context context, Cate3 cate3) {
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra("catgId", cate3.getProdCatgId());
        intent.putExtra("typeId", cate3.getProdTypeId());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product);
        setToolbar(false);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        String catgId = getIntent().getStringExtra("catgId");
        String brandID = getIntent().getStringExtra("brandID");
        String typeId = getIntent().getStringExtra("typeId");
        String searchKey = getIntent().getStringExtra("searchKey");

        //初始化控制器
        productListSortController = new ProductListSortController(binding.includeProductlistSort);
        productListContentController = new ProductListContentController(binding.includeProductlistContent);
        productListSortController.setShadowView(binding.includeProductlistContent.shadow);
        productListContentController.setCatgId(TextUtils.isEmpty(catgId) ? "1" : catgId);
        productListContentController.setBrandID(brandID);
        productListContentController.setTypeId(typeId);
        if (!TextUtils.isEmpty(catgId)) productListSortController.setSelectCate(catgId);
        if (!TextUtils.isEmpty(brandID)) productListSortController.setSelectBrand(brandID);
        if (!TextUtils.isEmpty(searchKey)) binding.editSearch.setText(searchKey);
        productListSortController.setOnSortSelectListenner(new ProductListSortController.OnSortSelectListenner() {
            @Override
            public void onSort(String sort) {
                productListContentController.setSort(sort);
                productListContentController.netGetProductList(true);
            }

            @Override
            public void onSelectCate(String catgId) {
                productListContentController.setTypeId(null);
                productListContentController.setCatgId(catgId);
                productListContentController.netGetProductList(true);
            }

            @Override
            public void onSelectBrand(String brandID) {
                productListContentController.setTypeId(null);
                productListContentController.setBrandID(brandID);
                productListContentController.netGetProductList(true);
            }
        });
    }

    private void initView() {
    }

    private void initCtrl() {
    }

    private void initData() {
        productListSortController.checkFirst();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
                finish();
                break;
            case R.id.btn_right:
                if (productListContentController.isGridMode()) {
                    productListContentController.setListMode();
                    binding.btnRight.setSelected(false);
                } else {
                    productListContentController.setGridMode();
                    binding.btnRight.setSelected(true);
                }
                break;
        }
    }
}
