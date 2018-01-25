package com.magicbeans.xgate.data.db;

import android.arch.lifecycle.LiveData;

import com.magicbeans.xgate.bean.product.Product;

import java.util.List;

/**
 * Created by Administrator on 2018/1/25.
 */

public interface AppDataBaseInterface<T>{
    LiveData<List<T>> queryAll();
    void insert(T... t);
    void update(T... t);
    void delete(T... t);
}