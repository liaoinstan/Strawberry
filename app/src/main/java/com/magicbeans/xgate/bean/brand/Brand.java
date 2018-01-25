package com.magicbeans.xgate.bean.brand;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/12.
 */

public class Brand implements Serializable {

    private boolean isHeader = false;

    private String IndexName;
    private String BrandID;
    private String BrandName;
    private String BrandLangName;
    private String DisplayBrandLangName;
    private String CatgID;

    //标题头构造
    public Brand(String IndexName,String BrandLangName, boolean isHeader) {
        this.IndexName = IndexName;
        this.BrandLangName = BrandLangName;
        this.isHeader = isHeader;
    }

    //品牌构造
    public Brand(String BrandID, String CatgID, String BrandLangName, String BrandName) {
        this.BrandID = BrandID;
        this.CatgID = CatgID;
        this.BrandLangName = BrandLangName;
        this.BrandName = BrandName;
    }

    public String getIndexName() {
        return IndexName;
    }

    public void setIndexName(String indexName) {
        IndexName = indexName;
    }

    public String getBrandID() {
        return BrandID;
    }

    public void setBrandID(String BrandID) {
        this.BrandID = BrandID;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String BrandName) {
        this.BrandName = BrandName;
    }

    public String getBrandLangName() {
        return BrandLangName;
    }

    public void setBrandLangName(String BrandLangName) {
        this.BrandLangName = BrandLangName;
    }

    public String getDisplayBrandLangName() {
        return DisplayBrandLangName;
    }

    public void setDisplayBrandLangName(String DisplayBrandLangName) {
        this.DisplayBrandLangName = DisplayBrandLangName;
    }

    public String getCatgID() {
        return CatgID;
    }

    public void setCatgID(String CatgID) {
        this.CatgID = CatgID;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }
}
