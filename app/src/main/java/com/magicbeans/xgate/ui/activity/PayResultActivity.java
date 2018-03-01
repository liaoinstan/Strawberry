package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.view.View;

import com.ins.common.utils.FocusUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.pay.PayResult;
import com.magicbeans.xgate.databinding.ActivityPayresultBinding;
import com.magicbeans.xgate.helper.AppHelper;
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
    private PayResult payResult;

    public static void start(Context context, PayResult payResult) {
        Intent intent = new Intent(context, PayResultActivity.class);
        intent.putExtra("payResult", payResult);
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
        payResult = (PayResult) getIntent().getSerializableExtra("payResult");
        commonRecommendController = new CommonRecommendController(binding.includeRecommend);
    }

    private void initView() {
    }

    private void initCtrl() {
        switch (payResult.getProcessorResponseCodeInt()) {
            case 1000:
                setToolbar("订单支付成功");
                break;
            default:
                setToolbar("订单支付失败");
                break;
        }
    }

    private void initData() {
        setData();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_orderdetail:
                OrderDetailActivity.start(this, payResult.getSOID());
                finish();
                break;
        }
    }

    private void setData() {
        String detail = "支付方式：信用卡支付\n订单金额：" + AppHelper.getPriceSymbol(null) + payResult.getAmount();
        binding.textPayresult.setText(detail);
    }
}
