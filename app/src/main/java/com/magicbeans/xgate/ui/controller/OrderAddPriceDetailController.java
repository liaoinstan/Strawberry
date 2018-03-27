package com.magicbeans.xgate.ui.controller;

import android.support.v7.widget.LinearLayoutManager;

import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.bean.checkout.CheckoutWrap;
import com.magicbeans.xgate.bean.checkout.PromoList;
import com.magicbeans.xgate.bean.order.OrderPriceDetail;
import com.magicbeans.xgate.bean.shopcart.Promote;
import com.magicbeans.xgate.bean.shopcart.ShopCartInfo;
import com.magicbeans.xgate.bean.shopcart.Surcharge;
import com.magicbeans.xgate.databinding.LayOrderaddPricedetailBinding;
import com.magicbeans.xgate.helper.AppHelper;
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

    public void setCheckOut(CheckoutWrap wrap){
        List<OrderPriceDetail> results = new ArrayList<>();
        results.add((new OrderPriceDetail("商品总价", AppHelper.replecePriceSymbol(wrap.getOrderSummary().getSubTotal()))));
        results.add(new OrderPriceDetail(wrap.getOrderSummary().getShipment().getName(), AppHelper.replecePriceSymbol(wrap.getOrderSummary().getShipment().getAmount())));

        //优惠内容
        List<PromoList> promoList = wrap.getOrderSummary().getPromoList();
        if (!StrUtil.isEmpty(promoList)) {
            for (int i = 0; i < promoList.size(); i++) {
                results.add(new OrderPriceDetail(promoList.get(i).getName(), AppHelper.replecePriceSymbol(promoList.get(i).getDiscAmount())));
            }
        }

        //附加费
        List<com.magicbeans.xgate.bean.checkout.Surcharge> surchargeList = wrap.getOrderSummary().getSurcharge();
        if (!StrUtil.isEmpty(surchargeList)) {
            for (int i = 0; i < surchargeList.size(); i++) {
                results.add(new OrderPriceDetail(surchargeList.get(i).getName(), AppHelper.replecePriceSymbol(surchargeList.get(i).getAmount())));
            }
        }
        adapter.getResults().addAll(results);
        adapter.notifyDataSetChanged();
    }

    public void setShopCartInfo(ShopCartInfo shopCartInfo) {
        this.shopCartInfo = shopCartInfo;
        if (shopCartInfo == null) return;

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
