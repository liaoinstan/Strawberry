package com.magicbeans.xgate.helper;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.bean.shopcart.ShopCart;
import com.magicbeans.xgate.bean.shopcart.ShopCartWrap;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.data.db.manager.ShopCartTableManager;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STFormatCallback;
import com.magicbeans.xgate.net.nethelper.NetShopCartHelper;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

/**
 * Created by liaoinstan on 2018/1/22.
 * 管理购物车的离线操作和在线请求
 * 这个类负责管理购物车相关接口的在线请求{@link NetShopCartHelper}，和本地数据库{@link ShopCartTableManager}
 * 在已登录状态，所有购物车的操作都需要请求接口，在未登陆状态，所有购物车的操作全部在本地数据库进行
 * 用户下次登陆后如果本地数据库有商品，要通过批量上传接口同步到服务器
 *
 * 以下的几个方法分别在登陆和未登录状态下进行了2种不同的调用
 */

public class DataShopCartHelper {
    private static DataShopCartHelper instance;

    private DataShopCartHelper() {
    }

    public static synchronized DataShopCartHelper getInstance() {
        if (instance == null) instance = new DataShopCartHelper();
        return instance;
    }

    /////////////////////////////////////

    //添加购物车，如果已登录则请求接口，未登陆则添加本地数据库，用户下次登陆时要把本地数据库同步到服务器
    public void addShopCart(final Product product) {
        if (!AppHelper.User.isLogin()) {
            //未登陆状态，添加到本地数据库
            LiveData<Integer> liveData = ShopCartTableManager.getInstance().insertOrUpdate(ShopCart.transByProduct(product).setOffLineFlag());
            postEvent(liveData);
        } else {
            //已登录，则请求接口添加购物车商品
            NetShopCartHelper.getInstance().netAddShopCart(product.getProdID(), 1);
        }
    }

    public void addShopCart(Product2 product2) {
        if (!AppHelper.User.isLogin()) {
            LiveData<Integer> liveData = ShopCartTableManager.getInstance().insertOrUpdate(ShopCart.transByProduct2(product2).setOffLineFlag());
            postEvent(liveData);
        } else {
            NetShopCartHelper.getInstance().netAddShopCart(product2.getProdID(), product2.getCount());
        }
    }

    public void removeShopCart(Context context, ShopCart shopCart) {
        if (!AppHelper.User.isLogin()) {
            LiveData<Integer> liveData = ShopCartTableManager.getInstance().delete(shopCart);
            postEvent(liveData);
        } else {
            NetShopCartHelper.getInstance().netRemoveShopCart(context, shopCart.getProdID());
        }
    }

    public void updateShopCart(ShopCart shopCart) {
        if (!AppHelper.User.isLogin()) {
            LiveData<Integer> liveData = ShopCartTableManager.getInstance().update(shopCart);
            postEvent(liveData);
        } else {
            NetShopCartHelper.getInstance().netUpdateShopCart(shopCart.getProdID(), shopCart.getQty());
        }
    }


    public void batchDeleteShopCart(Context context, List<ShopCart> shopCarts) {
        if (!AppHelper.User.isLogin()) {
            LiveData<Integer> liveData = ShopCartTableManager.getInstance().delete(shopCarts.toArray(new ShopCart[]{}));
            postEvent(liveData);
        } else {
            NetShopCartHelper.getInstance().netBatchDeleteShopCart(context, shopCarts);
        }
    }

    private void postEvent(LiveData<Integer> liveData) {
        liveData.observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                EventBus.getDefault().post(new EventBean(EventBean.EVENT_REFRESH_SHOPCART));
            }
        });
    }

    public LiveData<List<ShopCart>> getShopCartList() {
        if (!AppHelper.User.isLogin()) {
            return ShopCartTableManager.getInstance().queryAllBeans();
        } else {
            return NetShopCartHelper.getInstance().netGetShopCartList();
        }
    }
}
