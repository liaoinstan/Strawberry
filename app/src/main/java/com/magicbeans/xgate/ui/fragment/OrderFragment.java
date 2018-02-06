package com.magicbeans.xgate.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.Goods;
import com.magicbeans.xgate.bean.Order;
import com.magicbeans.xgate.bean.order.OrderWrap;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.bean.shopcart.ShopCart;
import com.magicbeans.xgate.bean.shopcart.ShopCartWrap;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.databinding.FragmentOrderBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.net.STFormatCallback;
import com.magicbeans.xgate.ui.activity.OrderDetailActivity;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterOrder;
import com.magicbeans.xgate.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by liaoinstan
 */
public class OrderFragment extends BaseFragment implements OnRecycleItemClickListener {

    private int position;
    private FragmentOrderBinding binding;
    private RecycleAdapterOrder adapter;

    public static Fragment newInstance(int position) {
        OrderFragment fragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registEventBus();
        this.position = getArguments().getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
    }

    private void initCtrl() {
        adapter = new RecycleAdapterOrder(getContext());
        adapter.setOnItemClickListener(this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.recycler.setAdapter(adapter);
        binding.loadingLayout.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        binding.spring.setHeader(new AliHeader(getContext(), false));
        binding.spring.setFooter(new AliFooter(getContext(), false));
        binding.spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.spring.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.getResults().add(new Order());
                        adapter.getResults().add(new Order());
                        binding.spring.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
    }

    private void initData() {
        netOrderHistory();
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
        Order order = adapter.getResults().get(viewHolder.getLayoutPosition());
        OrderDetailActivity.start(getContext(), order.getSOID());
    }

    //把不是该分类下的订单移除
    private void convertOrders(List<Order> orders) {
        if (StrUtil.isEmpty(orders)) return;
        Iterator<Order> iterator = orders.iterator();
        while (iterator.hasNext()) {
            Order order = iterator.next();
            if (position == 0) {
                //全部
                continue;
            } else if (order.getOrderStatusId().equals("18") && position == 1) {
                //等待付款
                continue;
            } else {
                iterator.remove();
            }
        }
    }

    //订单历史
    public void netOrderHistory() {
        binding.loadingLayout.showLoadingView();
        Map<String, Object> param = new NetParam()
                .put("token", Token.getLocalToken())
                .put("years", "30d")
                .build();
        NetApi.NI().netOrderHistory(param).enqueue(new STCallback<OrderWrap>(OrderWrap.class) {
            @Override
            public void onSuccess(int status, OrderWrap wrap, String msg) {
                if (wrap.getResponseCode() == 0) {
                    List<Order> orders = wrap.getOrders();
                    convertOrders(orders);
                    if (!StrUtil.isEmpty(orders)) {
                        adapter.getResults().clear();
                        adapter.getResults().addAll(orders);
                        adapter.notifyDataSetChanged();
                        binding.loadingLayout.showOut();
                    } else {
                        binding.loadingLayout.showLackView();
                    }
                } else {
                    onError(wrap.getResponseCode(), wrap.getResponseMsg());
                }
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                binding.loadingLayout.showFailView();
            }
        });
    }
}
