package com.magicbeans.xgate.bean.postbean;

import java.io.Serializable;

/**
 * Created by Marie on 2018/1/29.
 */

public class FreeGift implements Serializable {
    private String DiscID;
    private String ProdId;

    public FreeGift() {
    }

    public FreeGift(String discID, String prodId) {
        DiscID = discID;
        ProdId = prodId;
    }

    public String getDiscID() {
        return DiscID;
    }

    public void setDiscID(String discID) {
        DiscID = discID;
    }

    public String getProdId() {
        return ProdId;
    }

    public void setProdId(String prodId) {
        ProdId = prodId;
    }
}
