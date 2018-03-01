package com.magicbeans.xgate.data.db.dao;

import java.util.List;

/**
 * Created by Administrator on 2018/1/3.
 */

public interface BaseTableDao<T> {
    List<T> querryAll();

    List<T> queryById(int[] ids);

    void insert(T... historyTables);

    void update(T... historyTables);

    void delete(T... historyTables);

    void deleteAll();
}
