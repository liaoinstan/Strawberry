package com.magicbeans.xgate.data.db.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.bean.shopcart.ShopCart;

public class Converters {
    //################ ShopCart ################
    @TypeConverter
    public static String fromShopCart(ShopCart product2) {
        return new Gson().toJson(product2);
    }

    @TypeConverter
    public static ShopCart toShopCart(String string) {
        return new Gson().fromJson(string, ShopCart.class);
    }

    //################ product2 ################
    @TypeConverter
    public static String fromProduct2(Product2 product2) {
        return new Gson().toJson(product2);
    }

    @TypeConverter
    public static Product2 toProduct2(String string) {
        return new Gson().fromJson(string, Product2.class);
    }

    //################ product ################
    @TypeConverter
    public static String fromProduct(Product product) {
        return new Gson().toJson(product);
    }

    @TypeConverter
    public static Product toProduct(String string) {
        return new Gson().fromJson(string, Product.class);
    }
}