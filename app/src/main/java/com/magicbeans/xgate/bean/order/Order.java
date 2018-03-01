package com.magicbeans.xgate.bean.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/20.
 */

public class Order implements Serializable {

    public static final int STATUS_ALL = 0;
    public static final int STATUS_UNPAY = 1;
    public static final int STATUS_UNOUT = 2;
    public static final int STATUS_UNIN = 3;
    public static final int STATUS_UNEVA = 4;

    private String SOID;
    private String OrderDate;
    private String OrderStatus;
    private String OrderStatusImg;
    private String OrderStatusId;
    private String OrderDateFormatted;
    private String NetAmount;
    private String CurSymbol;
    private String NetAmountFormatted;

    private List<OrderProduct> ItemList;

    public Order() {
    }

    public Order(List<OrderProduct> itemList) {
        ItemList = itemList;
    }

    public String getSOID() {
        return SOID;
    }

    public void setSOID(String SOID) {
        this.SOID = SOID;
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

    public String getOrderStatusImg() {
        return OrderStatusImg;
    }

    public void setOrderStatusImg(String orderStatusImg) {
        OrderStatusImg = orderStatusImg;
    }

    public String getOrderStatusId() {
        return OrderStatusId;
    }

    public void setOrderStatusId(String orderStatusId) {
        OrderStatusId = orderStatusId;
    }

    public String getOrderDateFormatted() {
        return OrderDateFormatted;
    }

    public void setOrderDateFormatted(String orderDateFormatted) {
        OrderDateFormatted = orderDateFormatted;
    }

    public String getNetAmount() {
        return NetAmount;
    }

    public void setNetAmount(String netAmount) {
        NetAmount = netAmount;
    }

    public String getCurSymbol() {
        return CurSymbol;
    }

    public void setCurSymbol(String curSymbol) {
        CurSymbol = curSymbol;
    }

    public String getNetAmountFormatted() {
        return NetAmountFormatted;
    }

    public void setNetAmountFormatted(String netAmountFormatted) {
        NetAmountFormatted = netAmountFormatted;
    }

    public List<OrderProduct> getItemList() {
        return ItemList;
    }

    public void setItemList(List<OrderProduct> itemList) {
        ItemList = itemList;
    }
}
