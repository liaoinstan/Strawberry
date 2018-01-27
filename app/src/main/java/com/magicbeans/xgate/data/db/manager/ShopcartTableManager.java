package com.magicbeans.xgate.data.db.manager;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.ins.common.helper.SelectHelper;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.data.db.AppDataBase;
import com.magicbeans.xgate.data.db.entity.HistoryTable;
import com.magicbeans.xgate.data.db.entity.ShopCartTable;

import java.util.ArrayList;
import java.util.List;

/**
 * ShopcartTableManager.java
 * <p>
 */

public class ShopcartTableManager extends BaseTableManager<ShopCartTable> {

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

    public LiveData<List<Product2>> queryAllBeans() {
        final MutableLiveData<List<Product2>> beansLiveData = new MediatorLiveData<>();
        LiveData<List<ShopCartTable>> tablesLiveData = queryAll();
        tablesLiveData.observeForever(new Observer<List<ShopCartTable>>() {
            @Override
            public void onChanged(@Nullable List<ShopCartTable> tables) {
                beansLiveData.setValue(ShopCartTable.wraps2beans(tables));
            }
        });
        return beansLiveData;
    }

    public void insert(final Product2... beans) {
        insert(ShopCartTable.beans2wraps(beans));
    }

    public void update(final Product2... beans) {
        update(ShopCartTable.beans2wraps(beans));
    }

    public void delete(final Product2... beans) {
        delete(ShopCartTable.beans2wraps(beans));
    }

    public MutableLiveData<Integer> count() {
        final MutableLiveData<Integer> resultsLiveData = new MediatorLiveData<>();
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                int count = 0;
                AppDataBase.getInstance().beginTransaction();
                try {
                    count = AppDataBase.getInstance().shopCartTableDao().count();
                    AppDataBase.getInstance().setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    AppDataBase.getInstance().endTransaction();
                }
                return count;
            }

            @Override
            protected void onPostExecute(Integer count) {
                super.onPostExecute(count);
                resultsLiveData.setValue(count);
            }
        }.execute();
        return resultsLiveData;
    }
}
