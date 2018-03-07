package com.magicbeans.xgate.net.interceptor;

import com.magicbeans.xgate.common.AppData;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/9.
 */

public class NetInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request requestOld = chain.request();

        //添加公共参数
        HttpUrl httpUrl = requestOld.url().newBuilder()
                .addQueryParameter("region", "cn")
                .build();

        //添加公共请求头
        Request requestNew = requestOld.newBuilder()
                .header("SecretKey", "XGate-Ecds4324-231545")
                .header("Authorization", "304a12a8866eb445700625ac39f19f02")
                .header("User-Agent", AppData.App.getUserAgent())
                .url(httpUrl)
//                .method(requestOld.method(), requestOld.body())
                .build();

        return chain.proceed(requestNew);
    }
}
