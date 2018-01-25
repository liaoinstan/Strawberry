package com.magicbeans.xgate.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.data.db.entity.ShopCart;
import com.magicbeans.xgate.data.db.entity.ShopCartTable;

import java.util.ArrayList;
import java.util.List;

/**
 * AppDatabaseManager.java
 * <p>
 * Created by lijiankun on 17/7/5.
 */

public class AppDatabaseManager {

    private static AppDatabaseManager INSTANCE;

    private AppDatabaseManager() {
    }

    public static AppDatabaseManager getInstance() {
        if (INSTANCE == null) {
            synchronized (AppDatabaseManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppDatabaseManager();
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<List<ShopCart>> queryShopCarts() {
        final MutableLiveData<List<ShopCart>> resultsLiveData = new MediatorLiveData<>();
        new AsyncTask<Void, Void, List<ShopCart>>() {
            @Override
            protected List<ShopCart> doInBackground(Void... voids) {
                List<ShopCart> results = new ArrayList<>();
                AppDataBase.getInstance().beginTransaction();
                try {
                    results.addAll(AppDataBase.getInstance().shopCartDao().querryAll());
                    AppDataBase.getInstance().setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    AppDataBase.getInstance().endTransaction();
                }
                return results;
            }

            @Override
            protected void onPostExecute(List<ShopCart> shopCarts) {
                super.onPostExecute(shopCarts);
                resultsLiveData.setValue(shopCarts);
            }
        }.execute();
        return resultsLiveData;
    }

    public void insertShopCart(final ShopCart... shopCarts) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                AppDataBase.getInstance().beginTransaction();
                try {
                    AppDataBase.getInstance().shopCartDao().insertAll(shopCarts);
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

    public void deleteShopCart(final ShopCart... shopCarts) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                AppDataBase.getInstance().beginTransaction();
                try {
                    AppDataBase.getInstance().shopCartDao().deleteAll(shopCarts);
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

    //////////////////


    public LiveData<List<Product2>> queryShopCartTables() {
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

    public void insertShopCartTable(final Product2... product2s) {
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

    public void updateShopCartTable(final Product2... product2s) {
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

    public void deleteShopCartTable(final Product2... product2s) {
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
