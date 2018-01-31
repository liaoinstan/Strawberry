package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.adyen.core.PaymentRequest;
import com.adyen.core.interfaces.HttpResponseCallback;
import com.adyen.core.interfaces.PaymentDataCallback;
import com.adyen.core.interfaces.PaymentRequestListener;
import com.adyen.core.models.Payment;
import com.adyen.core.models.PaymentRequestResult;
import com.adyen.core.utils.AsyncHttpClient;
import com.google.gson.Gson;
import com.ins.common.utils.L;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.common.CommonEntity;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.net.STFormatCallback;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PayTestActivity extends BaseAppCompatActivity implements View.OnClickListener {

    public static void start(Context context) {
        Intent intent = new Intent(context, PayTestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytest);
        setToolbar();
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
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_test_pay:
                testPay();
                break;
        }
    }

//    private final String APIKEY = "AQErhmfuXNWTK0Qc+iSDhnY5sOaeWplUA5ZeADEO0AoKGDN5BwUMunpetRCcsRDBXVsNvuR83LVYjEgiTGAH-8dXLRbeP42zvEfVycOGHPAMHBryZCRDSyIjyTESmfuU=-Rk9ukaRxLqxBe2KF";
    private final String x_demo_server_api_key = "0101448667EE5CD5932B441CFA2483867639B0E69E5A995403965E00310E8B6CAE8D7206ADD36411D16303257317FEFDD7AFBAB07B4E5F0910ED62C78A5C6AA387F587EE1BDC2010C15D5B0DBEE47CDCB5588C48224C6007";
    private final String url = "https://checkoutshopper-test.adyen.com/checkoutshopper/demoserver/";
    private static final String SETUP = "setup";

    private void testPay() {
        PaymentRequest paymentRequest = new PaymentRequest(this, new PaymentRequestListener() {
            @Override
            public void onPaymentDataRequested(@NonNull final PaymentRequest paymentRequest, @NonNull String sdkToken, @NonNull final PaymentDataCallback callback) {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=UTF-8");

                // Provide this data to identify your app against the server; implement your own protocol (e.g. OAuth 2.0) to use your own server.
                headers.put("x-demo-server-api-key", x_demo_server_api_key); // Replace with your own Checkout Demo API key.

                final JSONObject jsonObject = new JSONObject();
                try {
                    // You always need to get the token from the SDK, even when using your own server.
                    jsonObject.put("token", sdkToken);
                    // Below is dummy data in a format expected by the Adyen test server. When implementing your own server, the data below becomes free format.
                    // You can also decide to add it to the payment creation request while sending it from your own server.
                    jsonObject.put("returnUrl", "app://checkout");
                    jsonObject.put("countryCode", "NL");
                    jsonObject.put("shopperLocale", "nl_NL");
                    final JSONObject amount = new JSONObject();
                    amount.put("value", 100);
                    amount.put("currency", "EUR");
                    jsonObject.put("amount", amount);
                    jsonObject.put("shopperReference", "example.merchant@adyen.com");
                    jsonObject.put("channel", "android");
                    jsonObject.put("reference", "test-payment");
                } catch (final JSONException jsonException) {
                    Log.e("Unexpected error", "Setup failed");
                }
                AsyncHttpClient.post(url + SETUP, headers, jsonObject.toString(), new HttpResponseCallback() { // Use https://checkoutshopper-test.adyen.com/checkoutshopper/demoserver/setup
                    @Override
                    public void onSuccess(final byte[] response) {
                        ToastUtil.showToastShort("onPaymentDataRequested onSuccess");
                        callback.completionWithPaymentData(response);
                    }

                    @Override
                    public void onFailure(final Throwable e) {
                        ToastUtil.showToastShort("onPaymentDataRequested onFailure");
                        e.printStackTrace();
                        paymentRequest.cancel();
                    }
                });
            }

            @Override
            public void onPaymentResult(@NonNull PaymentRequest paymentRequest, @NonNull PaymentRequestResult paymentRequestResult) {
                if (paymentRequestResult.isProcessed() && (
                        paymentRequestResult.getPayment().getPaymentStatus() == Payment.PaymentStatus.AUTHORISED
                                || paymentRequestResult.getPayment().getPaymentStatus() == Payment.PaymentStatus.RECEIVED)) {
                    ToastUtil.showToastShort("onPaymentResult onSuccess");
                } else {
                    ToastUtil.showToastShort("onPaymentResult onFailure");
                    paymentRequestResult.getError().printStackTrace();
                }
            }
        });
        paymentRequest.start();
    }
}
