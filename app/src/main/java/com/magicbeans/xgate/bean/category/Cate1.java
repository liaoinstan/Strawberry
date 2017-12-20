package com.magicbeans.xgate.bean.category;

import com.ins.common.entity.BaseSelectBean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/14.
 */

public class Cate1 extends BaseSelectBean implements Serializable {

    /**
     * title : 护肤品
     * PromotionName : null
     * type : 4
     * OthCatgId : null
     * CatgId : 1
     * brandId : null
     * URL : null
     */

    private String title;
    private String type;
    private String CatgId;
    private String brandId;

    //逻辑字段
    private boolean isHeader;

    public Cate1(boolean isHeader, String title) {
        this.title = title;
        this.isHeader = isHeader;
    }

    public Cate1(String title, String catgId) {
        this.title = title;
        CatgId = catgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCatgId() {
        return CatgId;
    }

    public void setCatgId(String catgId) {
        CatgId = catgId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }
}
