package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.order.OrderDetail;
import com.magicbeans.xgate.bean.order.OrderProduct;
import com.magicbeans.xgate.databinding.ActivityOrderdetailBinding;
import com.magicbeans.xgate.helper.AppHelper;
import com.magicbeans.xgate.net.nethelper.NetOrderHelper;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterOrderDetailGoods;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

public class OrderDetailActivity extends BaseAppCompatActivity implements View.OnClickListener, OnRecycleItemClickListener {

    private ActivityOrderdetailBinding binding;
    private String orderId;
    private RecycleAdapterOrderDetailGoods adapter;
    private String netAmount;

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
                PayWayActivity.start(this, orderId, netAmount);
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
            binding.orderStatus.setText(orderDetail.getOrderStatus());
            binding.snNum.setText("订单编号： " + orderDetail.getSOID());
            binding.orderTime.setText("下单时间： " + orderDetail.getOrderDate() );
            binding.textAddressDetail.setText(orderDetail.getFullShipAddr().replace("\\n", "").replace("none", ""));
            adapter.getResults().clear();
            adapter.getResults().addAll(orderDetail.getProductOrderList());
            adapter.notifyDataSetChanged();
            if (TextUtils.isEmpty(orderDetail.getSOID())) {
                DialogSure.showDialog(this, "您的订单正在受理中，预计将在2小时内完成，暂时无法查看，请耐心等待", new DialogSure.CallBack() {
                    @Override
                    public void onSure() {
                        finish();
                    }
                });
            }
        }
    }

    //订单详情
    public void netOrderDetail() {
        showLoadingDialog();
        NetOrderHelper.getInstance().netOrderDetail(orderId, new NetOrderHelper.OnOrderDetailCallback() {
            @Override
            public void onOrderDetail(OrderDetail orderDetail) {
                netAmount = orderDetail.getNetAmount();
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
