package com.magicbeans.xgate.data.db.manager;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.data.db.AppDataBase;
import com.magicbeans.xgate.data.db.AppDataBaseInterface;
import com.magicbeans.xgate.data.db.entity.HistoryTable;

import java.util.ArrayList;
import java.util.List;

/**
 * ShopcartTableManager.java
 * <p>
 * Created by lijiankun on 17/7/5.
 */

public class HistoryTableManager implements AppDataBaseInterface<Product> {

    private static HistoryTableManager INSTANCE;

    private HistoryTableManager() {
    }

    public static HistoryTableManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HistoryTableManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HistoryTableManager();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<List<Product>> queryAll() {
        final MutableLiveData<List<Product>> resultsLiveData = new MediatorLiveData<>();
        new AsyncTask<Void, Void, List<HistoryTable>>() {
            @Override
            protected List<HistoryTable> doInBackground(Void... voids) {
                List<HistoryTable> results = new ArrayList<>();
                AppDataBase.getInstance().beginTransaction();
                try {
                    results.addAll(AppDataBase.getInstance().historyTableDao().querryAll());
                    AppDataBase.getInstance().setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    AppDataBase.getInstance().endTransaction();
                }
                return results;
            }

            @Override
            protected void onPostExecute(List<HistoryTable> shopCartTables) {
                super.onPostExecute(shopCartTables);
                resultsLiveData.setValue(HistoryTable.wraps2beans(shopCartTables));
            }
        }.execute();
        return resultsLiveData;
    }

    @Override
    public void insert(final Product... products) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                AppDataBase.getInstance().beginTransaction();
                try {
                    AppDataBase.getInstance().historyTableDao().insertAll(HistoryTable.beans2wraps(products));
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
    public void update(final Product... products) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                AppDataBase.getInstance().beginTransaction();
                try {
                    AppDataBase.getInstance().historyTableDao().updateAll(HistoryTable.beans2wraps(products));
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
    public void delete(final Product... products) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                AppDataBase.getInstance().beginTransaction();
                try {
                    AppDataBase.getInstance().historyTableDao().deleteAll(HistoryTable.beans2wraps(products));
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
