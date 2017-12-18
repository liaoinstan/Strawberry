package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.PopBean;
import com.magicbeans.xgate.bean.category.Cate3;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.bean.product.ProductWrap;
import com.magicbeans.xgate.databinding.ActivityProductBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterProduct;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.controller.ProductListContentController;
import com.magicbeans.xgate.ui.controller.ProductListSortController;
import com.magicbeans.xgate.ui.dialog.MyGridPopupWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public static void startCategroy(Context context, String catgId) {
        start(context, catgId, null, null);
    }

    public static void startBrand(Context context, String brandID) {
        start(context, null, brandID, null);
    }

    public static void startCate3(Context context, Cate3 cate3) {
        start(context, cate3.getProdCatgId(), null, cate3.getProdTypeId());
    }


    public static void start(Context context, String catgId, String brandID, String typeId) {
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra("catgId", catgId);
        intent.putExtra("brandID", brandID);
        intent.putExtra("typeId", typeId);
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

        //初始化控制器
        productListSortController = new ProductListSortController(binding.includeProductlistSort);
        productListContentController = new ProductListContentController(binding.includeProductlistContent);
        productListSortController.setShadowView(binding.includeProductlistContent.shadow);
        productListContentController.setCatgId(catgId);
        productListContentController.setBrandID(brandID);
        productListContentController.setTypeId(typeId);
    }

    private void initView() {
    }

    private void initCtrl() {
    }

    private void initData() {
        productListContentController.netGetProductList();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
                finish();
                break;
            case R.id.btn_right:
                if (productListContentController.isGridMode()) {
                    productListContentController.setListMode();
                } else {
                    productListContentController.setGridMode();
                }
                break;
        }
    }
}
