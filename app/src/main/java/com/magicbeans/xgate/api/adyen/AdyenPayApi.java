package com.magicbeans.xgate.api.adyen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.adyen.core.PaymentRequest;
import com.adyen.core.interfaces.HttpResponseCallback;
import com.adyen.core.interfaces.PaymentDataCallback;
import com.adyen.core.interfaces.PaymentRequestListener;
import com.adyen.core.models.Payment;
import com.adyen.core.models.PaymentRequestResult;
import com.adyen.core.utils.AsyncHttpClient;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.bean.user.User;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.net.nethelper.NetTokenHelper;
import com.magicbeans.xgate.ui.activity.PayTestActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdyenPayApi {

    private Context context;

    private AdyenPayApi(Context context) {
        this.context = context;
    }

    public static synchronized AdyenPayApi with(Context context) {
        return new AdyenPayApi(context);
    }

    /////////////////////////////////////

    private static final String VERIFY = "verify";
    private String merchantServerUrl = "demo2017.strawberrynet.com/RedirectmWeChat.aspx";
    private String merchantApiSecretKey = "0101408667EE5CD5932B441CFA2483867639B0E69E5A995423965E7B6A5B8B6CAE8D7206ADD36411D16303257317FEFDD7A4BE2403A31C396DE18F6C0898682F20228C10C15D5B0DBEE47CDCB5588C48224C6007";
    private String merchantApiHeaderKeyForApiSecretKey = "x-demo-server-api-key";

    private void pay(final String soId, final float payAmount, final String email) {
        PaymentRequest paymentRequest = new PaymentRequest(context, new PaymentRequestListener() {
            @Override
            public void onPaymentDataRequested(@NonNull final PaymentRequest paymentRequest, @NonNull String sdkToken, @NonNull final PaymentDataCallback callback) {
                Map<String, Object> param = new NetParam()
                        .put("SOId", soId)
                        .put("payAmount", payAmount)
                        .put("email", email)
                        .build();
                NetApi.NI().adyenPaySetup(param).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            ResponseBody body = response.body();
                            if (body == null) {
                                ToastUtil.showToastShort("服務器异常：" + response.code());
                            } else {
                                callback.completionWithPaymentData(body.bytes());
                            }
                        } catch (Exception e) {
                            ToastUtil.showToastShort("未知错误");
                            e.printStackTrace();
                            paymentRequest.cancel();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        ToastUtil.showToastShort("请求失败");
                        t.printStackTrace();
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
            Toast.makeText(context, "Failed to verify payment.", Toast.LENGTH_LONG).show();
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
                Toast.makeText(context, resultString, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(final Throwable e) {
                Toast.makeText(context, resultString, Toast.LENGTH_LONG).show();
            }
        });
    }
}
