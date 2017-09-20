package com.shelwee.update.utils;

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

import android.util.Log;

/**
 * 
 * @author ShelWee
 * 
 */
public class HttpRequest {

	public static InputStream get(String url) {
		try {
			URL urlPath = new URL(url);
			SSLContext sc = SSLContext.getInstance("TLS");
			////////////////
			//允许所有证书
			sc.init(null, new TrustManager[] { new MyTrustManager() },new SecureRandom());
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
			Log.e("TimeOut",
					"the connection is timeout, maybe the server was closed.");
			return null;
		}
	}

//	private void GetHttps() {
//		String https = " https://800wen.com/";
//		try {
//			SSLContext sc = SSLContext.getInstance("TLS");
//			sc.init(null, new TrustManager[] { new MyTrustManager() },new SecureRandom());
//			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//			HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
//			HttpsURLConnection conn = (HttpsURLConnection) new URL(https).openConnection();
//			conn.setDoOutput(true);
//			conn.setDoInput(true);
//			conn.connect();
//
//			BufferedReader br = new BufferedReader(new InputStreamReader(
//					conn.getInputStream()));
//			StringBuffer sb = new StringBuffer();
//			String line;
//			while ((line = br.readLine()) != null)
//				sb.append(line);
//
//			text.setText(sb.toString());
//
//		} catch (Exception e) {
//			Log.e(this.getClass().getName(), e.getMessage());
//		}
//	}

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
