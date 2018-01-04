package com.magicbeans.xgate.ui.controller;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.ins.common.utils.L;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.bean.product.ProductDetail;
//import com.magicbeans.xgate.data.db.AppDatabaseManager;
//import com.magicbeans.xgate.data.db.entity.ShopCart;
import com.magicbeans.xgate.data.db.AppDatabaseManager;
import com.magicbeans.xgate.data.db.entity.ShopCart;
import com.magicbeans.xgate.data.db.entity.ShopCartTable;
import com.magicbeans.xgate.databinding.LayProductdetailBottombarBinding;
import com.magicbeans.xgate.databinding.LayProductdetailDescribeBinding;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ProductDetailBottombarController implements View.OnClickListener {

    private Context context;
    private LayProductdetailBottombarBinding binding;

    private ProductDetail productDetail;

    public ProductDetailBottombarController(LayProductdetailBottombarBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
        initCtrl();
        initData();
    }

    private void initCtrl() {
        binding.textShopbag.setOnClickListener(this);
        binding.textFavo.setOnClickListener(this);
        binding.textAdd.setOnClickListener(this);
    }

    private void initData() {
    }

    public void setData(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_shopbag:
                break;
            case R.id.text_favo:
                break;
            case R.id.text_add:
                if (productDetail != null) {
                    Product2 product2 = productDetail.getSelectProduct(productDetail.getProdID());
                    if (product2 != null) {
                        //###### 添加到数据库 ######
//                        AppDatabaseManager.getInstance().insertShopCart(ShopCart.convertProduct2ToShopcart(product2));
                        AppDatabaseManager.getInstance().insertShopCartTable(product2);
//                        LiveData<List<Product2>> productsLiveData = AppDatabaseManager.getInstance().queryShopCartTables();
//                        productsLiveData.observeForever(new Observer<List<Product2>>() {
//                            @Override
//                            public void onChanged(@Nullable List<Product2> product2s) {
//                                for (Product2 product2:product2s){
//                                    L.e(product2.getProdID());
//                                }
//                            }
//                        });
                        //###### 添加到数据库 ######
                        ToastUtil.showToastLong("测试：\nid：" + product2.getProdID() + "\n类别:" + product2.getSizeText() + "\n数量：" + product2.getCount() + "\n添加成功");
                    }
                }
                break;
        }
    }
}
