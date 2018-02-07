package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.view.View;

import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.bean.product.ProductDetail;
import com.magicbeans.xgate.databinding.LayProductdetailDescribeBinding;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ProductDetailDescribeController extends BaseController<LayProductdetailDescribeBinding>{

    public ProductDetailDescribeController(LayProductdetailDescribeBinding binding) {
        super(binding);
        initCtrl();
        initData();
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    public void setData(ProductDetail productDetail) {
        Product2 product2 = productDetail.getSelectProduct(productDetail.getProdID());
        if (product2 != null && !StrUtil.isEmpty(product2.getDescription())) {
            binding.textDescribe.setVisibility(View.VISIBLE);
            binding.textDescribe.setText(product2.getShowDescription());
        } else {
            binding.textDescribe.setVisibility(View.GONE);
        }
    }
}
