package com.magicbeans.xgate.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.magicbeans.xgate.data.db.entity.ShopCart;

import java.util.List;

/**
 * Created by Administrator on 2018/1/3.
 */

@Dao
public interface ShopCartDao {
    @Query("SELECT * FROM ShopCart")
    List<ShopCart> querryAll();

    @Query("SELECT * FROM ShopCart WHERE ProdID IN (:ProdIDs)")
    List<ShopCart> queryById(int[] ProdIDs);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ShopCart... shopCarts);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAll(ShopCart... shopCarts);

    @Delete
    void deleteAll(ShopCart... shopCarts);

}
