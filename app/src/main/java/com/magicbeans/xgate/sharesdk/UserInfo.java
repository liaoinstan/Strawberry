package com.magicbeans.xgate.sharesdk;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/9.
 */

public class UserInfo implements Serializable {
    private String userId;
    private String userIcon;
    private String userName;
    private String userGender;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId='" + userId + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", userName='" + userName + '\'' +
                ", userGender='" + userGender + '\'' +
                '}';
    }
}
