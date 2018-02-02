package com.magicbeans.xgate.bean.postbean;

import java.io.Serializable;

/**
 * Created by Marie on 2018/1/29.
 */

public class Addr implements Serializable{
    private String FirstName;
    private String LastName;
    private String Company;
    private String Addr1;
    private String Addr2;
    private String Addr3;
    private String Country;
    private String City;
    private String Town;
    private String State;
    private String Postcode;
    private String Tel;
    private String Mobile;
    private String Fax;

    //############# 业务方法 ##############

    //获取一个默认地址，用于测试
    public static Addr getDefaulAddr(){
        Addr addr = new Addr();
        addr.setFirstName("albert");
        addr.setLastName("liaoinstan");
        addr.setAddr1("test");
        addr.setCountry("中国");
        addr.setCity("成都");
        addr.setTown("成华区");
        addr.setState("test state");
        addr.setPostcode("610100");
        addr.setTel("18276174445");
        addr.setMobile("18276174445");
        return addr;
    }

    //############# 业务方法 ##############

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getAddr1() {
        return Addr1;
    }

    public void setAddr1(String addr1) {
        Addr1 = addr1;
    }

    public String getAddr2() {
        return Addr2;
    }

    public void setAddr2(String addr2) {
        Addr2 = addr2;
    }

    public String getAddr3() {
        return Addr3;
    }

    public void setAddr3(String addr3) {
        Addr3 = addr3;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getTown() {
        return Town;
    }

    public void setTown(String town) {
        Town = town;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPostcode() {
        return Postcode;
    }

    public void setPostcode(String postcode) {
        Postcode = postcode;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        Fax = fax;
    }
}
