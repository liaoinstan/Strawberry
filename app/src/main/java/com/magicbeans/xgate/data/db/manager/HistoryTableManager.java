package com.magicbeans.xgate.data.db.manager;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.data.db.AppDataBase;
import com.magicbeans.xgate.data.db.entity.HistoryTable;

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
        Observable.create(new ObservableOnSubscribe<List<HistoryTable>>() {
            @Override
            public void subscribe(ObservableEmitter<List<HistoryTable>> e) throws Exception {
                List<HistoryTable> results = AppDataBase.getInstance().historyTableDao().querryLimit(page, count);
                e.onNext(results);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<HistoryTable>>() {
                    @Override
                    public void accept(List<HistoryTable> results) throws Exception {
                        resultsLiveData.setValue(HistoryTable.wraps2beans(results));
                    }
                });
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

    public MutableLiveData<Integer> count() {
        final MutableLiveData<Integer> resultsLiveData = new MediatorLiveData<>();
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                int count = AppDataBase.getInstance().historyTableDao().count();
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
