package com.magicbeans.xgate.data.db.manager;

import android.arch.lifecycle.LiveData;

import java.util.List;

/**
 * Created by Administrator on 2018/1/25.
 */

public interface BaseTableManagerInterface<T> {
    LiveData<List<T>> queryAll();

    LiveData<List<T>> queryById(int[] ids);

    LiveData<Integer> insert(T... t);

    LiveData<Integer> update(T... t);

    LiveData<Integer> delete(T... t);

    LiveData<Integer> deleteAll();
}