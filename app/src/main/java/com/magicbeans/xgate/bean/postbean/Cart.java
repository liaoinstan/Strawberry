package com.magicbeans.xgate.bean.postbean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Marie on 2018/1/29.
 */

public class Cart implements Serializable{

    private List<Prod> ProdList;

    public Cart() {
    }

    public Cart(List<Prod> prodList) {
        ProdList = prodList;
    }

    public List<Prod> getProdList() {
        return ProdList;
    }

    public void setProdList(List<Prod> prodList) {
        ProdList = prodList;
    }
}
