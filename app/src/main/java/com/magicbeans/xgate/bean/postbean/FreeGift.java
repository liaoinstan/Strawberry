package com.magicbeans.xgate.bean.postbean;

import java.io.Serializable;

/**
 * Created by Marie on 2018/1/29.
 */

public class FreeGift implements Serializable {
    /**
     * "DiscId": 121,
     * "ProdId": 215885,
     * "ProdNumber": "21588526708",
     * "Name": "Stila Lip Glaze - # Blood Orange",
     * "Size": "1.5ml/0.05oz",
     * "ChargePrice": "",
     * "Selected": false,
     * "ClassName": "121-215885"
     */

    private String DiscID;
    private String ProdId;
    private String ProdNumber;
    private String Name;
    private String Size;

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

    public String getProdNumber() {
        return ProdNumber;
    }

    public void setProdNumber(String prodNumber) {
        ProdNumber = prodNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }
}
