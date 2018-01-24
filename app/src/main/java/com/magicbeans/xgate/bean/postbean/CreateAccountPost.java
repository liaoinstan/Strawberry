package com.magicbeans.xgate.bean.postbean;

import com.magicbeans.xgate.net.ParamHelper;

import java.io.Serializable;

/**
 * Created by Marie on 2018/1/15.
 */

public class CreateAccountPost implements Serializable {
    /**
     * {
     * "OpenId" : "O3LILj6sS2EXP2fDt-bdU3qfh7gUaaaa2",
     * "OpenIdType": 1,
     * "Email": "pinktest4@strawberrynet.com",
     * "Mobile": "14111111111",
     * "DeviceId": "a12d5d9993241b00cf9a2070484f57ca",
     * "deviceType": "iOS",
     * "DisplayName": "Pink",
     * "Language": "zh_CN",
     * "HeadImageURL": "",
     * "Gender" : 0
     * }
     */

    private String OpenId;
    private int OpenIdType;
    private String Email;
    private String Mobile;
    private String Password;
    private String DeviceId;
    private String DisplayName;
    private String HeadImageURL;
    private int Gender;
    private String Language = ParamHelper.getLanguage();
    private String deviceType = ParamHelper.getDeviceType();

    public String getOpenId() {
        return OpenId;
    }

    public void setOpenId(String openId) {
        OpenId = openId;
    }

    public int getOpenIdType() {
        return OpenIdType;
    }

    public void setOpenIdType(int openIdType) {
        OpenIdType = openIdType;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getHeadImageURL() {
        return HeadImageURL;
    }

    public void setHeadImageURL(String headImageURL) {
        HeadImageURL = headImageURL;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int gender) {
        Gender = gender;
    }

    @Override
    public String toString() {
        return "CreateAccountPost{" +
                "OpenId='" + OpenId + '\'' +
                ", OpenIdType=" + OpenIdType +
                ", Email='" + Email + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Password='" + Password + '\'' +
                ", DeviceId='" + DeviceId + '\'' +
                ", DisplayName='" + DisplayName + '\'' +
                ", HeadImageURL='" + HeadImageURL + '\'' +
                ", Gender=" + Gender +
                ", Language='" + Language + '\'' +
                ", deviceType='" + deviceType + '\'' +
                '}';
    }
}
