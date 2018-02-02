package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
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
            case R.id.text_url1:
            case R.id.text_url2:
                String url = ((TextView) v).getText().toString();
                WebActivity.start(this, "adyen 集成文档", url);
                break;
        }
    }

    private static final String SETUP = "setup";
    private static final String VERIFY = "verify";
    private String merchantServerUrl = "https://checkoutshopper-test.adyen.com/checkoutshopper/demoserver/";
    private String merchantApiSecretKey = "0101448667EE5CD5932B441CFA2483867639B0E69E5A995403965E00310E8B6CAE8D7206ADD36411D16303257317FEFDD7AFBAB07B4E5F0910ED62C78A5C6AA387F587EE1BDC2010C15D5B0DBEE47CDCB5588C48224C6007";
//    private String merchantApiSecretKey = "0101408667EE5CD5932B441CFA2483867639B0E69E5A995423965E7B6A5B8B6CAE8D7206ADD36411D16303257317FEFDD7A4BE2403A31C396DE18F6C0898682F20228C10C15D5B0DBEE47CDCB5588C48224C6007";
    private String merchantApiHeaderKeyForApiSecretKey = "x-demo-server-api-key";

    private void testPay() {
        PaymentRequest paymentRequest = new PaymentRequest(this, new PaymentRequestListener() {
            @Override
            public void onPaymentDataRequested(@NonNull final PaymentRequest paymentRequest, @NonNull String sdkToken, @NonNull final PaymentDataCallback callback) {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=UTF-8");

                // Provide this data to identify your app against the server; implement your own protocol (e.g. OAuth 2.0) to use your own server.
                headers.put(merchantApiHeaderKeyForApiSecretKey, merchantApiSecretKey); // Replace with your own Checkout Demo API key.

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
                    amount.put("value", 1);
                    amount.put("currency", "EUR");
                    jsonObject.put("amount", amount);
                    jsonObject.put("shopperReference", "example.merchant@adyen.com");
                    jsonObject.put("channel", "android");
                    jsonObject.put("reference", "test-payment");
                } catch (final JSONException jsonException) {
                    Log.e("Unexpected error", "Setup failed");
                }
                AsyncHttpClient.post(merchantServerUrl + SETUP, headers, jsonObject.toString(), new HttpResponseCallback() { // Use https://checkoutshopper-test.adyen.com/checkoutshopper/demoserver/setup
                    @Override
                    public void onSuccess(final byte[] response) {
                        callback.completionWithPaymentData(response);
                    }

                    @Override
                    public void onFailure(final Throwable e) {
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
                    verifyPayment(paymentRequestResult.getPayment());
                } else {
                    ToastUtil.showToastShort("onPaymentResult onFailure");
                    if (paymentRequestResult.getError() != null) {
                        paymentRequestResult.getError().printStackTrace();
                    }
                }
            }
        });
        paymentRequest.start();
    }

    //验证支付结果
    private void verifyPayment(final Payment payment) {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("payload", payment.getPayload());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to verify payment.", Toast.LENGTH_LONG).show();
            return;
        }
        String verifyString = jsonObject.toString();

        final Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json; charset=UTF-8");
        headers.put(merchantApiHeaderKeyForApiSecretKey, merchantApiSecretKey);

        AsyncHttpClient.post(merchantServerUrl + VERIFY, headers, verifyString, new HttpResponseCallback() {
            String resultString = "";

            @Override
            public void onSuccess(final byte[] response) {
                try {
                    JSONObject jsonVerifyResponse = new JSONObject(new String(response, Charset.forName("UTF-8")));
                    String authResponse = jsonVerifyResponse.getString("authResponse");
                    if (authResponse.equalsIgnoreCase(payment.getPaymentStatus().toString())) {
                        resultString = "Payment is " + payment.getPaymentStatus().toString().toLowerCase(Locale.getDefault()) + " and verified.";
                    } else {
                        resultString = "Failed to verify payment.";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    resultString = "Failed to verify payment.";
                }
                Toast.makeText(PayTestActivity.this, resultString, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(final Throwable e) {
                Toast.makeText(PayTestActivity.this, resultString, Toast.LENGTH_LONG).show();
            }
        });
    }
}
