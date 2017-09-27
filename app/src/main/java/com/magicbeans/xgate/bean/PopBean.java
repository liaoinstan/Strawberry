package com.magicbeans.xgate.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/20.
 */

public class PopBean implements Serializable {

    private String name;

    public PopBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
