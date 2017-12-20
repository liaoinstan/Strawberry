package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.view.View;

import com.ins.common.entity.Image;
import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.bean.product.ProductDetail;
import com.magicbeans.xgate.databinding.LayProductdetailAttrBinding;
import com.magicbeans.xgate.databinding.LayProductdetailDescribeBinding;
import com.magicbeans.xgate.helper.AppHelper;
import com.magicbeans.xgate.ui.dialog.DialogBottomProductAttr;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ProductDetailDescribeController {

    private Context context;
    private LayProductdetailDescribeBinding binding;

    public ProductDetailDescribeController(LayProductdetailDescribeBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
        initCtrl();
        initData();
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    public void setData(ProductDetail productDetail) {
        Product2 product2 = ProductDetail.getSelectProduct(productDetail, productDetail.getProdID());
        if (product2 != null && !StrUtil.isEmpty(product2.getDescription())) {
            binding.textDescribe.setVisibility(View.VISIBLE);
            binding.textDescribe.setText(product2.getShowDescription());
        } else {
            binding.textDescribe.setVisibility(View.GONE);
        }
    }
}
