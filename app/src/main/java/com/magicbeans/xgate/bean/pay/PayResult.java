package com.magicbeans.xgate.bean.pay;

import com.ins.common.utils.StrUtil;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/2/13.
 */

public class PayResult implements Serializable {

    //0 支付成功，1支付失败，2支付取消
    private int status;
    private String SOID;
    private float amount;
    private String payType;

    public PayResult(PaypalResult paypalResult) {
        this.SOID = paypalResult.getSOID();
        this.amount = paypalResult.getAmount();
        //FIXME:服务器没有返回支付方式，这里写个默认，这个问题得跟服务器开发人员提
        this.payType = "信用卡支付";
        this.status = paypalResult.isPaySuccess() ? 0 : 1;
    }

    public PayResult(AdyenResult adyenResult) {
        this.SOID = adyenResult.getMerchantReference();
        this.amount = StrUtil.str2float(adyenResult.getAmount());
        this.payType = adyenResult.getPaidMethond();
        //FIXME:服务器没有返回支付状态，这个问题也要追
        this.status = 0;
    }

    public PayResult() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSOID() {
        return SOID;
    }

    public void setSOID(String SOID) {
        this.SOID = SOID;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        amount = amount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
