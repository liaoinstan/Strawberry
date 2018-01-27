package com.magicbeans.xgate.data.db.manager;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.data.db.AppDataBase;
import com.magicbeans.xgate.data.db.entity.HistoryTable;

import java.util.ArrayList;
import java.util.List;

/**
 * ShopcartTableManager.java
 * <p>
 */

public class HistoryTableManager extends BaseTableManager<HistoryTable> {

    private static HistoryTableManager INSTANCE;

    private HistoryTableManager() {
        super();
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

    public LiveData<List<Product>> querryLimit(final int page, final int count) {
        final MutableLiveData<List<Product>> resultsLiveData = new MediatorLiveData<>();
        new AsyncTask<Void, Void, List<HistoryTable>>() {
            @Override
            protected List<HistoryTable> doInBackground(Void... voids) {
                List<HistoryTable> results = new ArrayList<>();
                AppDataBase.getInstance().beginTransaction();
                try {
                    results.addAll(AppDataBase.getInstance().historyTableDao().querryLimit(page, count));
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

    public LiveData<List<Product>> queryAllBeans() {
        final MutableLiveData<List<Product>> beansLiveData = new MediatorLiveData<>();
        LiveData<List<HistoryTable>> tablesLiveData = queryAll();
        tablesLiveData.observeForever(new Observer<List<HistoryTable>>() {
            @Override
            public void onChanged(@Nullable List<HistoryTable> tables) {
                beansLiveData.setValue(HistoryTable.wraps2beans(tables));
            }
        });
        return beansLiveData;
    }

    public void insert(final Product... beans) {
        insert(HistoryTable.beans2wraps(beans));
    }

    public void update(final Product... beans) {
        update(HistoryTable.beans2wraps(beans));
    }

    public void delete(final Product... beans) {
        delete(HistoryTable.beans2wraps(beans));
    }
}
