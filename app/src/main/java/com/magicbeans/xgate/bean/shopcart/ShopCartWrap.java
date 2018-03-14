package com.magicbeans.xgate.bean.shopcart;

import android.text.TextUtils;

import com.ins.common.utils.StrUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */

public class ShopCartWrap implements Serializable {
    private List<ShopCart> ProdList;
    private Shipment Shipment;
    private List<Promote> PromoList;
    private List<Surcharge> Surcharge;
    private String SubTotal;
    private String OrderTotal;

    public List<ShopCart> getProdList() {
        return ProdList;
    }

    public void setProdList(List<ShopCart> prodList) {
        ProdList = prodList;
    }

    public com.magicbeans.xgate.bean.shopcart.Shipment getShipment() {
        return Shipment;
    }

    public void setShipment(com.magicbeans.xgate.bean.shopcart.Shipment shipment) {
        Shipment = shipment;
    }

    public String getOrderTotal() {
        return OrderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        OrderTotal = orderTotal;
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

    public String getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(String subTotal) {
        SubTotal = subTotal;
    }
    //############## 业务方法 ###############

    public String getErrorMsg(String ProdID) {
        if (StrUtil.isEmpty(ProdList) || TextUtils.isEmpty(ProdID)) {
            return "";
        }
        for (ShopCart shopCart : ProdList) {
            if (ProdID.equals(shopCart.getProdID())) {
                return shopCart.getErrorMsg();
            }
        }
        return "";
    }
}
