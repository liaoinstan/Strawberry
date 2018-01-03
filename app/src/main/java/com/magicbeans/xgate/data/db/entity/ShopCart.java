package com.magicbeans.xgate.data.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.bean.product.ProductImages;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/3.
 */
@Entity
public class ShopCart {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "prodID")
    private String ProdID;
    @ColumnInfo(name = "prodName")
    private String ProdName;
    @ColumnInfo(name = "wasPrice")
    private String WasPrice;
    @ColumnInfo(name = "shopPrice")
    private String ShopPrice;
    @ColumnInfo(name = "sizeText")
    private String SizeText;
    @ColumnInfo(name = "count")
    private int count = 1; //购买数量，默认1，无法为0
    @ColumnInfo(name = "headerImg")
    private String headerImg;


//############  业务方法  ###########

    public static Product2 convertShopcartToProduct2(final ShopCart shopCart) {
        Product2 product2 = new Product2();
        product2.setProdID(shopCart.ProdID);
        product2.setProdName(shopCart.ProdName);
        product2.setWasPrice(shopCart.WasPrice);
        product2.setShopPrice(shopCart.ShopPrice);
        product2.setSizeText(shopCart.SizeText);
        product2.setCount(shopCart.count);
        product2.setProductImages(new ArrayList<ProductImages>() {{
            ProductImages img = new ProductImages();
            img.setImg700Src(shopCart.headerImg);
            add(img);
        }});
        return product2;
    }

    public static ShopCart convertProduct2ToShopcart(Product2 product2) {
        ShopCart shopCart = new ShopCart();
        shopCart.ProdID = product2.getProdID();
        shopCart.ProdName = product2.getProdName();
        shopCart.WasPrice = product2.getWasPrice();
        shopCart.ShopPrice = product2.getShopPrice();
        shopCart.SizeText = product2.getSizeText();
        shopCart.count = product2.getCount();
        shopCart.headerImg = product2.getHeaderImg();
        return shopCart;
    }

    public static List<Product2> convertShopcartListToProduct2List(List<ShopCart> shopCarts) {
        List<Product2> product2s = new ArrayList<>();
        if (!StrUtil.isEmpty(shopCarts)) {
            for (ShopCart shopCart : shopCarts) {
                Product2 product2 = ShopCart.convertShopcartToProduct2(shopCart);
                product2s.add(product2);
            }
        }
        return product2s;
    }

    public static List<ShopCart> convertProduct2ListToShopcartList(List<Product2> product2s) {
        List<ShopCart> shopCarts = new ArrayList<>();
        if (!StrUtil.isEmpty(product2s)) {
            for (Product2 product2 : product2s) {
                ShopCart shopCart = ShopCart.convertProduct2ToShopcart(product2);
                shopCarts.add(shopCart);
            }
        }
        return shopCarts;
    }

    //############  业务方法  ###########

    public String getProdID() {
        return ProdID;
    }

    public void setProdID(String prodID) {
        ProdID = prodID;
    }

    public String getProdName() {
        return ProdName;
    }

    public void setProdName(String prodName) {
        ProdName = prodName;
    }

    public String getWasPrice() {
        return WasPrice;
    }

    public void setWasPrice(String wasPrice) {
        WasPrice = wasPrice;
    }

    public String getShopPrice() {
        return ShopPrice;
    }

    public void setShopPrice(String shopPrice) {
        ShopPrice = shopPrice;
    }

    public String getSizeText() {
        return SizeText;
    }

    public void setSizeText(String sizeText) {
        SizeText = sizeText;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    @Override
    public String toString() {
        return "ShopCart{" +
                "ProdID='" + ProdID + '\'' +
                ", ProdName='" + ProdName + '\'' +
                ", WasPrice='" + WasPrice + '\'' +
                ", ShopPrice='" + ShopPrice + '\'' +
                ", SizeText='" + SizeText + '\'' +
                ", count=" + count +
                ", headerImg='" + headerImg + '\'' +
                '}';
    }
}
