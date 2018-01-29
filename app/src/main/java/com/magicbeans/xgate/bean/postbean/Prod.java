package com.magicbeans.xgate.bean.postbean;

import com.magicbeans.xgate.bean.product.Product2;

import java.io.Serializable;

/**
 * Created by Marie on 2018/1/29.
 */

public class Prod implements Serializable {
    private String ProdId;
    private int Qty;

    public Prod() {
    }

    public Prod(Product2 product2) {
        ProdId = product2.getProdID();
        Qty = product2.getCount();
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
