package com.magicbeans.xgate.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.order.Order;
import com.magicbeans.xgate.databinding.FragmentOrderBinding;
import com.magicbeans.xgate.net.nethelper.NetOrderHelper;
import com.magicbeans.xgate.ui.activity.OrderDetailActivity;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterOrder;
import com.magicbeans.xgate.ui.base.BaseFragment;

import java.util.Iterator;
import java.util.List;

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
                netOrderHistory(false);
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
        netOrderHistory(true);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
        Order order = adapter.getResults().get(viewHolder.getLayoutPosition());
        OrderDetailActivity.start(getContext(), order.getSOID());
    }

    //TODO:接口本应该提供一个根据订单类型查询的接口，但是接口并不完善，这里先自行进行筛选
    //TODO:另外订单状态和UI设计不同，只知道18是待付款，需要进一步确认
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
    public void netOrderHistory(boolean needLoading) {
        if (needLoading) binding.loadingLayout.showLoadingView();
        NetOrderHelper.getInstance().netOrderHistory(new NetOrderHelper.OnOrderListCallback() {
            @Override
            public void onOrderList(List<Order> orders) {
                convertOrders(orders);
                if (!StrUtil.isEmpty(orders)) {
                    adapter.getResults().clear();
                    adapter.getResults().addAll(orders);
                    adapter.notifyDataSetChanged();
                    binding.loadingLayout.showOut();
                } else {
                    binding.loadingLayout.showLackView();
                }
                binding.spring.onFinishFreshAndLoad();
            }

            @Override
            public void onError(String msg) {
                ToastUtil.showToastShort(msg);
                binding.loadingLayout.showFailView();
                binding.spring.onFinishFreshAndLoad();
            }
        });
    }
}
