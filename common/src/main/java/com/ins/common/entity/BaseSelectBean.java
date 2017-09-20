package com.ins.common.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/25.
 * 选择实体基类
 */

public class BaseSelectBean implements Serializable {

    //是否被选择
    protected boolean isSelect = false;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
