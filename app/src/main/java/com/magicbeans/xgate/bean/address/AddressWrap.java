package com.magicbeans.xgate.bean.address;

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
    public static void removeById(List<Address> addressList, String addressId){
        Iterator<Address> iterator = addressList.iterator();
        while (iterator.hasNext()){
            Address address = iterator.next();
            if (addressId.equals(address.getAddressID())){
                iterator.remove();
            }
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
