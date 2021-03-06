package com.magicbeans.xgate.api.paypal;

import android.text.TextUtils;

import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.bean.pay.PaypalResult;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;

import java.util.Map;

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

//        String url = NetParam.createUrl("https://demo2017.strawberrynet.com/app/apiPaypalRequest.aspx/app/apiPaypalRequest.aspx", param);
//        L.e(url);

        NetApi.NI().apiPaypalPay(param).enqueue(new STCallback<PaypalResult>(PaypalResult.class) {
            @Override
            public void onSuccess(int status, PaypalResult payResult, String msg) {
                if (callback != null) callback.onPaySuccess(payResult);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }


    public interface OnTokenCallback {
        void onToken(String clientToken);

        void onError(String msg);
    }

    public interface OnPaypalCallback {
        void onPaySuccess(PaypalResult paypalResult);
    }
}
