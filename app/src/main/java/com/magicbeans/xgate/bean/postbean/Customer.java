package com.magicbeans.xgate.bean.postbean;

import java.io.Serializable;

/**
 * Created by Marie on 2018/1/29.
 */

public class Customer implements Serializable {
    private String Email;
    private String token;
    private String openId;
    private String IDCardNumber;
    private Addr BillAddr;
    private Addr ShipAddr;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getIDCardNumber() {
        return IDCardNumber;
    }

    public void setIDCardNumber(String IDCardNumber) {
        this.IDCardNumber = IDCardNumber;
    }

    public Addr getBillAddr() {
        return BillAddr;
    }

    public void setBillAddr(Addr billAddr) {
        BillAddr = billAddr;
    }

    public Addr getShipAddr() {
        return ShipAddr;
    }

    public void setShipAddr(Addr shipAddr) {
        ShipAddr = shipAddr;
    }
}
