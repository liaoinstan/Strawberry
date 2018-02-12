package com.magicbeans.xgate.bean.common;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/16.
 */

public class CommonEntity implements Serializable{
    private int reponseCode;
    private String responseMsg;
    private String token;

    public int getReponseCode() {
        return reponseCode;
    }

    public void setReponseCode(int reponseCode) {
        this.reponseCode = reponseCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }
}
