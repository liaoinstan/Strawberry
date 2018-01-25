package com.magicbeans.xgate.sharesdk;

import com.magicbeans.xgate.net.ParamHelper;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/9.
 */

public class UserInfo implements Serializable {
    private String openId;
    private String userIcon;
    private String userName;
    private String userGender;
    private String platform;

    //////////////////////////////////////////
    public int getOpenIDType() {
        return ParamHelper.getOpenIDType(platform);
    }

    public int getUserGenderInt() {
        return "m".equals(userGender) ? 1 : 0;
    }

    //////////////////////////////////////////

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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
                "openId='" + openId + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", userName='" + userName + '\'' +
                ", userGender='" + userGender + '\'' +
                '}';
    }
}
