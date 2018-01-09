package com.magicbeans.xgate.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/9.
 */

public class NetInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request original = chain.request();

        Request request = original.newBuilder()
                .header("SecretKey", "XGate-Ecds4324-231545")
                .header("Authorization", "304a12a8866eb445700625ac39f19f02")
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);
    }
}
