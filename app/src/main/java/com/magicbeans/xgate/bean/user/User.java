package com.magicbeans.xgate.bean.user;

import com.google.gson.annotations.SerializedName;
import com.magicbeans.xgate.net.ParamHelper;
import com.magicbeans.xgate.sharesdk.UserInfo;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/9.
 */

public class User implements Serializable {

    /**
     * "AccountID": "90001234",
     "FirstName": "",
     "Surname": "",
     "Nickname": "???",
     "Gender": "",
     "MobileCountryCode": "",
     "MobileNumber": "18000000000",
     "DayOfBirthday": "99",
     "MonthOfBirthday": "99",
     "YearOfBirthday": "99",
     "Email": null,
     "Location": "",
     "langId": "1",
     "Currency": "",
     */

    @SerializedName(value = "accountID",alternate = "AccountID")
    private String accountID;
    private String FirstName;
    private String Surname;
    private String Nickname;
    private String Gender;
    private String MobileCountryCode;
    private String MobileNumber;
    private String DayOfBirthday;
    private String MonthOfBirthday;
    private String YearOfBirthday;
    private String Email;
//    private int Location;
    private String langId;
//    private int Currency;

    private String token;
    private String Mobile;
    private String LastName;

    //本地字段

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getMobileCountryCode() {
        return MobileCountryCode;
    }

    public void setMobileCountryCode(String mobileCountryCode) {
        MobileCountryCode = mobileCountryCode;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getDayOfBirthday() {
        return DayOfBirthday;
    }

    public void setDayOfBirthday(String dayOfBirthday) {
        DayOfBirthday = dayOfBirthday;
    }

    public String getMonthOfBirthday() {
        return MonthOfBirthday;
    }

    public void setMonthOfBirthday(String monthOfBirthday) {
        MonthOfBirthday = monthOfBirthday;
    }

    public String getYearOfBirthday() {
        return YearOfBirthday;
    }

    public void setYearOfBirthday(String yearOfBirthday) {
        YearOfBirthday = yearOfBirthday;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "accountID='" + accountID + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", Surname='" + Surname + '\'' +
                ", Nickname='" + Nickname + '\'' +
                ", Gender=" + Gender +
                ", MobileCountryCode=" + MobileCountryCode +
                ", MobileNumber='" + MobileNumber + '\'' +
                ", DayOfBirthday=" + DayOfBirthday +
                ", MonthOfBirthday=" + MonthOfBirthday +
                ", YearOfBirthday=" + YearOfBirthday +
                ", Email='" + Email + '\'' +
                ", langId=" + langId +
                ", token='" + token + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", LastName='" + LastName + '\'' +
                '}';
    }
}
