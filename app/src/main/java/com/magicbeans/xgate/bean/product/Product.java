package com.magicbeans.xgate.bean.product;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class Product implements Serializable {

    private String ProdID;
    private String ProdLangName;
    private String ProdLangSize;
    private String ProdBrandLangName;
    private String RefPrice;
    private String Shopprice;
    private String WasPrice;
    private String Save;
    private String CurSymbol;
    @SerializedName("ProductImages")
    private ProductImages productImages;

    public String getProdID() {
        return ProdID;
    }

    public void setProdID(String prodID) {
        ProdID = prodID;
    }

    public String getProdLangName() {
        return ProdLangName;
    }

    public void setProdLangName(String prodLangName) {
        ProdLangName = prodLangName;
    }

    public String getProdLangSize() {
        return ProdLangSize;
    }

    public void setProdLangSize(String prodLangSize) {
        ProdLangSize = prodLangSize;
    }

    public String getProdBrandLangName() {
        return ProdBrandLangName;
    }

    public void setProdBrandLangName(String prodBrandLangName) {
        ProdBrandLangName = prodBrandLangName;
    }

    public String getRefPrice() {
        return RefPrice;
    }

    public void setRefPrice(String refPrice) {
        RefPrice = refPrice;
    }

    public String getShopprice() {
        return Shopprice;
    }

    public void setShopprice(String shopprice) {
        Shopprice = shopprice;
    }

    public String getWasPrice() {
        return WasPrice;
    }

    public void setWasPrice(String wasPrice) {
        WasPrice = wasPrice;
    }

    public String getSave() {
        return Save;
    }

    public void setSave(String save) {
        Save = save;
    }

    public String getCurSymbol() {
        return CurSymbol;
    }

    public void setCurSymbol(String curSymbol) {
        CurSymbol = curSymbol;
    }

    public ProductImages getProductImages() {
        return productImages;
    }

    public void setProductImages(ProductImages productImages) {
        this.productImages = productImages;
    }
}
