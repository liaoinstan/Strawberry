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

public class ProductDetailAttrController implements View.OnClickListener {

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
        binding.btnSelectattr.setOnClickListener(this);
        dialogBottomProductAttr.setOnSelectListenner(new DialogBottomProductAttr.OnSelectListenner() {
            @Override
            public void onSelect(Product2 product2) {
                binding.btnSelectattr.setText(product2.getSizeText());
            }
        });
    }

    private void initData() {
    }

    public void setData(ProductDetail productDetail) {
        binding.textBrand.setText(productDetail.getBrandName());
        dialogBottomProductAttr.setData(productDetail);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_selectattr:
                dialogBottomProductAttr.show();
                break;
        }
    }
}
