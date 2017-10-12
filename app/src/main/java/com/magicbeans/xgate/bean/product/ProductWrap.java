package com.magicbeans.xgate.bean.product;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ProductWrap implements Serializable{

    @SerializedName("ProductList")
    private List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
