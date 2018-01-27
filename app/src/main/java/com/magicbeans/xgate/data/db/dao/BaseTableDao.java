package com.magicbeans.xgate.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.magicbeans.xgate.data.db.entity.HistoryTable;

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
