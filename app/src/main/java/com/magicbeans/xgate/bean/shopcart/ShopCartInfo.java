package com.magicbeans.xgate.bean.shopcart;

import com.magicbeans.xgate.helper.AppHelper;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/6.
 */

public class ShopCartInfo implements Serializable{
    //订单总价
    private String totalPrice;
    //应该支付总价
    private String payPrice;
    //运费
    private String shipmentPrice;
    //运送方式
    private String shipmentName;

    public ShopCartInfo() {
    }

    public ShopCartInfo(ShopCartWrap shopCartWrap) {
        totalPrice = AppHelper.replecePriceSymbol(shopCartWrap.getOrderTotal());
        Shipment shipment = shopCartWrap.getShipment();
        if (shipment != null) {
            shipmentPrice = AppHelper.replecePriceSymbol(shipment.getTxtAmount());
            shipmentName = AppHelper.replecePriceSymbol(shipment.getName());
        }
    }

    public String getTotalPrice() {
        return totalPrice;
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
