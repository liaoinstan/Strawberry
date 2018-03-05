package com.magicbeans.xgate.ui.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.utils.FocusUtil;
import com.ins.common.utils.L;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.shopcart.ShopCart;
import com.magicbeans.xgate.data.db.manager.ShopCartTableManager;
import com.magicbeans.xgate.databinding.FragmentShopbagBinding;
import com.magicbeans.xgate.ui.activity.ShopcartActivity;
import com.magicbeans.xgate.ui.base.BaseFragment;
import com.magicbeans.xgate.ui.controller.CommonRecommendController;
import com.magicbeans.xgate.ui.controller.ShopCartContentController;

import java.util.List;


/**
 * Created by liaoinstan
 */
public class ShopBagFragment extends BaseFragment {

    private int position;
    private View rootView;

    private FragmentShopbagBinding binding;

    private ShopCartContentController shopCartContentController;
    private CommonRecommendController commonRecommendController;

    public static Fragment newInstance(int position) {
        ShopBagFragment fragment = new ShopBagFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCommonEvent(EventBean event) {
        switch (event.getEvent()) {
            case EventBean.EVENT_IN_SHOPCART:
                //用户进入购物车，会进入这个case，这里做一些特殊操作
                break;
            case EventBean.EVENT_REFRESH_SHOPCART:
                //收到刷新购物车的消息（从本地数据库获取）
                shopCartContentController.refreshData();
                break;
            case EventBean.EVENT_REFRESH_SHOPCART_REMOTE:
                //收到刷新购物车的消息（远程服务器获取数据）
                shopCartContentController.refreshRemoteData();
                break;
            case EventBean.EVENT_LOGIN:
                //用户登录，检查本地是否存在离线状态下添加的商品，若存在批量添加到服务器数据库
                shopCartContentController.batchAddOfflineData();
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.position = getArguments().getInt("position");
        registEventBus();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopbag, container, false);
        rootView = binding.getRoot();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBase();
        initView();
        initCtrl();
        initData();
        FocusUtil.focusToTop(binding.getRoot());
    }

    private void initBase() {
        //该fragment有两处使用，一处是首页的购物车tab，一处是ShopcartActivity，两处业务逻辑完全一样
        //特殊处理，如果该fragment在ShopcartActivity中使用，则需要显示左上角的返回按钮
        if (getActivity() instanceof ShopcartActivity) {
            setToolbar(true);
        }
        shopCartContentController = new ShopCartContentController(binding);
        commonRecommendController = new CommonRecommendController(binding.includeRecomend, 6);
    }

    private void initView() {
    }

    private void initData() {
    }

    private void initCtrl() {
        binding.btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shopCartContentController.isEdit()) {
                    shopCartContentController.setEditModel(false);
                    binding.btnRight.setText("编辑");
                } else {
                    shopCartContentController.setEditModel(true);
                    binding.btnRight.setText("完成");
                }
            }
        });
    }
}
