package com.magicbeans.xgate.ui.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.FocusUtil;
import com.ins.common.utils.StatusBarTextUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.common.TestBean;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.data.db.AppDatabaseManager;
import com.magicbeans.xgate.data.db.entity.ShopCart;
import com.magicbeans.xgate.databinding.FragmentShopbagBinding;
import com.magicbeans.xgate.ui.activity.OrderAddActivity;
import com.magicbeans.xgate.ui.activity.ProductDetailActivity;
import com.magicbeans.xgate.ui.activity.ShopcartActivity;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterHomeShopbag;
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

    //TODO:这个方法里设置StatusBar字体颜色会导致UI出现无法及时响应的异常，不要这样做，在CateFragment中这样做会导致子fragment无法加载的异常
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            if (getActivity() != null)
//                StatusBarTextUtil.transBarBackground(getActivity(), ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
//        }
//    }


    @Override
    public void onCommonEvent(EventBean event) {
        switch (event.getEvent()) {
            case EventBean.EVENT_IN_SHOPCART:
                //用户进入购物车，会进入这个case，这里做一些特殊操作
                break;
            case EventBean.EVENT_REFRESH_SHOPCART:
                //收到刷新购物车的消息
                shopCartContentController.refreshData();
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
        if (getActivity() instanceof ShopcartActivity){
            setToolbar(true);
        }
        shopCartContentController = new ShopCartContentController(binding);
        commonRecommendController = new CommonRecommendController(binding.includeRecomend, 4);
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
