package com.magicbeans.xgate.data.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.bean.product.Product2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/3.
 */
@Entity(tableName = "HistoryTable")
public class HistoryTable {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "bean")
    private Product bean;

    public HistoryTable(Product bean) {
        this.id = bean.getProdID();
        this.bean = bean;
    }

    //############  业务方法  ###########

    public static List<Product> wraps2beans(List<HistoryTable> tables) {
        ArrayList<Product> results = new ArrayList<>();
        if (!StrUtil.isEmpty(tables)) {
            for (HistoryTable historyTable : tables) {
                results.add(historyTable.getBean());
            }
        }
        return results;
    }

    public static HistoryTable[] beans2wraps(Product[] beans) {
        ArrayList<HistoryTable> tables = new ArrayList<>();
        for (Product bean : beans) {
            tables.add(new HistoryTable(bean));
        }
        return tables.toArray(new HistoryTable[]{});
    }

    //############  业务方法  ###########


    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public Product getBean() {
        return bean;
    }

    public void setBean(Product bean) {
        this.bean = bean;
    }
}
