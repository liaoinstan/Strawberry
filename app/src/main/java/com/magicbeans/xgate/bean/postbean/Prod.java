package com.magicbeans.xgate.bean.postbean;

import com.magicbeans.xgate.bean.shopcart.ShopCart;

import java.io.Serializable;

/**
 * Created by Marie on 2018/1/29.
 */

public class Prod implements Serializable {
    private String ProdId;
    private int Qty;

    public Prod() {
    }

    public Prod(ShopCart shopCart) {
        ProdId = shopCart.getProdID();
        Qty = shopCart.getQty();
    }


    public String getProdId() {
        return ProdId;
    }

    public void setProdId(String prodId) {
        ProdId = prodId;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }
}
