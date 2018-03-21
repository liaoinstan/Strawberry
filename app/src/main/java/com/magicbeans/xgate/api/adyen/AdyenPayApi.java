package com.magicbeans.xgate.api.adyen;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.adyen.core.PaymentRequest;
import com.adyen.core.interfaces.PaymentDataCallback;
import com.adyen.core.interfaces.PaymentRequestListener;
import com.adyen.core.models.Payment;
import com.adyen.core.models.PaymentRequestResult;
import com.ins.common.utils.L;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.bean.pay.AdyenResult;
import com.magicbeans.xgate.bean.pay.PaypalResult;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;

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

    private String merchantServerUrl = "demo2017.strawberrynet.com/RedirectmWeChat.aspx";
    private String merchantApiSecretKey = "0101408667EE5CD5932B441CFA2483867639B0E69E5A995423965E7B6A5B8B6CAE8D7206ADD36411D16303257317FEFDD7A4BE2403A31C396DE18F6C0898682F20228C10C15D5B0DBEE47CDCB5588C48224C6007";
    private String merchantApiHeaderKeyForApiSecretKey = "x-demo-server-api-key";

    public void pay(final String soId, final int payAmount, final String email, final OnAdyenCallback callback) {
        PaymentRequest paymentRequest = new PaymentRequest(context, new PaymentRequestListener() {
            @Override
            public void onPaymentDataRequested(@NonNull final PaymentRequest paymentRequest, @NonNull String sdkToken, @NonNull final PaymentDataCallback callback) {
                Map<String, Object> param = new NetParam()
                        .put("soid", soId)
                        .put("currency", "CNY")
                        .put("device", "android")
                        .put("token", "sdkToken")
                        .put("amount", payAmount + "")
                        .put("email", email)
                        .build();
                NetApi.NI().adyenPaySetup(param).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            ResponseBody body = response.body();
                            if (body == null) {
                                ToastUtil.showToastShort("服務器异常：" + response.code());
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        paymentRequest.cancel();
                                    }
                                }, 2000);
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
                    verifyPayment(paymentRequestResult.getPayment(), callback);
                } else {
                    if (paymentRequestResult.getError() != null) {
                        paymentRequestResult.getError().printStackTrace();
                    }
                }
            }
        });
        paymentRequest.start();
    }

    //验证支付结果
    private void verifyPayment(final Payment payment, final OnAdyenCallback callback) {
        Map<String, Object> param = new NetParam()
                .put("payload", payment.getPayload())
                .build();
        NetApi.NI().adyenPaySetup(param).enqueue(new STCallback<AdyenResult>(AdyenResult.class) {
            @Override
            public void onSuccess(int status, AdyenResult adyenResult, String msg) {
                if (callback != null) callback.onPaySuccess(adyenResult);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }

    public interface OnAdyenCallback {
        void onPaySuccess(AdyenResult adyenResult);
    }
}
