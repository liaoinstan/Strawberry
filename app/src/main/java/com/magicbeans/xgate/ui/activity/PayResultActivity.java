package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.ins.common.common.GridSpacingItemDecoration;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.FocusUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.order.OrderDetail;
import com.magicbeans.xgate.databinding.ActivityPayresultBinding;
import com.magicbeans.xgate.helper.AppHelper;
import com.magicbeans.xgate.net.nethelper.NetOrderHelper;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterRecomment;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.controller.CommonRecommendController;

public class PayResultActivity extends BaseAppCompatActivity {

    public static final int STATE_PAY_SUCCESS = 0;
    public static final int STATE_PAY_FAIL = 1;
    public static final int STATE_PAY_CANCLE = 2;

    @IntDef({STATE_PAY_SUCCESS, STATE_PAY_FAIL, STATE_PAY_CANCLE})
    public @interface PayStatus {
    }

    private ActivityPayresultBinding binding;
    private CommonRecommendController commonRecommendController;
    private String SOID;
    private int payStatus;

    public static void startSuccess(Context context, String SOID) {
        start(context, SOID, STATE_PAY_SUCCESS);
    }

    public static void startFail(Context context, String SOID) {
        start(context, SOID, STATE_PAY_FAIL);
    }

    public static void start(Context context, String SOID, @PayStatus int payStatus) {
        Intent intent = new Intent(context, PayResultActivity.class);
        intent.putExtra("SOID", SOID);
        intent.putExtra("payStatus", payStatus);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payresult);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
        FocusUtil.focusToTop(toolbar);
    }

    private void initBase() {
        SOID = getIntent().getStringExtra("SOID");
        payStatus = getIntent().getIntExtra("payStatus", STATE_PAY_FAIL);
        commonRecommendController = new CommonRecommendController(binding.includeRecommend);
    }

    private void initView() {
    }

    private void initCtrl() {
        switch (payStatus) {
            case STATE_PAY_SUCCESS:
                setToolbar("订单支付成功");
                break;
            case STATE_PAY_FAIL:
                setToolbar("订单支付失败");
                break;
            case STATE_PAY_CANCLE:
                setToolbar("支付已取消");
                break;
        }
    }

    private void initData() {
        netOrderDetail();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_orderdetail:
                OrderDetailActivity.start(this, SOID);
                finish();
                break;
        }
    }

    private void setData(OrderDetail orderDetail) {
        if (orderDetail != null) {
            String detail = "支付方式：信用卡支付\n订单金额：" + AppHelper.getPriceSymbol(null) + orderDetail.getNetAmount();
            binding.textPayresult.setText(detail);
        }
    }

    //订单详情
    public void netOrderDetail() {
        showLoadingDialog();
        NetOrderHelper.getInstance().netOrderDetail(SOID, new NetOrderHelper.OnOrderDetailCallback() {
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
