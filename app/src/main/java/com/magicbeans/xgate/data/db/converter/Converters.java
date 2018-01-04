package com.magicbeans.xgate.data.db.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.magicbeans.xgate.bean.product.Product2;

public class Converters {
    @TypeConverter
    public static String fromProduct2(Product2 product2) {
        return new Gson().toJson(product2);
    }

    @TypeConverter
    public static Product2 toProduct2(String string) {
        return new Gson().fromJson(string, Product2.class);
    }
}