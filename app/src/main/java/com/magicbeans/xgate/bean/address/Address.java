package com.magicbeans.xgate.bean.address;

import android.text.TextUtils;

import com.ins.common.entity.BaseSelectBean;
import com.magicbeans.xgate.bean.postbean.Addr;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/20.
 */

public class Address extends BaseSelectBean implements Serializable {

    private String AddressID;
    private String AddressNickName;
    private String Firstname;
    private String Lastname;
    private String Address;
    private String Tel;
    private String isDefault;

    //############# 业务方法 ###############

    public boolean isDefault() {
        if (TextUtils.isEmpty(isDefault)) return false;
        return Boolean.parseBoolean(isDefault.toLowerCase());
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = String.valueOf(isDefault);
    }

    public String getTelEncry() {
        if (TextUtils.isEmpty(Tel)) return "";
        //从第4位起，后面4位显示为*
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < Tel.length(); i++) {
            char c = Tel.charAt(i);
            if (i >= 3 && i <= 6) {
                stringBuilder.append("*");
            } else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    public Addr transToAddr() {
        //"address1,address2,address2, state, city, country, 610100"
        String[] split = Address.split(",");
        Addr addr = new Addr();
        addr.setFirstName(Firstname);
        addr.setLastName(Lastname);
        addr.setAddr1(split[0]);
        addr.setCountry(split[split.length - 2]);
        addr.setCity(split[split.length - 3]);
        addr.setState(split[split.length - 4]);
        addr.setPostcode(split[split.length - 1]);
        addr.setTel(Tel);
        addr.setMobile(Tel);
        return addr;
    }

    //############# 业务方法 ###############

    //本地字段
    private String AccountID;

    public String getAddressID() {
        return AddressID;
    }

    public void setAddressID(String addressID) {
        AddressID = addressID;
    }

    public String getAddressNickName() {
        return AddressNickName;
    }

    public void setAddressNickName(String addressNickName) {
        AddressNickName = addressNickName;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getAccountID() {
        return AccountID;
    }

    public void setAccountID(String accountID) {
        AccountID = accountID;
    }
}
