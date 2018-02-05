package com.magicbeans.xgate.bean.address;

import com.ins.common.utils.StrUtil;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Marie on 2018/2/2.
 */

public class AddressWrap implements Serializable {

    private String AccountID;
    private List<Address> Addresses;

    public String getAccountID() {
        return AccountID;
    }

    //############ 业务方法 ############
    //从一个集合中移除指定id的对象
    public static void removeById(List<Address> addressList, String addressId) {
        Iterator<Address> iterator = addressList.iterator();
        while (iterator.hasNext()) {
            Address address = iterator.next();
            if (addressId.equals(address.getAddressID())) {
                iterator.remove();
            }
        }
    }

    //从地址集合中获取默认地址，没有默认地址返回null
    public static Address getDefaultAddress(List<Address> addressList) {
        if (StrUtil.isEmpty(addressList)) return null;
        for (Address address : addressList) {
            if (address.isDefault()) {
                return address;
            }
        }
        return null;
    }

    //从地址集合中获取默认地址，没有默认地址返回第一条地址
    public static Address getDefaultAddressEx(List<Address> addressList) {
        if (StrUtil.isEmpty(addressList)) return null;
        Address defaultAddress = getDefaultAddress(addressList);
        if (defaultAddress != null) {
            return defaultAddress;
        } else {
            return addressList.get(0);
        }
    }

    //############ 业务方法 ############

    public void setAccountID(String accountID) {
        AccountID = accountID;
    }

    public List<Address> getAddresses() {
        return Addresses;
    }

    public void setAddresses(List<Address> addresses) {
        Addresses = addresses;
    }
}
