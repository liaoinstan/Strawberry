package com.magicbeans.xgate.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;


import com.magicbeans.xgate.data.db.entity.ShopCart;

import java.util.ArrayList;
import java.util.List;

/**
 * AppDatabaseManager.java
 * <p>
 * Created by lijiankun on 17/7/5.
 */

public class AppDatabaseManager {

    private static AppDatabaseManager INSTANCE;

    private AppDatabaseManager() {
    }

    public static AppDatabaseManager getInstance() {
        if (INSTANCE == null) {
            synchronized (AppDatabaseManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppDatabaseManager();
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<List<ShopCart>> queryShopCarts() {
        final MutableLiveData<List<ShopCart>> resultsLiveData = new MediatorLiveData<>();
        new AsyncTask<Void, Void, List<ShopCart>>() {
            @Override
            protected List<ShopCart> doInBackground(Void... voids) {
                List<ShopCart> results = new ArrayList<>();
                AppDataBase.getInstance().beginTransaction();
                try {
                    results.addAll(AppDataBase.getInstance().shopCartDao().querryAll());
                    AppDataBase.getInstance().setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    AppDataBase.getInstance().endTransaction();
                }
                return results;
            }

            @Override
            protected void onPostExecute(List<ShopCart> shopCarts) {
                super.onPostExecute(shopCarts);
                resultsLiveData.setValue(shopCarts);
            }
        }.execute();
        return resultsLiveData;
    }

    public void insertShopCart(final ShopCart... shopCarts) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                AppDataBase.getInstance().beginTransaction();
                try {
                    AppDataBase.getInstance().shopCartDao().insertAll(shopCarts);
                    AppDataBase.getInstance().setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    AppDataBase.getInstance().endTransaction();
                }
                return null;
            }
        }.execute();
    }

    public void deleteShopCart(final ShopCart... shopCarts) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                AppDataBase.getInstance().beginTransaction();
                try {
                    AppDataBase.getInstance().shopCartDao().deleteAll(shopCarts);
                    AppDataBase.getInstance().setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    AppDataBase.getInstance().endTransaction();
                }
                return null;
            }
        }.execute();
    }
}
