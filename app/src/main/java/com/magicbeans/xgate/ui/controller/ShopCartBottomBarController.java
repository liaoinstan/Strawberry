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
import com.ins.common.utils.App;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.shopcart.ShopCart;
import com.magicbeans.xgate.bean.shopcart.ShopCartInfo;
import com.magicbeans.xgate.common.AppData;
import com.magicbeans.xgate.data.db.manager.ShopCartTableManager;
import com.magicbeans.xgate.databinding.FragmentShopbagBinding;
import com.magicbeans.xgate.databinding.LayShopcartBottombarBinding;
import com.magicbeans.xgate.helper.DataShopCartHelper;
import com.magicbeans.xgate.net.nethelper.NetShopCartHelper;
import com.magicbeans.xgate.sharesdk.ShareDialog;
import com.magicbeans.xgate.ui.activity.HomeActivity;
import com.magicbeans.xgate.ui.activity.OrderAddActivity;
import com.magicbeans.xgate.ui.activity.ProductDetailActivity;
import com.magicbeans.xgate.ui.activity.ShopcartActivity;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterHomeShopbag;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 * ShopCartContentController的子controller，用于分担ShopCartContentController的业务逻辑，负责处理bottombar的业务逻辑
 */

public class ShopCartBottomBarController extends BaseController<LayShopcartBottombarBinding> implements View.OnClickListener {

    private ShopCartInfo shopCartInfo;
    private RecycleAdapterHomeShopbag adapter;

    public ShopCartBottomBarController(LayShopcartBottombarBinding binding) {
        super(binding);
        initCtrl();
        initData();
    }

    private void initCtrl() {
        binding.btnGo.setOnClickListener(this);
        binding.textShopbagCheckall.setOnClickListener(this);
        binding.btnShare.setOnClickListener(this);
        binding.btnFavo.setOnClickListener(this);
        binding.btnDel.setOnClickListener(this);
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_shopbag_checkall:
                if (adapter.isSelectAll()) {
                    binding.textShopbagCheckall.setSelected(false);
                    adapter.selectAll(false);
                } else {
                    binding.textShopbagCheckall.setSelected(true);
                    adapter.selectAll(true);
                }
                break;
            case R.id.btn_go: {

                if (AppData.App.getIsDoingCart()){
                    return;
                }
                final List<ShopCart> selectBeans = adapter.getSelectBeans();
                if (!StrUtil.isEmpty(selectBeans)) {
                    OrderAddActivity.start(context, selectBeans, shopCartInfo);
                } else {
                    ToastUtil.showToastShort("请先选择要购买的商品");
                }
                break;
            }
            case R.id.btn_share: {
                List<ShopCart> selectBeans = adapter.getSelectBeans();
                if (!StrUtil.isEmpty(selectBeans)) {
                    new ShareDialog(context).setShareShopCart(selectBeans).show();
                } else {
                    //没有选中商品就把首页分享出去
                    new ShareDialog(context).setShareHome().show();
                }
                break;
            }
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

    //////////////////////////////////

    public void setShopCartAdapter(RecycleAdapterHomeShopbag adapter) {
        this.adapter = adapter;
    }

    //设置当前UI状态为编辑状态，或者普通状态
    public void setEditModel(boolean isEdit) {
        if (isEdit) {
            binding.layEdit.setVisibility(View.VISIBLE);
            binding.layUnedit.setVisibility(View.GONE);
        } else {
            binding.layEdit.setVisibility(View.GONE);
            binding.layUnedit.setVisibility(View.VISIBLE);
        }
    }

    public boolean isEdit() {
        return binding.layEdit.getVisibility() == View.VISIBLE;
    }

    public void setPriceAndCount() {
        setCount();
        setPrice();
    }

    private void setPrice() {
        List<ShopCart> selectShopCarts = adapter.getSelectBeans();
        if (StrUtil.isEmpty(selectShopCarts)) {
            //未选择直接设置为0
            binding.textShopbagPriceall.setText("合计：¥0.00");
        } else {
            LiveData<ShopCartInfo> liveData = NetShopCartHelper.getInstance().netGetShopCartInfo(selectShopCarts);
            showPriceLoading();
            liveData.observeForever(new Observer<ShopCartInfo>() {
                @Override
                public void onChanged(@Nullable ShopCartInfo shopCartInfo) {
                    ShopCartBottomBarController.this.shopCartInfo = shopCartInfo;
                    binding.textShopbagPriceall.setText("合计：" + shopCartInfo.getTotalPrice());
                    EventBean eventBean = new EventBean(EventBean.EVENT_SHOPCART_INFO);
                    eventBean.put("shopCartInfo", shopCartInfo);
                    EventBus.getDefault().post(eventBean);
                    dismissPriceLoading();
                }
            });
        }
    }

    public void setCount() {
        List<ShopCart> selectShopCarts = adapter.getSelectBeans();
        binding.btnGo.setText("去结算(" + ShopCartContentController.calcuCount(selectShopCarts) + ")");
    }

    private void showPriceLoading() {
        binding.progress.setVisibility(View.VISIBLE);
        binding.textShopbagPriceall.setVisibility(View.GONE);
    }

    private void dismissPriceLoading() {
        binding.progress.setVisibility(View.GONE);
        binding.textShopbagPriceall.setVisibility(View.VISIBLE);
    }
}
