package com.magicbeans.xgate.bean.eva;

import com.magicbeans.xgate.bean.*;

import java.util.List;

/**
 * Created by Administrator on 2017/10/13.
 */

public class EvaWrap {
    private List<Eva> products;
    private String Total;
    private String Avg;

    public List<Eva> getProducts() {
        return products;
    }

    public void setProducts(List<Eva> products) {
        this.products = products;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getAvg() {
        return Avg;
    }

    public void setAvg(String avg) {
        Avg = avg;
    }
}
