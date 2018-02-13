package com.magicbeans.xgate.bean.pay;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/2/13.
 */

public class PayResult implements Serializable {

    /**
     * SOID : 1820846855
     * Amount : 606.5
     * TransactionID : 9r006fdd
     * ProcessorAuthorizationCode : 8F4VCZ
     * ProcessorResponseCode : 1000
     * ProcessorResponseText : Approved
     */

    private String SOID;
    private float Amount;
    private String TransactionID;
    private String ProcessorAuthorizationCode;
    private String ProcessorResponseCode;
    private String ProcessorResponseText;

    ////////////////////////
    public int getProcessorResponseCodeInt() {
        return Integer.parseInt(ProcessorResponseCode);
    }

    public boolean isPaySuccess() {
        return "1000".equals(ProcessorResponseCode);
    }
    ////////////////////////

    public String getSOID() {
        return SOID;
    }

    public void setSOID(String SOID) {
        this.SOID = SOID;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float Amount) {
        this.Amount = Amount;
    }

    public String getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(String TransactionID) {
        this.TransactionID = TransactionID;
    }

    public String getProcessorAuthorizationCode() {
        return ProcessorAuthorizationCode;
    }

    public void setProcessorAuthorizationCode(String ProcessorAuthorizationCode) {
        this.ProcessorAuthorizationCode = ProcessorAuthorizationCode;
    }

    public String getProcessorResponseCode() {
        return ProcessorResponseCode;
    }

    public void setProcessorResponseCode(String ProcessorResponseCode) {
        this.ProcessorResponseCode = ProcessorResponseCode;
    }

    public String getProcessorResponseText() {
        return ProcessorResponseText;
    }

    public void setProcessorResponseText(String ProcessorResponseText) {
        this.ProcessorResponseText = ProcessorResponseText;
    }
}
