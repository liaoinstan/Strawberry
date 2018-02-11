package com.ins.version.network;

import android.util.Log;

import com.ins.version.VersionHelper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 使用HttpsURLConnection 发起请求，注意该操作需要在子线程中运行，由于只有一个请求，为了精简库体积不导入三方请求框架
 */
public class HttpRequest {

    public static InputStream get(String url) {
        try {
            URL urlPath = new URL(url);
            SSLContext sc = SSLContext.getInstance("TLS");
            ////////////////
            //允许所有证书
            sc.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
            ////////////////
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlPath.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(3000);
            httpURLConnection.connect();
            InputStream inputStream = null;
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
            }
            return inputStream;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(VersionHelper.TAG, "检查更新，请求超时");
            return null;
        }
    }

    private static class MyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static class MyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
