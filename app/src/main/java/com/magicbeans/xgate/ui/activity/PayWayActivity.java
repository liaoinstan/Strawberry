package com.magicbeans.xgate.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.PayPal;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.braintreepayments.api.models.PayPalRequest;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.api.adyen.AdyenPayApi;
import com.magicbeans.xgate.api.paypal.PaypalApi;
import com.magicbeans.xgate.bean.pay.AdyenResult;
import com.magicbeans.xgate.bean.pay.PayResult;
import com.magicbeans.xgate.bean.pay.PaypalResult;
import com.magicbeans.xgate.common.AppData;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

public class PayWayActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private String SOID;
    private String totalPrice;

    private final int REQUEST_CODE = 1001;
    private BraintreeFragment mBraintreeFragment;

    public static void start(Context context) {
        Intent intent = new Intent(context, PayWayActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, String SOID, String totalPrice) {
        Intent intent = new Intent(context, PayWayActivity.class);
        intent.putExtra("SOID", SOID);
        intent.putExtra("totalPrice", totalPrice);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payway);

        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        SOID = getIntent().getStringExtra("SOID");
        totalPrice = getIntent().getStringExtra("totalPrice");
    }

    private void initView() {
        findViewById(R.id.text_paypal).setOnClickListener(this);
        findViewById(R.id.text_adyen).setOnClickListener(this);
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_paypal:
                payPal();
                break;
            case R.id.text_adyen:
                AdyenPayApi.with(this).pay(SOID, (int)(Double.valueOf(totalPrice) * 100), AppData.App.getUser().getEmail(), new AdyenPayApi.OnAdyenCallback() {
                    @Override
                    public void onPaySuccess(AdyenResult adyenResult) {
                        PayResultActivity.start(PayWayActivity.this, new PayResult(adyenResult));
                    }
                });
                break;
        }
    }

    public void payPal() {
        showLoadingDialog();
        PaypalApi.getInstance().apiGetPaypalToken(new PaypalApi.OnTokenCallback() {
            @Override
            public void onToken(String clientToken) {
                clientToken = clientToken.trim();

                try {
                    mBraintreeFragment = BraintreeFragment.newInstance(PayWayActivity.this, clientToken);
                    PayPalRequest request = new PayPalRequest("1")
                            .currencyCode("USD")
                            .intent(PayPalRequest.INTENT_AUTHORIZE);
                    PayPal.requestOneTimePayment(mBraintreeFragment, request);

                    // mBraintreeFragment is ready to use!
                } catch (InvalidArgumentException e) {
                    // There was an issue with your authorization string.
                }

//                DropInRequest dropInRequest = new DropInRequest().clientToken(clientToken);
//                startActivityForResult(dropInRequest.getIntent(PayWayActivity.this), REQUEST_CODE);
//                dismissLoadingDialog();
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
            public void onPaySuccess(PaypalResult paypalResult) {
                dismissLoadingDialog();
                PayResultActivity.start(PayWayActivity.this, new PayResult(paypalResult));
                finish();
            }
        });
    }
}
