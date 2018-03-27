package com.magicbeans.xgate.bean.checkout;

import java.util.List;

/**
 * Created by Marie on 2018/3/27.
 */

public class OrderSummary {

    private String SubTotal;
    private String OrderTotal;
    private List<PromoList> PromoList;
    private Shipment Shipment;
    private List<Surcharge> Surcharge;

    public List<com.magicbeans.xgate.bean.checkout.Surcharge> getSurcharge() {
        return Surcharge;
    }

    public void setSurcharge(List<com.magicbeans.xgate.bean.checkout.Surcharge> surcharge) {
        Surcharge = surcharge;
    }

    public String getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(String subTotal) {
        SubTotal = subTotal;
    }

    public String getOrderTotal() {
        return OrderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        OrderTotal = orderTotal;
    }

    public List<com.magicbeans.xgate.bean.checkout.PromoList> getPromoList() {
        return PromoList;
    }

    public void setPromoList(List<com.magicbeans.xgate.bean.checkout.PromoList> promoList) {
        PromoList = promoList;
    }

    public com.magicbeans.xgate.bean.checkout.Shipment getShipment() {
        return Shipment;
    }

    public void setShipment(com.magicbeans.xgate.bean.checkout.Shipment shipment) {
        Shipment = shipment;
    }
}
