package com.magicbeans.xgate.api.paypal;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.adyen.core.PaymentRequest;
import com.adyen.core.interfaces.HttpResponseCallback;
import com.adyen.core.interfaces.PaymentDataCallback;
import com.adyen.core.interfaces.PaymentRequestListener;
import com.adyen.core.models.Payment;
import com.adyen.core.models.PaymentRequestResult;
import com.adyen.core.utils.AsyncHttpClient;
import com.ins.common.utils.L;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.bean.common.CommonEntity;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.net.nethelper.NetAddressHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaypalApi {

    private static PaypalApi instance;

    private PaypalApi() {
    }

    public static synchronized PaypalApi getInstance() {
        if (instance == null) instance = new PaypalApi();
        return instance;
    }

    ////////////////////////////////////////

    //paypal支付
    public void apiGetPaypalToken(final OnTokenCallback callback) {
        Map<String, Object> param = new NetParam()
                .put("accountID", Token.getLocalAccountId())
                .put("token", Token.getLocalToken())
                .build();
        NetApi.NI().apiGetPaypalToken(param).enqueue(new STCallback<String>(String.class) {
            @Override
            public void onSuccess(int status, String paypalToken, String msg) {
                if (!TextUtils.isEmpty(paypalToken)) {
                    if (callback != null) callback.onToken(paypalToken);
                } else {
                    onError(0, "client token 为空");
                }
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                if (callback != null) callback.onError(msg);
            }
        });
    }

    //paypal支付
    public void apiPaypalPay(String nonce, String soid, final OnPaypalCallback callback) {
        Map<String, Object> param = new NetParam()
                .put("accountID", Token.getLocalAccountId())
                .put("token", Token.getLocalToken())
                .put("payment_method_nonce", nonce)
                .put("soid", soid)
                .build();

        String url = NetParam.createUrl("https://demo2017.strawberrynet.com/app/apiPaypalRequest.aspx/app/apiPaypalRequest.aspx", param);

        L.e(url);

        NetApi.NI().apiPaypalPay(param).enqueue(new STCallback<CommonEntity>(CommonEntity.class) {
            @Override
            public void onSuccess(int status, CommonEntity com, String msg) {
                if (callback != null) callback.onPaySuccess();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                if (callback != null) callback.onError(msg);
            }
        });
    }


    public interface OnTokenCallback {
        void onToken(String clientToken);

        void onError(String msg);
    }

    public interface OnPaypalCallback {
        void onPaySuccess();

        void onError(String msg);
    }
}
