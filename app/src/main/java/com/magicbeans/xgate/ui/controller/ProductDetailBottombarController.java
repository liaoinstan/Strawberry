package com.magicbeans.xgate.ui.controller;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.ins.common.helper.ShopAnimHelper;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.bean.product.ProductDetail;
import com.magicbeans.xgate.bean.shopcart.ShopCart;
import com.magicbeans.xgate.data.db.manager.ShopCartTableManager;
import com.magicbeans.xgate.databinding.LayProductdetailBottombarBinding;
import com.magicbeans.xgate.helper.DataShopCartHelper;
import com.magicbeans.xgate.net.nethelper.NetShopCartHelper;
import com.magicbeans.xgate.ui.activity.ShopcartActivity;

import java.util.List;

//import com.magicbeans.xgate.data.db.ShopCartTableManager;
//import com.magicbeans.xgate.data.db.entity.ShopCart;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ProductDetailBottombarController implements View.OnClickListener {

    private Context context;
    private LayProductdetailBottombarBinding binding;
    private View root;

    private ProductDetail productDetail;

    public ProductDetailBottombarController(LayProductdetailBottombarBinding binding, View root) {
        this.root = root;
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
        refreshShopCount();
    }

    public void setData(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_shopbag:
                ShopcartActivity.start(context);
                break;
            case R.id.text_favo:
                ToastUtil.showToastShort("建设中,敬请期待...");
                break;
            case R.id.text_add:
                if (productDetail != null) {
                    Product2 product2 = productDetail.getSelectProduct(productDetail.getProdID());
                    if (product2 != null) {
                        //###### 添加到服务器及本地数据库 ######
                        product2.setBrandName(productDetail.getBrandName());
                        DataShopCartHelper.getInstance().addShopCart(product2);
                        //###### 飞入动画 ######
                        ShopAnimHelper.newInstance().quickStart(binding.textAdd, binding.textShopbag, (ViewGroup) root, product2.getHeaderImg());
                    }
                }
                break;
        }
    }

    public void refreshShopCount() {
        LiveData<List<ShopCart>> listLiveData = ShopCartTableManager.getInstance().queryAllBeans();
        listLiveData.observeForever(new Observer<List<ShopCart>>() {
            @Override
            public void onChanged(@Nullable List<ShopCart> shopCarts) {
                int count = ShopCartContentController.calcuCount(shopCarts);
                binding.textDotCount.setText(count + "");
                binding.textDotCount.setVisibility(count != 0 ? View.VISIBLE : View.GONE);
                YoYo.with(Techniques.Pulse)
                        .duration(300)
                        .playOn(binding.textDotCount);
            }
        });
    }
}
