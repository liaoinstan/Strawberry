package com.magicbeans.xgate.bean.address;

import java.io.Serializable;

/**
 * Created by Marie on 2018/3/5.
 */

public class District implements Serializable{
    private String name;
    private String postcode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
