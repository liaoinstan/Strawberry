package com.magicbeans.xgate.bean.shopcart;

import android.text.Html;

import com.google.gson.Gson;
import com.ins.common.entity.BaseSelectBean;
import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.bean.product.ProductDetail;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */

public class ShopCart extends BaseSelectBean implements Serializable {

    /**
     * ProdID : 100315
     * BrandId : 510
     * BrandName : 爱琪美  Academie
     * ProdName : 男士活性舒缓抗皱露  <br /><span class="engN">Men Active Stimulating Balm For Deep Lines</span>
     * ProductNum : 10031521321
     * Size : 50ml/1.7oz
     * Price : &#165;283.50
     * SubTotal : &#165;283.50
     * Qty : 1
     * ErrorMsg :
     * isLowInStock :
     * SalesQtyByYear :
     * BagAddedPriceDifference : -283.5
     */

    private String ProdID;
    private String BrandId;
    private String BrandName;
    private String ProdName;
    private String ProductNum;
    private String Size;
    private String Price;
    private String SubTotal;
    private int Qty;
    private String ErrorMsg;
    private String isLowInStock;
    private String SalesQtyByYear;
    private double BagAddedPriceDifference;

    //本地字段

    public ShopCart() {
    }

    public ShopCart(ProductDetail productDetail) {
        //获取选中的子产品
        Product2 product2 = productDetail.getSelectProduct(productDetail.getProdID());
        ProdID = productDetail.getProdID();
        BrandId = productDetail.getBrandId();
        BrandName = productDetail.getBrandName();
        ProdName = product2.getProdName();
        Size = product2.getSizeText();
        Price = product2.getShopPrice();
        Qty = product2.getCount();
    }

    //////////////////////业务方法///////////////////////

    public float getPriceFloat() {
        return Float.parseFloat(Price.substring(Price.indexOf(";") + 1));
    }

    //从一个购物车列表获取批量修改ids字符串参数
    public static String getBatchUpdateIds(List<ShopCart> shopCarts) {
        if (StrUtil.isEmpty(shopCarts)) return "";
        String ids = "";
        for (ShopCart shopCart : shopCarts) {
            ids += shopCart.getProdID() + "_" + shopCart.getQty() + ",";
        }
        return StrUtil.subLastChart(ids, ",");
    }

    public String getHeaderImg() {
        return "https://c.cdnsbn.com/images/products/" + ProductNum + ".jpg";
    }

    public String getTitleName() {
        return BrandName + " - " + Html.fromHtml(ProdName).toString();
    }

    //////////////////////业务方法///////////////////////

    @Override
    public boolean equals(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(this).equals(gson.toJson(obj));
    }

    public String getProdID() {
        return ProdID;
    }

    public void setProdID(String ProdID) {
        this.ProdID = ProdID;
    }

    public String getBrandId() {
        return BrandId;
    }

    public void setBrandId(String BrandId) {
        this.BrandId = BrandId;
    }

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

    public String getProductNum() {
        return ProductNum;
    }

    public void setProductNum(String ProductNum) {
        this.ProductNum = ProductNum;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String Size) {
        this.Size = Size;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(String SubTotal) {
        this.SubTotal = SubTotal;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int Qty) {
        this.Qty = Qty;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(String ErrorMsg) {
        this.ErrorMsg = ErrorMsg;
    }

    public String getIsLowInStock() {
        return isLowInStock;
    }

    public void setIsLowInStock(String isLowInStock) {
        this.isLowInStock = isLowInStock;
    }

    public String getSalesQtyByYear() {
        return SalesQtyByYear;
    }

    public void setSalesQtyByYear(String SalesQtyByYear) {
        this.SalesQtyByYear = SalesQtyByYear;
    }

    public double getBagAddedPriceDifference() {
        return BagAddedPriceDifference;
    }

    public void setBagAddedPriceDifference(double BagAddedPriceDifference) {
        this.BagAddedPriceDifference = BagAddedPriceDifference;
    }
}
