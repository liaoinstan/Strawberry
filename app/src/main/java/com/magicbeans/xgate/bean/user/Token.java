package com.magicbeans.xgate.bean.user;

import android.text.TextUtils;

import com.magicbeans.xgate.common.AppData;

import java.io.Serializable;

/**
 * Created by Marie on 2018/1/22.
 * 记录在本地的用户登录信息，需要持久化
 */

public class Token implements Serializable {
    private String accountID;
    private String token;

    public Token() {
    }

    public Token(String accountID, String token) {
        this.accountID = accountID;
        this.token = token;
    }

    //############# 业务方法 ###############

    //检查token信息是否为null
    public static boolean isEmpty(Token token) {
        if (token != null && !TextUtils.isEmpty(token.getAccountID()) && !TextUtils.isEmpty(token.getToken())) {
            return false;
        } else {
            return true;
        }
    }

    //从本地获取Token，失败返回""
    public static String getLocalToken() {
        Token token = AppData.App.getToken();
        if (token != null && !TextUtils.isEmpty(token.getToken())) {
            return token.getToken();
        } else {
            return "";
        }
    }

    //accountID，失败返回""
    public static String getLocalAccountId() {
        Token token = AppData.App.getToken();
        if (token != null && !TextUtils.isEmpty(token.getAccountID())) {
            return token.getAccountID();
        } else {
            return "";
        }
    }

    //############################

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
