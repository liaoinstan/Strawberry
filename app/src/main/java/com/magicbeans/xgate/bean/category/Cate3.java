package com.magicbeans.xgate.bean.category;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/14.
 */

public class Cate3 implements Serializable {

    private String ProdTypeId;
    private String ProdTypeName;
    //本地字段
    private String ProdCatgId;
    //逻辑字段
    private boolean isHeader;
    private String HeaderName;

    //header构造
    public Cate3(boolean isHeader, String headerName) {
        this.isHeader = isHeader;
        HeaderName = headerName;
    }

    public String getProdTypeId() {
        return ProdTypeId;
    }

    public void setProdTypeId(String prodTypeId) {
        ProdTypeId = prodTypeId;
    }

    public String getProdTypeName() {
        return ProdTypeName;
    }

    public void setProdTypeName(String prodTypeName) {
        ProdTypeName = prodTypeName;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public String getHeaderName() {
        return HeaderName;
    }

    public void setHeaderName(String headerName) {
        HeaderName = headerName;
    }

    public String getProdCatgId() {
        return ProdCatgId;
    }

    public void setProdCatgId(String prodCatgId) {
        ProdCatgId = prodCatgId;
    }
}
