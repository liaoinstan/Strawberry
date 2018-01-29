package com.magicbeans.xgate.data.db.manager;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.data.db.AppDataBase;
import com.magicbeans.xgate.data.db.dao.BaseTableDao;
import com.magicbeans.xgate.data.db.dao.HistoryTableDao;
import com.magicbeans.xgate.data.db.dao.ShopCartTableDao;
import com.magicbeans.xgate.data.db.entity.HistoryTable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * BaseTableManager
 */

public class BaseTableManager<T> implements BaseTableManagerInterface<T> {

    private BaseTableDao baseTableDao;

    public BaseTableManager() {
        initBaseTableDao();
    }

    //利用反射为baseTableDao赋相应的实体，要求AppDataBase的获取dao方法shopCartTableDao()与实体类命名保持一致ShopCartTable ：shopCartTableDao() -> ShopCartTable
    //混淆的时候注意不要混淆该类
    private void initBaseTableDao() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class clazz = (Class) type.getActualTypeArguments()[0];
        String tName = clazz.getSimpleName();  //tName=HistoryTable
        String tDao = tName.substring(0, 1).toLowerCase() + tName.substring(1) + "Dao"; // historyTableDao
        try {
            AppDataBase instance = AppDataBase.getInstance();
            Method method = AppDataBase.class.getMethod(tDao);
            baseTableDao = (BaseTableDao) method.invoke(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public LiveData<List<T>> queryAll() {
//        这是使用AsyncTask的写法，由于使用匿名AsyncTask会造成内层泄露导致activity无法释放，重新采用rxjava进行重构代码，该代码注释保留
//        final MutableLiveData<List<T>> resultsLiveData = new MediatorLiveData<>();
//        new AsyncTask<Void, Void, List<T>>() {
//            @Override
//            protected List<T> doInBackground(Void... voids) {
//                List<T> results = new ArrayList<>();
//                AppDataBase.getInstance().beginTransaction();
//                try {
//                    results.addAll(baseTableDao.querryAll());
//                    AppDataBase.getInstance().setTransactionSuccessful();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    AppDataBase.getInstance().endTransaction();
//                }
//                return results;
//            }
//
//            @Override
//            protected void onPostExecute(List<T> tables) {
//                super.onPostExecute(tables);
//                resultsLiveData.setValue(tables);
//            }
//        }.execute();
//        return resultsLiveData;

//        rxjava写法，其他地方一律使用rxjava进行操作，不再保留AsyncTask的注释
        final MutableLiveData<List<T>> resultsLiveData = new MediatorLiveData<>();
        Observable.create(new ObservableOnSubscribe<List<T>>() {
            @Override
            public void subscribe(ObservableEmitter<List<T>> e) throws Exception {
                List<T> results = baseTableDao.querryAll();
                e.onNext(results);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<T>>() {
                    @Override
                    public void accept(List<T> results) throws Exception {
                        resultsLiveData.setValue(results);
                    }
                });
        return resultsLiveData;
    }

    @Override
    public LiveData<Integer> insert(final T... tables) {
        final MutableLiveData<Integer> resultsLiveData = new MediatorLiveData<>();
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                baseTableDao.insert(tables);
                e.onNext(tables.length);
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

    @Override
    public LiveData<Integer> update(final T... tables) {
        final MutableLiveData<Integer> resultsLiveData = new MediatorLiveData<>();
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                baseTableDao.update(tables);
                e.onNext(tables.length);
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

    @Override
    public LiveData<Integer> delete(final T... tables) {
        final MutableLiveData<Integer> resultsLiveData = new MediatorLiveData<>();
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                baseTableDao.delete(tables);
                e.onNext(tables.length);
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

    @Override
    public LiveData<Integer> deleteAll() {
        final MutableLiveData<Integer> resultsLiveData = new MediatorLiveData<>();
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                baseTableDao.deleteAll();
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
}
