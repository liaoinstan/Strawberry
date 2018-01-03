package com.magicbeans.xgate.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.magicbeans.xgate.data.db.dao.ShopCartDao;
import com.magicbeans.xgate.data.db.entity.ShopCart;

/**
 * Created by Administrator on 2018/1/3.
 */

@Database(entities = {ShopCart.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private static final String DATABASE_NAME = "strawberrydb";

    private static AppDataBase INSTANCE;

//    private AppDataBase() {
//    }

    public static AppDataBase getInstance() {
        return INSTANCE;
    }

    public static void createDataBase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDataBase.class, DATABASE_NAME).build();
        }
    }

    public abstract ShopCartDao shopCartDao();
}
