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
public interface HistoryTableDao extends BaseTableDao<HistoryTable>{
    @Query("SELECT * FROM HistoryTable ORDER BY timestamp DESC LIMIT :size OFFSET (:page-1)*:size")
    List<HistoryTable> querryLimit(int page, int size);

    @Query("SELECT * FROM HistoryTable")
    List<HistoryTable> querryAll();

    @Query("SELECT * FROM HistoryTable WHERE id IN (:ids)")
    List<HistoryTable> queryById(int[] ids);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HistoryTable... historyTables);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(HistoryTable... historyTables);

    @Delete
    void delete(HistoryTable... historyTables);

    @Query("DELETE FROM HistoryTable")
    void deleteAll();
}
