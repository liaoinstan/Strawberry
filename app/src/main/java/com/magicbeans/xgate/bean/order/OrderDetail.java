package com.magicbeans.xgate.bean.order;

import com.magicbeans.xgate.bean.product.Product;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/2/6.
 */

public class OrderDetail implements Serializable{
    private String SOID;
    private String NetAmount;
    private List<OrderProduct> ProductOrderList;

    public String getSOID() {
        return SOID;
    }

    public void setSOID(String SOID) {
        this.SOID = SOID;
    }

    public String getNetAmount() {
        return NetAmount;
    }

    public void setNetAmount(String netAmount) {
        NetAmount = netAmount;
    }

    public List<OrderProduct> getProductOrderList() {
        return ProductOrderList;
    }

    public void setProductOrderList(List<OrderProduct> productOrderList) {
        ProductOrderList = productOrderList;
    }
}
