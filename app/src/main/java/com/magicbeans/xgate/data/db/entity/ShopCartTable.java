package com.magicbeans.xgate.data.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.bean.product.Product2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/3.
 */
@Entity(tableName = "ShopCartTable")
public class ShopCartTable {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "product")
    private Product2 bean;

    public ShopCartTable(Product2 bean) {
        this.id = bean.getProdID();
        this.bean = bean;
    }

    //############  业务方法  ###########

    public static List<Product2> wraps2beans(List<ShopCartTable> shopCartTables) {
        ArrayList<Product2> results = new ArrayList<>();
        if (!StrUtil.isEmpty(shopCartTables)) {
            for (ShopCartTable shopCartTable : shopCartTables) {
                results.add(shopCartTable.getBean());
            }
        }
        return results;
    }

    public static ShopCartTable[] beans2wraps(Product2[] product2s) {
        ArrayList<ShopCartTable> shopCartTables = new ArrayList<>();
        for (Product2 product2 : product2s) {
            shopCartTables.add(new ShopCartTable(product2));
        }
        return shopCartTables.toArray(new ShopCartTable[]{});
    }

    //############  业务方法  ###########


    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public Product2 getBean() {
        return bean;
    }

    public void setBean(Product2 bean) {
        this.bean = bean;
    }
}
