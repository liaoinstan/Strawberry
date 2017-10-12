package com.magicbeans.xgate.bean.product;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SEELE on 2017/10/12.
 */

public class ProductDetail implements Serializable{

    private boolean IsOutOfStock;
    private int ResponseCode;
    private String RedirectURL;
    private String CanonicalURL;
    private String BrandURL;
    private String BrandName;
    private int BrandId;
    private String CurSymbol;
    private int CurSymbolPosition;
    private String CurrName;
    private int maxQuantity;
    private String GoogleRemarkingValue;
    private String GoogleRemarkingProdId;
    private String GoogleRemarkingpageCatg;
    private String MetaTitle;
    private String MetaDesc;
    private String NoOfReview;
    private String AverageRation;

    private List<Product2> Prods;

    public boolean isIsOutOfStock() {
        return IsOutOfStock;
    }

    public void setIsOutOfStock(boolean IsOutOfStock) {
        this.IsOutOfStock = IsOutOfStock;
    }

    public int getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(int ResponseCode) {
        this.ResponseCode = ResponseCode;
    }

    public String getRedirectURL() {
        return RedirectURL;
    }

    public void setRedirectURL(String RedirectURL) {
        this.RedirectURL = RedirectURL;
    }

    public String getCanonicalURL() {
        return CanonicalURL;
    }

    public void setCanonicalURL(String CanonicalURL) {
        this.CanonicalURL = CanonicalURL;
    }

    public String getBrandURL() {
        return BrandURL;
    }

    public void setBrandURL(String BrandURL) {
        this.BrandURL = BrandURL;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String BrandName) {
        this.BrandName = BrandName;
    }

    public int getBrandId() {
        return BrandId;
    }

    public void setBrandId(int BrandId) {
        this.BrandId = BrandId;
    }

    public String getCurSymbol() {
        return CurSymbol;
    }

    public void setCurSymbol(String CurSymbol) {
        this.CurSymbol = CurSymbol;
    }

    public int getCurSymbolPosition() {
        return CurSymbolPosition;
    }

    public void setCurSymbolPosition(int CurSymbolPosition) {
        this.CurSymbolPosition = CurSymbolPosition;
    }

    public List<Product2> getProds() {
        return Prods;
    }

    public void setProds(List<Product2> prods) {
        Prods = prods;
    }

    public String getCurrName() {
        return CurrName;
    }

    public void setCurrName(String CurrName) {
        this.CurrName = CurrName;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public String getGoogleRemarkingValue() {
        return GoogleRemarkingValue;
    }

    public void setGoogleRemarkingValue(String GoogleRemarkingValue) {
        this.GoogleRemarkingValue = GoogleRemarkingValue;
    }

    public String getGoogleRemarkingProdId() {
        return GoogleRemarkingProdId;
    }

    public void setGoogleRemarkingProdId(String GoogleRemarkingProdId) {
        this.GoogleRemarkingProdId = GoogleRemarkingProdId;
    }

    public String getGoogleRemarkingpageCatg() {
        return GoogleRemarkingpageCatg;
    }

    public void setGoogleRemarkingpageCatg(String GoogleRemarkingpageCatg) {
        this.GoogleRemarkingpageCatg = GoogleRemarkingpageCatg;
    }

    public String getMetaTitle() {
        return MetaTitle;
    }

    public void setMetaTitle(String MetaTitle) {
        this.MetaTitle = MetaTitle;
    }

    public String getMetaDesc() {
        return MetaDesc;
    }

    public void setMetaDesc(String MetaDesc) {
        this.MetaDesc = MetaDesc;
    }

    public boolean isOutOfStock() {
        return IsOutOfStock;
    }

    public void setOutOfStock(boolean outOfStock) {
        IsOutOfStock = outOfStock;
    }

    public String getNoOfReview() {
        return NoOfReview;
    }

    public void setNoOfReview(String noOfReview) {
        NoOfReview = noOfReview;
    }

    public String getAverageRation() {
        return AverageRation;
    }

    public void setAverageRation(String averageRation) {
        AverageRation = averageRation;
    }
}
