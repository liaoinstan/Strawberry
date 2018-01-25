package com.magicbeans.xgate.net.interceptor;

import com.ins.common.utils.L;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/4/26.
 */

public class RSAInterceptor implements Interceptor {

    private static boolean needRSA = false;     //是否加密

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        //请求体定制：统一添加参数
        if (request.body() instanceof FormBody) {
            FormBody.Builder newFormBody = new FormBody.Builder();
            FormBody oidFormBody = (FormBody) request.body();
            for (int i = 0; i < oidFormBody.size(); i++) {
                if (!needRSA) {
                    newFormBody.addEncoded(oidFormBody.encodedName(i), oidFormBody.encodedValue(i));
                }
                //打印出未加密参数调试
                L.e("未加密参数", oidFormBody.encodedName(i) + ":" + oidFormBody.encodedValue(i));
            }
            if (needRSA) {
//                newFormBody.add(DESSignUtil.PARAMNAME, DESSignUtil.DES(oidFormBody));
            }
            requestBuilder.method(request.method(), newFormBody.build());
        }
        requestBuilder.addHeader("token", "asdasdadasd");
        return chain.proceed(requestBuilder.build());
    }
}
