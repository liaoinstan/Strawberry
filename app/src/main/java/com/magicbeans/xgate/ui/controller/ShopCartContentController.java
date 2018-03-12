package com.magicbeans.xgate.ui.controller;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;

import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.L;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.shopcart.ShopCart;
import com.magicbeans.xgate.bean.shopcart.ShopCartInfo;
import com.magicbeans.xgate.data.db.manager.ShopCartTableManager;
import com.magicbeans.xgate.databinding.FragmentShopbagBinding;
import com.magicbeans.xgate.helper.DataShopCartHelper;
import com.magicbeans.xgate.net.nethelper.NetShopCartHelper;
import com.magicbeans.xgate.sharesdk.ShareDialog;
import com.magicbeans.xgate.ui.activity.OrderAddActivity;
import com.magicbeans.xgate.ui.activity.ProductDetailActivity;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterHomeShopbag;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ShopCartContentController extends BaseController<FragmentShopbagBinding> implements OnRecycleItemClickListener {

    //子controller，用于分担ShopCartContentController的业务逻辑
    private ShopCartBottomBarController bottomBarController;
    private RecycleAdapterHomeShopbag adapter;

    public ShopCartContentController(FragmentShopbagBinding binding) {
        super(binding);
        initBase();
        initCtrl();
        initData(true);
    }

    private void initBase() {
        bottomBarController = new ShopCartBottomBarController(binding.includeBottombar);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterHomeShopbag(context);
        adapter.setLoadingLayout(binding.loadingLayout);
        adapter.setTextSelectAll(binding.includeBottombar.textShopbagCheckall);
        adapter.setOnItemClickListener(this);
        bottomBarController.setShopCartAdapter(adapter);
        binding.recycle.setNestedScrollingEnabled(false);
        binding.recycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.recycle.addItemDecoration(new ItemDecorationDivider(context, LinearLayoutManager.VERTICAL));
        binding.recycle.setAdapter(adapter);
        binding.spring.setHeader(new AliHeader(context, false));
        binding.spring.setFooter(new AliFooter(context, false));
        binding.spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                initData(false);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.spring.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
        adapter.setOnSelectChangeListenner(new RecycleAdapterHomeShopbag.OnSelectChangeListenner() {
            @Override
            public void onSelectChange(boolean changePrice) {
                if (changePrice) {
                    bottomBarController.setPriceAndCount();
                } else {
                    bottomBarController.setCount();
                }
            }
        });
    }

    private void initData(final boolean isShowLoading) {
        if (isShowLoading) showLoadingDialog();
        LiveData<List<ShopCart>> shopCartsLiveData = DataShopCartHelper.getInstance().getShopCartList();
        shopCartsLiveData.observeForever(new Observer<List<ShopCart>>() {
            @Override
            public void onChanged(@Nullable List<ShopCart> shopCarts) {
                adapter.notifyDataSetChanged(shopCarts);
                binding.spring.onFinishFreshAndLoad();
                //计算价格
                bottomBarController.setPriceAndCount();
                if (isShowLoading) dismissLoadingDialog();
            }
        });
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
        if (isEdit()) {
            //编辑模式下点击商品，改变选择状态
            adapter.selectItem(viewHolder.getLayoutPosition());
        } else {
            //非编辑模式下点击商品，进入详情
            ShopCart shopCart = adapter.getResults().get(viewHolder.getLayoutPosition());
            ProductDetailActivity.start(context, shopCart.getProdID());
        }
    }

    //#################  对外方法 ##################

    //刷新购物车（重新加载本地数据库列表）
    public void refreshData() {
        LiveData<List<ShopCart>> product2sLiveData = ShopCartTableManager.getInstance().queryAllBeans();
        product2sLiveData.observeForever(new Observer<List<ShopCart>>() {
            @Override
            public void onChanged(@Nullable List<ShopCart> shopCarts) {
                adapter.notifyDataSetChanged(shopCarts);
                binding.spring.onFinishFreshAndLoad();
                //计算价格
                bottomBarController.setPriceAndCount();
            }
        });
    }

    //刷新购物车（请求服务器最新购物车数据）
    public void refreshRemoteData() {
        initData(false);
    }

    //刷新购物车（请求服务器最新购物车数据）
    public void batchAddOfflineData() {
        LiveData<List<ShopCart>> listLiveData = ShopCartTableManager.getInstance().queryAllOfflineBeans();
        listLiveData.observeForever(new Observer<List<ShopCart>>() {
            @Override
            public void onChanged(@Nullable List<ShopCart> shopCarts) {
                if (shopCarts != null) {
                    //如果存在offline数据，则同步到该用户服务器数据库上
                    NetShopCartHelper.getInstance().netBatchAddShopCart(shopCarts);
                } else {
                    //不存在则刷新远程数据
                    refreshRemoteData();
                }
            }
        });
    }

    //设置当前UI状态为编辑状态，或者普通状态
    public void setEditModel(boolean isEdit) {
        bottomBarController.setEditModel(isEdit);
    }

    //计算商品数量
    public static int calcuCount(List<ShopCart> shopCarts) {
        int count = 0;
        for (ShopCart shopCart : shopCarts) {
            count += shopCart.getQty();
        }
        return count;
    }

    public boolean isEdit() {
        return bottomBarController.isEdit();
    }

    public final void showLoadingDialog() {
        //只在ShopcartActivity中使用才显示加载进度条
        if (context instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) context).showLoadingDialog();
        }
    }

    public final void dismissLoadingDialog() {
        if (context instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) context).dismissLoadingDialog();
        }
    }
}
