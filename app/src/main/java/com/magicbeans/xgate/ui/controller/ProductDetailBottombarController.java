package com.magicbeans.xgate.ui.controller;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.ins.common.common.SinpleShowInAnimatorListener;
import com.ins.common.helper.ShopAnimHelper;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.bean.product.ProductDetail;
import com.magicbeans.xgate.data.db.manager.ShopcartTableManager;
import com.magicbeans.xgate.databinding.LayProductdetailBottombarBinding;
import com.magicbeans.xgate.net.nethelper.NetShopCartHelper;
import com.magicbeans.xgate.ui.activity.ShopcartActivity;

import org.greenrobot.eventbus.EventBus;

//import com.magicbeans.xgate.data.db.ShopcartTableManager;
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
                NetShopCartHelper.getInstance().netGetShopCartList();
                break;
            case R.id.text_add:
                if (productDetail != null) {
                    Product2 product2 = productDetail.getSelectProduct(productDetail.getProdID());
                    if (product2 != null) {
                        //###### 添加到本地数据库 ######
                        ShopcartTableManager.getInstance().insert(product2);
//                        ToastUtil.showToastLong("测试：\nid：" + product2.getProdID() + "\n类别:" + product2.getSizeText() + "\n数量：" + product2.getCount() + "\n添加成功");
                        //###### 添加到服务器 ######
                        NetShopCartHelper.getInstance().netAddShopCart(product2.getProdID());
                        //###### 飞入动画 ######
                        ShopAnimHelper.newInstance().setOnAnimListener(new ShopAnimHelper.AnimListener() {
                            @Override
                            public void setAnimStart() {

                            }

                            @Override
                            public void setAnimEnd() {
                                EventBus.getDefault().post(new EventBean(EventBean.EVENT_REFRESH_SHOPCART));
                            }
                        }).quickStart(binding.textAdd, binding.textShopbag, (ViewGroup) root, product2.getHeaderImg());
                    }
                }
                break;
        }
    }

    public void refreshShopCount() {
        MutableLiveData<Integer> count = ShopcartTableManager.getInstance().count();
        count.observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                binding.textDotCount.setText(integer + "");
                binding.textDotCount.setVisibility(integer != 0 ? View.VISIBLE : View.GONE);
                YoYo.with(Techniques.Pulse)
                        .duration(300)
                        .playOn(binding.textDotCount);
            }
        });
    }
}
