package com.magicbeans.xgate.bean.common;

import com.ins.common.entity.BaseSelectBean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/19.
 */

public class TestBean extends BaseSelectBean implements Serializable{
    private int id;
    private String name;

    public TestBean() {
    }

    public TestBean(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
