package com.magicbeans.xgate.bean.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/2/6.
 */

public class OrderDetail implements Serializable{
    private String SOID;
    private String NetAmount;
    private String OrderDate;
    private String OrderStatus;
    private String fullShipAddr;
    private List<OrderProduct> ProductOrderList;


    public String getFullShipAddr() {
        return fullShipAddr;
    }

    public void setFullShipAddr(String fullShipAddr) {
        this.fullShipAddr = fullShipAddr;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

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
