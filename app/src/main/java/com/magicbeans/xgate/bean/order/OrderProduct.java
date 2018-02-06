package com.magicbeans.xgate.bean.order;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/2/6.
 */

public class OrderProduct implements Serializable{

    /**
     * BrandName : 加州巴克斯特
     * ProdName : 面膜泥 (中性至油性肌肤)
     * ProdSize : 120ml/4oz
     * Img : https://a.cdnsbn.com/images/products/msn/13582914121.jpg
     * ProdId : 135829
     * UnitPrice : 145.50
     * Qty : 20
     * TotalPrice : 2,910.00
     * OutOfStockMsg : null
     * isHidden : null
     * CurSymbol : &#165;
     */

    private String BrandName;
    private String ProdName;
    private String ProdSize;
    private String Img;
    private String ProdId;
    private String UnitPrice;
    private String Qty;
    private String TotalPrice;
    private String OutOfStockMsg;
    private boolean isHidden;
    private String CurSymbol;

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String BrandName) {
        this.BrandName = BrandName;
    }

    public String getProdName() {
        return ProdName;
    }

    public void setProdName(String ProdName) {
        this.ProdName = ProdName;
    }

    public String getProdSize() {
        return ProdSize;
    }

    public void setProdSize(String ProdSize) {
        this.ProdSize = ProdSize;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String Img) {
        this.Img = Img;
    }

    public String getProdId() {
        return ProdId;
    }

    public void setProdId(String ProdId) {
        this.ProdId = ProdId;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String UnitPrice) {
        this.UnitPrice = UnitPrice;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String Qty) {
        this.Qty = Qty;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String TotalPrice) {
        this.TotalPrice = TotalPrice;
    }

    public Object getOutOfStockMsg() {
        return OutOfStockMsg;
    }

    public void setOutOfStockMsg(String outOfStockMsg) {
        OutOfStockMsg = outOfStockMsg;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public String getCurSymbol() {
        return CurSymbol;
    }

    public void setCurSymbol(String curSymbol) {
        CurSymbol = curSymbol;
    }
}
