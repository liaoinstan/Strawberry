package com.magicbeans.xgate.bean.shopcart;

import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.bean.order.OrderPriceDetail;
import com.magicbeans.xgate.helper.AppHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/6.
 * Order Total = Sub Total - Discount Amount + Shipment Amount + Surcharge Amount
 * 订单总价 = 商品总价 - 折扣总额 + 运费 + 附加费
 */

public class ShopCartInfo implements Serializable {
    //订单总价
    private String totalPrice;
    //商品总价
    private String subPrice;
    //运费
    private String shipmentPrice;
    //运送方式
    private String shipmentName;

    private String OrderTotal;
    //优惠
    private List<Promote> PromoList;
    //附加费
    private List<Surcharge> Surcharge;

    public ShopCartInfo() {
    }

    public ShopCartInfo(ShopCartWrap shopCartWrap) {
        totalPrice = AppHelper.replecePriceSymbol(shopCartWrap.getOrderTotal());
        subPrice = AppHelper.replecePriceSymbol(shopCartWrap.getSubTotal());
        Shipment shipment = shopCartWrap.getShipment();
        if (shipment != null) {
            shipmentPrice = AppHelper.replecePriceSymbol(shipment.getTxtAmount());
            shipmentName = AppHelper.replecePriceSymbol(shipment.getName());
        }
        PromoList = shopCartWrap.getPromoList();
        Surcharge = shopCartWrap.getSurcharge();
    }

    ///////////////////////////////////////////////////////////////////////

    public String getInfoStr() {
        String infoStr = "";
        infoStr += "配送方式：" + shipmentName + "\n";
        infoStr += "当前运费：" + shipmentPrice + "\n";
        //优惠内容
        if (!StrUtil.isEmpty(PromoList)) {
            for (Promote promote : PromoList) {
                infoStr += promote.getName() + shipmentName + AppHelper.replecePriceSymbol(promote.getDiscAmount()) + "\n";
            }
        }
        //附加费
        if (!StrUtil.isEmpty(Surcharge)) {
            for (Surcharge surcharge : Surcharge) {
                infoStr += surcharge.getNameNoHtml() + shipmentName + AppHelper.replecePriceSymbol(surcharge.getTxtAmount()) + "\n";
            }
        }
        return infoStr;
    }
    ///////////////////////////////////////////////////////////////////////

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getSubPrice() {
        return subPrice;
    }

    public void setSubPrice(String subPrice) {
        this.subPrice = subPrice;
    }

    public List<Promote> getPromoList() {
        return PromoList;
    }

    public void setPromoList(List<Promote> promoList) {
        PromoList = promoList;
    }

    public List<com.magicbeans.xgate.bean.shopcart.Surcharge> getSurcharge() {
        return Surcharge;
    }

    public void setSurcharge(List<com.magicbeans.xgate.bean.shopcart.Surcharge> surcharge) {
        Surcharge = surcharge;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getShipmentPrice() {
        return shipmentPrice;
    }

    public String getShipmentName() {
        return shipmentName;
    }

    public void setShipmentName(String shipmentName) {
        this.shipmentName = shipmentName;
    }

    public void setShipmentPrice(String shipmentPrice) {
        this.shipmentPrice = shipmentPrice;
    }
}
