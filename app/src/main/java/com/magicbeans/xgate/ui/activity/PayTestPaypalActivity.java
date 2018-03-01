package com.magicbeans.xgate.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.api.adyen.AdyenPayApi;
import com.magicbeans.xgate.api.paypal.PaypalApi;
import com.magicbeans.xgate.bean.pay.PayResult;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

public class PayTestPaypalActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private String SOID;

    private final int REQUEST_CODE = 1001;

    public static void start(Context context) {
        Intent intent = new Intent(context, PayTestPaypalActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, String SOID) {
        Intent intent = new Intent(context, PayTestPaypalActivity.class);
        intent.putExtra("SOID", SOID);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytest_paypal);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        SOID = getIntent().getStringExtra("SOID");
    }

    private void initView() {
        findViewById(R.id.text_test_paypal).setOnClickListener(this);
        findViewById(R.id.text_test_adyen).setOnClickListener(this);
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_test_paypal:
                payTest();
                break;
            case R.id.text_test_adyen:
                AdyenPayApi.with(this).pay("1820816891", 129.5f, "liaoinstan@qq.com");
                break;
        }
    }

    public void payTest() {
        showLoadingDialog();
        PaypalApi.getInstance().apiGetPaypalToken(new PaypalApi.OnTokenCallback() {
            @Override
            public void onToken(String clientToken) {
                clientToken = clientToken.trim();
                DropInRequest dropInRequest = new DropInRequest().clientToken(clientToken);
                startActivityForResult(dropInRequest.getIntent(PayTestPaypalActivity.this), REQUEST_CODE);
                dismissLoadingDialog();
            }

            @Override
            public void onError(String msg) {
                dismissLoadingDialog();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String deviceData = result.getDeviceData();
                // use the result to update your UI and send the payment method nonce to your server
                netPaypalPay(nonce.getNonce());
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
                ToastUtil.showToastShort("支付已取消");
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                error.printStackTrace();
                ToastUtil.showToastShort("支付失败：" + error.getMessage());
            }
        }
    }

    //paypal支付
    public void netPaypalPay(String nonce) {
        showLoadingDialog();
        PaypalApi.getInstance().apiPaypalPay(nonce, SOID, new PaypalApi.OnPaypalCallback() {
            @Override
            public void onPaySuccess(PayResult payResult) {
                ToastUtil.showToastShort("支付成功");
                dismissLoadingDialog();
                PayResultActivity.start(PayTestPaypalActivity.this, payResult);
                finish();
            }

            @Override
            public void onPayFail(PayResult payResult) {
                ToastUtil.showToastShort("支付失败");
                dismissLoadingDialog();
                PayResultActivity.start(PayTestPaypalActivity.this, payResult);
                finish();
            }

            @Override
            public void onError(String msg) {
                ToastUtil.showToastShort("网络错误");
            }
        });
    }
}
