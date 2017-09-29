package com.magicbeans.xgate.bean;

import android.text.TextUtils;

import com.ins.common.entity.BaseSelectBean;
import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.utils.SortUtil;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/20.
 */

//用户实体，占位，目前没有字段
public class Brand extends SortBean implements Serializable {
    private int id;
    private boolean isHeader = false;

    public Brand(String sortName) {
        super(sortName);
    }

    public Brand(String sortName, String sortTag, boolean isHeader) {
        super(sortName);
        setSortTag(sortTag);
        this.isHeader = isHeader;
    }

    //###############业务方法################

    //#################################

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }
}
