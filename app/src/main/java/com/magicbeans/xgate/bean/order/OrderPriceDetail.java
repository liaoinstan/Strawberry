package com.magicbeans.xgate.bean.order;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/14.
 */

public class OrderPriceDetail implements Serializable{
    private String name;
    private String price;

    public OrderPriceDetail() {
    }

    public OrderPriceDetail(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
