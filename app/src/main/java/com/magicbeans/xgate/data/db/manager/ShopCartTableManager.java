package com.magicbeans.xgate.data.db.manager;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.bean.shopcart.ShopCart;
import com.magicbeans.xgate.data.db.AppDataBase;
import com.magicbeans.xgate.data.db.entity.ShopCartTable;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * ShopCartTableManager.java
 * <p>
 */

public class ShopCartTableManager extends BaseTableManager<ShopCartTable> {

    private static ShopCartTableManager INSTANCE;

    private ShopCartTableManager() {
    }

    public static ShopCartTableManager getInstance() {
        if (INSTANCE == null) {
            synchronized (ShopCartTableManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ShopCartTableManager();
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<List<ShopCart>> queryAllBeans() {
        final MutableLiveData<List<ShopCart>> beansLiveData = new MediatorLiveData<>();
        LiveData<List<ShopCartTable>> tablesLiveData = queryAll();
        tablesLiveData.observeForever(new Observer<List<ShopCartTable>>() {
            @Override
            public void onChanged(@Nullable List<ShopCartTable> tables) {
                beansLiveData.setValue(ShopCartTable.wraps2beans(tables));
            }
        });
        return beansLiveData;
    }

    public void insert(final ShopCart... beans) {
        insert(ShopCartTable.beans2wraps(beans));
    }

    public void update(final ShopCart... beans) {
        update(ShopCartTable.beans2wraps(beans));
    }

    public void delete(final ShopCart... beans) {
        delete(ShopCartTable.beans2wraps(beans));
    }

    //同步数据库表
    //TODO:旧数据更新
    public LiveData<Integer> deleteAndUpdate(final ShopCart... datas) {
        final MutableLiveData<Integer> resultsLiveData = new MediatorLiveData<>();
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                //检查数据异同，把数据库数据和列表数据对比，把旧数据合并到新数据中
                List<ShopCartTable> allTables = AppDataBase.getInstance().shopCartTableDao().querryAll();
                if (!StrUtil.isEmpty(allTables)) {
                    for (ShopCartTable table : allTables) {
                        ShopCart bean = table.getBean();
                        for (ShopCart data : datas) {
                            if (data.getProdID().equals(bean.getProdID())) {
                                data.setSelect(bean.isSelect());
                            }
                        }
                    }
                }
                //删除旧数据
                AppDataBase.getInstance().shopCartTableDao().deleteAll();
                //插入新数据
                AppDataBase.getInstance().shopCartTableDao().insert(ShopCartTable.beans2wraps(datas));
                e.onNext(1);
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
