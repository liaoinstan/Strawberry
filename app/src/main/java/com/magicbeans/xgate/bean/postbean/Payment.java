package com.magicbeans.xgate.bean.postbean;

import java.io.Serializable;

/**
 * Created by Marie on 2018/1/29.
 */

public class Payment implements Serializable {
    private String PaymentType;
    private String CardNum;
    private String ExpMonth;
    private String ExpYear;
    private String ExpCVV2;

    public Payment(String paymentType) {
        PaymentType = paymentType;
    }

    public Payment() {
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String paymentType) {
        PaymentType = paymentType;
    }

    public String getCardNum() {
        return CardNum;
    }

    public void setCardNum(String cardNum) {
        CardNum = cardNum;
    }

    public String getExpMonth() {
        return ExpMonth;
    }

    public void setExpMonth(String expMonth) {
        ExpMonth = expMonth;
    }

    public String getExpYear() {
        return ExpYear;
    }

    public void setExpYear(String expYear) {
        ExpYear = expYear;
    }

    public String getExpCVV2() {
        return ExpCVV2;
    }

    public void setExpCVV2(String expCVV2) {
        ExpCVV2 = expCVV2;
    }
}
