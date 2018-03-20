package com.magicbeans.xgate.bean.pay;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/20.
 */

public class AdyenResult implements Serializable{

    /**
     * additionalData : {"acquirerResponseCode":"NOTPAY : Order not paid"}
     * pspReference : 8515215292262074
     * authResponse : Received
     * merchantReference : 1830870675
     * paidMethond : Wechatpay
     * amount : 47
     */

    private String pspReference;
    private String authResponse;
    private String merchantReference;
    private String paidMethond;
    private String amount;

    ///////////////////////////

    ///////////////////////////

    public String getPspReference() {
        return pspReference;
    }

    public void setPspReference(String pspReference) {
        this.pspReference = pspReference;
    }

    public String getAuthResponse() {
        return authResponse;
    }

    public void setAuthResponse(String authResponse) {
        this.authResponse = authResponse;
    }

    public String getMerchantReference() {
        return merchantReference;
    }

    public void setMerchantReference(String merchantReference) {
        this.merchantReference = merchantReference;
    }

    public String getPaidMethond() {
        return paidMethond;
    }

    public void setPaidMethond(String paidMethond) {
        this.paidMethond = paidMethond;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
