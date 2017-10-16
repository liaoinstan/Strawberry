package com.magicbeans.xgate.bean.user;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/16.
 */

public class SignUpWrap implements Serializable{
    private int responseCode;
    private String responseMsg;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    @Override
    public String toString() {
        return "SignUpWrap{" +
                "responseCode=" + responseCode +
                ", responseMsg='" + responseMsg + '\'' +
                '}';
    }
}
