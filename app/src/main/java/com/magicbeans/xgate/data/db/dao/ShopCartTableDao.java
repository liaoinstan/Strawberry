package com.magicbeans.xgate.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.magicbeans.xgate.data.db.entity.ShopCartTable;

import java.util.List;

/**
 * Created by Administrator on 2018/1/3.
 */

@Dao
public interface ShopCartTableDao extends BaseTableDao<ShopCartTable> {
    @Query("SELECT * FROM ShopCartTable")
    List<ShopCartTable> querryAll();

    @Query("SELECT * FROM ShopCartTable WHERE id IN (:ids)")
    List<ShopCartTable> queryById(int[] ids);

    @Query("SELECT * FROM ShopCartTable WHERE isOffline == 1")
    List<ShopCartTable> queryAllOffline();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ShopCartTable... shopCartTables);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(ShopCartTable... shopCartTables);

    @Delete
    void delete(ShopCartTable... shopCartTables);

    @Query("DELETE FROM ShopCartTable")
    void deleteAll();

    @Query("SELECT count(1) FROM ShopCartTable")
    int count();
}
