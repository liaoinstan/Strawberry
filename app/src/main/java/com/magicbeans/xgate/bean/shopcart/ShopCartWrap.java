package com.magicbeans.xgate.bean.shopcart;

import android.text.TextUtils;

import com.ins.common.utils.StrUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */

public class ShopCartWrap implements Serializable {
    List<ShopCart> ProdList;

    public List<ShopCart> getProdList() {
        return ProdList;
    }

    public void setProdList(List<ShopCart> prodList) {
        ProdList = prodList;
    }

    //############## 业务方法 ###############

    public String getErrorMsg(String ProdID) {
        if (StrUtil.isEmpty(ProdList) || TextUtils.isEmpty(ProdID)) {
            return "";
        }
        for (ShopCart shopCart : ProdList) {
            if (ProdID.equals(shopCart.getProdID())) {
                return shopCart.getErrorMsg();
            }
        }
        return "";
    }
}
