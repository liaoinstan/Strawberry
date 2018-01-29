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

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                int count = AppDataBase.getInstance().shopCartTableDao().count();
                e.onNext(count);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer results) throws Exception {
                        resultsLiveData.setValue(results);
                    }
                });
        return resultsLiveData;
    }
}
