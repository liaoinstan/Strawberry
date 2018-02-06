package com.magicbeans.xgate.bean.order;

import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.bean.Order;
import com.magicbeans.xgate.bean.product.Product;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Marie on 2018/2/5.
 */

public class OrderWrap implements Serializable {
    private int ResponseCode;
    private String responseMsg;
    private List<Order> Orders;
    //推荐商品列表
    private List<Product> ProductList;

    /////////////////// 业务方法 /////////////////////


    /////////////////// 业务方法 /////////////////////

    public int getResponseCode() {
        return ResponseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public void setResponseCode(int responseCode) {
        ResponseCode = responseCode;
    }

    public List<Order> getOrders() {
        return Orders;
    }

    public void setOrders(List<Order> orders) {
        Orders = orders;
    }

    public List<Product> getProductList() {
        return ProductList;
    }

    public void setProductList(List<Product> productList) {
        ProductList = productList;
    }
}
