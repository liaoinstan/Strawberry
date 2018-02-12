package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.order.OrderDetail;
import com.magicbeans.xgate.bean.order.OrderProduct;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.databinding.ActivityOrderdetailBinding;
import com.magicbeans.xgate.helper.AppHelper;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.net.nethelper.NetOrderHelper;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterOrderDetailGoods;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import java.util.Map;

public class OrderDetailActivity extends BaseAppCompatActivity implements View.OnClickListener, OnRecycleItemClickListener {

    private ActivityOrderdetailBinding binding;
    private String orderId;
    private RecycleAdapterOrderDetailGoods adapter;

    public static void start(Context context, String orderId) {
        if (AppHelper.User.isLogin()) {
            Intent intent = new Intent(context, OrderDetailActivity.class);
            intent.putExtra("orderId", orderId);
            context.startActivity(intent);
        } else {
            LoginActivity.start(context);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_orderdetail);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        orderId = getIntent().getStringExtra("orderId");
    }

    private void initView() {
    }

    private void initCtrl() {
        adapter = new RecycleAdapterOrderDetailGoods(this);
        adapter.setOnItemClickListener(this);
        binding.recycler.setNestedScrollingEnabled(false);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recycler.setAdapter(adapter);
        binding.btnGo.setOnClickListener(this);
    }

    private void initData() {
        netOrderDetail();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go:
                PayTestPaypalActivity.start(this, orderId);
                break;
        }
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
        OrderProduct product = adapter.getResults().get(viewHolder.getLayoutPosition());
        ProductDetailActivity.start(this, product.getProdId());
    }

    private void setData(OrderDetail orderDetail) {
        if (orderDetail != null) {
            binding.textPriceTotal.setText(AppHelper.getPriceSymbol(null) + orderDetail.getNetAmount());
            binding.textPriceReal.setText(AppHelper.getPriceSymbol(null) + orderDetail.getNetAmount());
            adapter.getResults().clear();
            adapter.getResults().addAll(orderDetail.getProductOrderList());
            adapter.notifyDataSetChanged();
        }
    }

    //订单详情
    public void netOrderDetail() {
//        showLoadingDialog();
//        Map<String, Object> param = new NetParam()
//                .put("token", Token.getLocalToken())
//                .put("SOId", orderId)
//                .build();
//        NetApi.NI().netOrderDetail(param).enqueue(new STCallback<OrderDetail>(OrderDetail.class) {
//            @Override
//            public void onSuccess(int status, OrderDetail orderDetail, String msg) {
//                setData(orderDetail);
//                hideLoadingDialog();
//            }
//
//            @Override
//            public void onError(int status, String msg) {
//                ToastUtil.showToastShort(msg);
//                hideLoadingDialog();
//            }
//        });
        showLoadingDialog();
        NetOrderHelper.getInstance().netOrderDetail(orderId, new NetOrderHelper.OnOrderDetailCallback() {
            @Override
            public void onOrderDetail(OrderDetail orderDetail) {
                setData(orderDetail);
                hideLoadingDialog();
            }

            @Override
            public void onError(String msg) {
                ToastUtil.showToastShort(msg);
                hideLoadingDialog();
            }
        });
    }
}
