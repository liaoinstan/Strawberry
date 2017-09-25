package com.magicbeans.xgate.bean;

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

    private int id;

    private List<Goods> goodsList;

    public Order() {
    }

    public Order(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }
}
