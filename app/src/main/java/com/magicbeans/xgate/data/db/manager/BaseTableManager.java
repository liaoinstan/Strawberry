package com.magicbeans.xgate.data.db.manager;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.magicbeans.xgate.data.db.AppDataBase;
import com.magicbeans.xgate.data.db.dao.BaseTableDao;
import com.magicbeans.xgate.data.db.dao.HistoryTableDao;
import com.magicbeans.xgate.data.db.dao.ShopCartTableDao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

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
        final MutableLiveData<List<T>> resultsLiveData = new MediatorLiveData<>();
        new AsyncTask<Void, Void, List<T>>() {
            @Override
            protected List<T> doInBackground(Void... voids) {
                List<T> results = new ArrayList<>();
                AppDataBase.getInstance().beginTransaction();
                try {
                    results.addAll(baseTableDao.querryAll());
                    AppDataBase.getInstance().setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    AppDataBase.getInstance().endTransaction();
                }
                return results;
            }

            @Override
            protected void onPostExecute(List<T> tables) {
                super.onPostExecute(tables);
                resultsLiveData.setValue(tables);
            }
        }.execute();
        return resultsLiveData;
    }

    @Override
    public void insert(final T... tables) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                AppDataBase.getInstance().beginTransaction();
                try {
                    baseTableDao.insert(tables);
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
    public void update(final T... tables) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                AppDataBase.getInstance().beginTransaction();
                try {
                    baseTableDao.update(tables);
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
    public void delete(final T... tables) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                AppDataBase.getInstance().beginTransaction();
                try {
                    baseTableDao.delete(tables);
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
    public void deleteAll() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                AppDataBase.getInstance().beginTransaction();
                try {
                    baseTableDao.deleteAll();
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
