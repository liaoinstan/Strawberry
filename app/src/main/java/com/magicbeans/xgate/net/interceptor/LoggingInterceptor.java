package com.magicbeans.xgate.net.interceptor;

import com.ins.common.utils.L;
import com.ins.common.utils.UrlUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by Administrator on 2018/1/25.
 * 魅族手机无法打印HttpLoggingInterceptor的日志，妈卖批，垃圾魅族，只能自己实现一个了
 */

public class LoggingInterceptor implements Interceptor {
    private final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        RequestBody requestBody = request.body();

        //获取body
        String bodyStr = null;
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            bodyStr = buffer.readString(charset);
        }

        //获取get参数
        Map<String, String> paramMap = UrlUtil.getParamMap(request.url().toString());
        String paramMapLog = "";
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            paramMapLog += entry.getKey() + ":" + entry.getValue() + "\n";
        }

        L.e(String.format("发送请求 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n" +
                        "【method】：%s\n" +
                        "【url】：%s\n" +
                        "【get参数】：\n%s" +
                        "【headers】:\n%s" +
                        "【body】：%s",
                request.method(), request.url(), paramMapLog, request.headers(), bodyStr));

        //获取响应时间
        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        //获取响应body
        String rBody = null;
        if (responseBody != null) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    e.printStackTrace();
                }
            }
            rBody = buffer.clone().readString(charset);
        }

        //rBody可能过长，log一行打印不下，分行处理，一行打印3000个字符
        List<String> rBodys = clip(rBody);
        L.e(String.format("收到响应 %s %ss <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n" +
                        "【msg】：%s\n" +
                        "【请求url】：%s\n" +
                        "【请求body】：%s\n" +
                        "【响应body】：\n%s",
                response.code(), tookMs, response.message(), response.request().url(), bodyStr, rBodys.get(0)));
        //打印剩下部分未打印完的log
        for (int i = 1; i < rBodys.size(); i++) {
            L.e(rBodys.get(i));
        }


        return response;
    }

    //将超过最大长度的字符串分行处理
    private static List<String> clip(String s) {
        List<String> strs = new ArrayList<>();
        int maxlength = 3000;
        int iLen = s.length();
        while (iLen > maxlength) {
            String tmp = s.substring(0, maxlength);
            strs.add(tmp);
            s = s.substring(maxlength);
            iLen = s.length();
        }
        strs.add(s);
        return strs;
    }
}
