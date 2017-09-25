package com.magicbeans.xgate.bean;

import com.ins.common.entity.BaseSelectBean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/20.
 */

//用户实体，占位，目前没有字段
public class Brand extends SortBean implements Serializable{
    private int id;

    public Brand(String sortName) {
        super(sortName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
