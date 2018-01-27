package com.magicbeans.xgate.data.db.manager;

import android.arch.lifecycle.LiveData;

import com.magicbeans.xgate.bean.product.Product;

import java.util.List;

/**
 * Created by Administrator on 2018/1/25.
 */

public interface BaseTableManagerInterface<T>{
    LiveData<List<T>> queryAll();
    void insert(T... t);
    void update(T... t);
    void delete(T... t);
    void deleteAll();
}