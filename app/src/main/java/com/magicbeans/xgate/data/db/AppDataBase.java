package com.magicbeans.xgate.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.magicbeans.xgate.data.db.converter.Converters;
import com.magicbeans.xgate.data.db.dao.ShopCartDao;
import com.magicbeans.xgate.data.db.dao.ShopCartTableDao;
import com.magicbeans.xgate.data.db.entity.ShopCart;
import com.magicbeans.xgate.data.db.entity.ShopCartTable;

/**
 * Created by Administrator on 2018/1/3.
 */

@Database(entities = {ShopCart.class, ShopCartTable.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase {

    private static final String DATABASE_NAME = "strawberrydb";

    private static AppDataBase INSTANCE;

    public static AppDataBase getInstance() {
        return INSTANCE;
    }

    public static void createDataBase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDataBase.class, DATABASE_NAME).build();
        }
    }

    public abstract ShopCartDao shopCartDao();
    public abstract ShopCartTableDao shopCartTableDao();
}
