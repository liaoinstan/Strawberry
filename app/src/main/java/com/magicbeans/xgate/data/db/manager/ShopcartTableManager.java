package com.magicbeans.xgate.data.db.manager;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.data.db.AppDataBase;
import com.magicbeans.xgate.data.db.AppDataBaseInterface;
import com.magicbeans.xgate.data.db.entity.ShopCartTable;

import java.util.ArrayList;
import java.util.List;

/**
 * ShopcartTableManager.java
 * <p>
 * Created by lijiankun on 17/7/5.
 */

public class ShopcartTableManager implements AppDataBaseInterface<Product2> {

    private static ShopcartTableManager INSTANCE;

    private ShopcartTableManager() {
    }

    public static ShopcartTableManager getInstance() {
        if (INSTANCE == null) {
            synchronized (ShopcartTableManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ShopcartTableManager();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<List<Product2>> queryAll() {
        final MutableLiveData<List<Product2>> resultsLiveData = new MediatorLiveData<>();
        new AsyncTask<Void, Void, List<ShopCartTable>>() {
            @Override
            protected List<ShopCartTable> doInBackground(Void... voids) {
                List<ShopCartTable> results = new ArrayList<>();
                AppDataBase.getInstance().beginTransaction();
                try {
                    results.addAll(AppDataBase.getInstance().shopCartTableDao().querryAll());
                    AppDataBase.getInstance().setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    AppDataBase.getInstance().endTransaction();
                }
                return results;
            }

            @Override
            protected void onPostExecute(List<ShopCartTable> shopCartTables) {
                super.onPostExecute(shopCartTables);
                resultsLiveData.setValue(ShopCartTable.wraps2contents(shopCartTables));
            }
        }.execute();
        return resultsLiveData;
    }

    @Override
    public void insert(final Product2... product2s) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                AppDataBase.getInstance().beginTransaction();
                try {
                    AppDataBase.getInstance().shopCartTableDao().insertAll(ShopCartTable.contents2wraps(product2s));
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

    @Override
    public void update(final Product2... product2s) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                AppDataBase.getInstance().beginTransaction();
                try {
                    AppDataBase.getInstance().shopCartTableDao().updateAll(ShopCartTable.contents2wraps(product2s));
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

    @Override
    public void delete(final Product2... product2s) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                AppDataBase.getInstance().beginTransaction();
                try {
                    AppDataBase.getInstance().shopCartTableDao().deleteAll(ShopCartTable.contents2wraps(product2s));
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
