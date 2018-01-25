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

@Dao
public interface HistoryTableDao {
    @Query("SELECT * FROM HistoryTable")
    List<HistoryTable> querryAll();

    @Query("SELECT * FROM HistoryTable WHERE id IN (:ids)")
    List<HistoryTable> queryById(int[] ids);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(HistoryTable... historyTables);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAll(HistoryTable... historyTables);

    @Delete
    void deleteAll(HistoryTable... historyTables);

}
