package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ins.common.common.ItemDecorationDivider;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.common.KeyValue;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.bean.product.ProductDetail;
import com.magicbeans.xgate.databinding.LayProductdetailAttrBinding;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterProductAttr;
import com.magicbeans.xgate.ui.dialog.DialogBottomProductAttr;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ProductDetailAttrController {

    private Context context;
    private LayProductdetailAttrBinding binding;

    private DialogBottomProductAttr dialogBottomProductAttr;

    public ProductDetailAttrController(LayProductdetailAttrBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
        initCtrl();
        initData();
    }

    private void initCtrl() {
        dialogBottomProductAttr = new DialogBottomProductAttr(context);
        binding.btnSelectattr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBottomProductAttr.show();
            }
        });
        dialogBottomProductAttr.setOnSelectListenner(new DialogBottomProductAttr.OnSelectListenner() {
            @Override
            public void onSelect(Product2 product2) {
                dialogBottomProductAttr.hide();
                binding.btnSelectattr.setText(product2.getAttrText());
                if (onSelectListenner != null) onSelectListenner.onSelect(product2);
            }
        });
    }

    private void initData() {
    }

    public void setData(ProductDetail productDetail) {
        //设置商品信息
        binding.textBrand.setText(productDetail.getBrandName());
        //设置已选则品类信息
        Product2 product2 = productDetail.getSelectProduct(productDetail.getProdID());
        if (product2 != null) binding.btnSelectattr.setText(product2.getAttrText());
        //设置弹窗已选则品类信息
        dialogBottomProductAttr.setData(productDetail);
    }

    //###########  对外监听接口  #############

    private DialogBottomProductAttr.OnSelectListenner onSelectListenner;

    public void setOnSelectListenner(DialogBottomProductAttr.OnSelectListenner onSelectListenner) {
        this.onSelectListenner = onSelectListenner;
    }
}
