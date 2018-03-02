package com.magicbeans.xgate.data.db.manager;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.shopcart.ShopCart;
import com.magicbeans.xgate.data.db.AppDataBase;
import com.magicbeans.xgate.data.db.entity.ShopCartTable;

import org.greenrobot.eventbus.EventBus;

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

    public LiveData<List<ShopCart>> queryByIdBeans(final String... ids) {
        final MutableLiveData<List<ShopCart>> beansLiveData = new MediatorLiveData<>();
        int[] idsInt = new int[ids.length];
        for (int i = 0; i < ids.length; i++) {
            idsInt[i] = Integer.parseInt(ids[i]);
        }
        LiveData<List<ShopCartTable>> tablesLiveData = queryById(idsInt);
        tablesLiveData.observeForever(new Observer<List<ShopCartTable>>() {
            @Override
            public void onChanged(@Nullable List<ShopCartTable> tables) {
                beansLiveData.setValue(ShopCartTable.wraps2beans(tables));
            }
        });
        return beansLiveData;
    }

    public LiveData<Integer> insertOrUpdate(final ShopCart shopCart) {
        final MutableLiveData<Integer> resultsLiveData = new MediatorLiveData<>();
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                //先查询出是否已经存在该记录
                List<ShopCartTable> querryTables = AppDataBase.getInstance().shopCartTableDao().queryById(new int[]{Integer.parseInt(shopCart.getProdID())});
                if (StrUtil.isEmpty(querryTables)) {
                    //不存在则插入
                    AppDataBase.getInstance().shopCartTableDao().insert(new ShopCartTable(shopCart));
                } else {
                    //存在则更新数量
                    ShopCart exs = querryTables.get(0).getBean();
                    exs.setQty(exs.getQty() + 1);
                    AppDataBase.getInstance().shopCartTableDao().update(new ShopCartTable(exs));
                }
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

    public LiveData<Integer> insert(final ShopCart... beans) {
        return insert(ShopCartTable.beans2wraps(beans));
    }

    public LiveData<Integer> update(final ShopCart... beans) {
        return update(ShopCartTable.beans2wraps(beans));
    }

    public LiveData<Integer> delete(final ShopCart... beans) {
        return delete(ShopCartTable.beans2wraps(beans));
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
