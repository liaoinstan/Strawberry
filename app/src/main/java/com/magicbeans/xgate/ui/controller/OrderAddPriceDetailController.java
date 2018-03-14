package com.magicbeans.xgate.ui.controller;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.view.bundleimgview.BundleImgEntity;
import com.ins.common.view.bundleimgview.BundleImgView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.order.OrderPriceDetail;
import com.magicbeans.xgate.bean.shopcart.Promote;
import com.magicbeans.xgate.bean.shopcart.ShopCart;
import com.magicbeans.xgate.bean.shopcart.ShopCartInfo;
import com.magicbeans.xgate.bean.shopcart.Surcharge;
import com.magicbeans.xgate.databinding.LayOrderaddPricedetailBinding;
import com.magicbeans.xgate.databinding.LayOrderaddProductsBinding;
import com.magicbeans.xgate.helper.AppHelper;
import com.magicbeans.xgate.ui.activity.OrderProductActivity;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterEva;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterPriceDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class OrderAddPriceDetailController extends BaseController<LayOrderaddPricedetailBinding> {

    private ShopCartInfo shopCartInfo;
    private RecycleAdapterPriceDetail adapter;

    public OrderAddPriceDetailController(LayOrderaddPricedetailBinding binding) {
        super(binding);
        initCtrl();
        initData();
    }

    private void initCtrl() {
        adapter = new RecycleAdapterPriceDetail(context);
        binding.recycle.setNestedScrollingEnabled(false);
        binding.recycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.recycle.setAdapter(adapter);
    }

    private void initData() {
    }

    public void setShopCartInfo(ShopCartInfo shopCartInfo) {
        this.shopCartInfo = shopCartInfo;

        List<OrderPriceDetail> results = new ArrayList<>();

        results.add(new OrderPriceDetail("商品总价", shopCartInfo.getSubPrice()));
        results.add(new OrderPriceDetail(shopCartInfo.getShipmentName(), shopCartInfo.getShipmentPrice()));
        //优惠内容
        List<Promote> promoList = shopCartInfo.getPromoList();
        if (!StrUtil.isEmpty(promoList)) {
            for (Promote promote : promoList) {
                results.add(new OrderPriceDetail(promote.getName(), AppHelper.replecePriceSymbol(promote.getDiscAmount())));
            }
        }
        //附加费
        List<Surcharge> surchargeList = shopCartInfo.getSurcharge();
        if (!StrUtil.isEmpty(surchargeList)) {
            for (Surcharge surcharge : surchargeList) {
                results.add(new OrderPriceDetail(surcharge.getNameNoHtml(), AppHelper.replecePriceSymbol(surcharge.getTxtAmount())));
            }
        }

        adapter.getResults().addAll(results);
        adapter.notifyDataSetChanged();
    }
}
