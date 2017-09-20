package com.ins.common.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/25.
 * 项目类别
 */

public class SinglePopBean extends BaseSelectBean implements Serializable {

    private int id;

    private String name;

    public SinglePopBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public SinglePopBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SinglePopBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
