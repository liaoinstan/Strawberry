package com.magicbeans.xgate.data.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.bean.shopcart.ShopCart;

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
    @ColumnInfo(name = "bean")
    private ShopCart bean;

    public ShopCartTable(ShopCart bean) {
        this.id = bean.getProdID();
        this.bean = bean;
    }

    //############  业务方法  ###########

    public static List<ShopCart> wraps2beans(List<ShopCartTable> shopCartTables) {
        ArrayList<ShopCart> results = new ArrayList<>();
        if (!StrUtil.isEmpty(shopCartTables)) {
            for (ShopCartTable shopCartTable : shopCartTables) {
                results.add(shopCartTable.getBean());
            }
        }
        return results;
    }

    public static ShopCartTable[] beans2wraps(ShopCart[] beans) {
        ArrayList<ShopCartTable> shopCartTables = new ArrayList<>();
        for (ShopCart bean : beans) {
            shopCartTables.add(new ShopCartTable(bean));
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

    public ShopCart getBean() {
        return bean;
    }

    public void setBean(ShopCart bean) {
        this.bean = bean;
    }
}
