package com.magicbeans.xgate.ui.controller;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

public class ShopCartContentController extends BaseController<FragmentShopbagBinding> implements OnRecycleItemClickListener, View.OnClickListener {

    private RecycleAdapterHomeShopbag adapter;

    public ShopCartContentController(FragmentShopbagBinding binding) {
        super(binding);
        initCtrl();
        initData(true);
    }

    private void initCtrl() {
        binding.includeBottombar.btnGo.setOnClickListener(this);
        binding.includeBottombar.textShopbagCheckall.setOnClickListener(this);
        binding.includeBottombar.btnShare.setOnClickListener(this);
        binding.includeBottombar.btnFavo.setOnClickListener(this);
        binding.includeBottombar.btnDel.setOnClickListener(this);
        adapter = new RecycleAdapterHomeShopbag(context);
        adapter.setLoadingLayout(binding.loadingLayout);
        adapter.setOnItemClickListener(this);
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
            public void onSelectChange() {
                setPriceAndCount();
            }
        });
    }

    private void initData(final boolean isShowLoading) {
        if (isShowLoading) showLoadingDialog();
//        LiveData<List<ShopCart>> shopCartsLiveData = NetShopCartHelper.getInstance().netGetShopCartList();
        LiveData<List<ShopCart>> shopCartsLiveData = DataShopCartHelper.getInstance().getShopCartList();
        shopCartsLiveData.observeForever(new Observer<List<ShopCart>>() {
            @Override
            public void onChanged(@Nullable List<ShopCart> shopCarts) {
                adapter.notifyDataSetChanged(shopCarts);
                binding.spring.onFinishFreshAndLoad();
                //计算价格
                setPriceAndCount();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_shopbag_checkall:
                if (adapter.isSelectAll()) {
                    binding.includeBottombar.textShopbagCheckall.setSelected(false);
                    adapter.selectAll(false);
                } else {
                    binding.includeBottombar.textShopbagCheckall.setSelected(true);
                    adapter.selectAll(true);
                }
                break;
            case R.id.btn_go: {
                final List<ShopCart> selectBeans = adapter.getSelectBeans();
                if (!StrUtil.isEmpty(selectBeans)) {
                    OrderAddActivity.start(context, selectBeans);
                } else {
                    ToastUtil.showToastShort("请先选择要购买的商品");
                }
                break;
            }
            case R.id.btn_share:
                new ShareDialog(context).show();
                break;
            case R.id.btn_favo:
                ToastUtil.showToastShort("开发中");
                break;
            case R.id.btn_del: {
                final List<ShopCart> selectBeans = adapter.getSelectBeans();
                if (!StrUtil.isEmpty(selectBeans)) {
                    DialogSure.showDialog(context, "确定要删除这些商品？", new DialogSure.CallBack() {
                        @Override
                        public void onSure() {
                            List<ShopCart> results = adapter.getResults();
                            for (ShopCart shopCart : results) {
                                if (shopCart.isSelect()) {
                                    shopCart.setQty(0);
                                }
                            }
//                            NetShopCartHelper.getInstance().netBatchDeleteShopCart(context, selectBeans);
                            DataShopCartHelper.getInstance().batchDeleteShopCart(context, results);
                        }
                    });
                } else {
                    ToastUtil.showToastShort("请先选择要删除的商品");
                }
                break;
            }
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
                setPriceAndCount();
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
        if (isEdit) {
            binding.includeBottombar.layEdit.setVisibility(View.VISIBLE);
            binding.includeBottombar.layUnedit.setVisibility(View.GONE);
        } else {
            binding.includeBottombar.layEdit.setVisibility(View.GONE);
            binding.includeBottombar.layUnedit.setVisibility(View.VISIBLE);
        }
    }

    public void setPriceAndCount() {
        List<ShopCart> selectProduct2s = adapter.getSelectBeans();
        binding.includeBottombar.btnGo.setText("去结算(" + calcuCount(selectProduct2s) + ")");
        float totalPrice = calcuPrice(selectProduct2s);
        binding.includeBottombar.textShopbagPriceall.setText("合计：￥" + totalPrice);
    }

    //计算商品总价，这个方法作为工具类方法APP通用，目前的逻辑只是把商品价格全加起来
    public static float calcuPrice(List<ShopCart> shopCarts) {
        float totalPrice = 0;
        for (ShopCart shopCart : shopCarts) {
            totalPrice += shopCart.getPriceFloat() * shopCart.getQty();
        }
        return totalPrice;
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
        return binding.includeBottombar.layEdit.getVisibility() == View.VISIBLE;
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
