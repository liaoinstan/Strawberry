package com.magicbeans.xgate.bean.product;

import com.ins.common.entity.Image;
import com.ins.common.utils.StrUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEELE on 2017/10/12.
 */

public class ProductDetail implements Serializable {

    private boolean IsOutOfStock;
    private int ResponseCode;
    private String RedirectURL;
    private String CanonicalURL;
    private String BrandURL;
    private String BrandName;
    private String BrandId;
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

    //新增字段
    private String ProdID;

    // ###########  逻辑方法  ################

    //从ProductDetail详情实体中找到指定id的Product2实体
    public Product2 getSelectProduct(String prodId) {
        return Product2.findProduct2ById(Prods, prodId);
    }

    //从ProductDetail详情实体中找所有的Product2的产品图片，并以集合形式返回
    public List<Image> getImgs() {
        ArrayList<Image> imgs = new ArrayList<>();
        if (!StrUtil.isEmpty(Prods)) {
            for (Product2 product2 : Prods) {
                List<ProductImages> productImages = product2.getProductImages();
                if (!StrUtil.isEmpty(productImages)) {
                    for (ProductImages productImage : productImages) {
                        imgs.add(new Image(productImage.getImg700Src()));
                    }
                }
            }
        }
        return imgs;
    }

    public Product trans2Product() {
        Product product = new Product();
        product.setProdID(ProdID);
        Product2 product2 = getSelectProduct(ProdID);
        if (product2!=null) {
            product.setProdLangName(product2.getProdName());
            product.setProdLangSize(product2.getSizeText());
            product.setShopprice(product2.getShopPrice());
            product.setWasPrice(product2.getWasPrice());
            product.setProductImages(product2.getProductImages().get(0));
        }
        return product;
    }
    // ###########  逻辑方法  ################

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

    public String getBrandId() {
        return BrandId;
    }

    public void setBrandId(String brandId) {
        BrandId = brandId;
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

    public String getProdID() {
        return ProdID;
    }

    public void setProdID(String prodID) {
        ProdID = prodID;
    }
}
