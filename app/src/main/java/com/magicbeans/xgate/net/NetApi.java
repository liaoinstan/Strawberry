package com.magicbeans.xgate.net;

import com.ins.common.utils.L;
import com.magicbeans.xgate.net.interceptor.LoggingInterceptor;
import com.magicbeans.xgate.net.interceptor.NetInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求的Retrofit2创建类，以及通过该类去调用接口
 */
public class NetApi {

    private final static String LOG_TAG = "NetApi";
    private static NetInterface ni;
    private static String baseUrl;
    private static boolean debug;

    private NetApi() {
        throw new UnsupportedOperationException();
    }

    public static NetInterface NI() {
        if (ni == null) {
            initApi();
        }
        return ni;
    }

    private static void initApi() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                L.e(message);
            }
        });
        httpLoggingInterceptor.setLevel(debug ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //.addNetworkInterceptor(httpLoggingInterceptor)//FIXME:魅族手机上使用这个类无法打印日志，使用下面的自定义拦截器代替
                .addInterceptor(new LoggingInterceptor())
                .addInterceptor(new NetInterceptor())
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ni = retrofit.create(NetInterface.class);
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setBaseUrl(String baseUrl) {
        NetApi.baseUrl = baseUrl;
        initApi();
    }

    public static void setDebug(boolean debug) {
        NetApi.debug = debug;
    }
}
