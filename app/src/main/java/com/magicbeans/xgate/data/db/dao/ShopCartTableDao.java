package com.magicbeans.xgate.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.magicbeans.xgate.data.db.entity.ShopCart;
import com.magicbeans.xgate.data.db.entity.ShopCartTable;

import java.util.List;

/**
 * Created by Administrator on 2018/1/3.
 */

@Dao
public interface ShopCartTableDao {
    @Query("SELECT * FROM ShopCartTable")
    List<ShopCartTable> querryAll();

    @Query("SELECT * FROM ShopCartTable WHERE id IN (:ids)")
    List<ShopCartTable> queryById(int[] ids);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ShopCartTable... shopCartTables);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAll(ShopCartTable... shopCartTables);

    @Delete
    void deleteAll(ShopCartTable... shopCartTables);

}
