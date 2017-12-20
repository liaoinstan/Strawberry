package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ins.common.utils.L;
import com.ins.common.utils.viewutils.WebViewUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

public class WebActivity extends BaseAppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;

    private String url;
    private String title;

    public static void start(Context context) {
        Intent intent = new Intent(context, WebActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    public static void start(Context context, String title, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        L.e("web", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        if (getIntent().hasExtra("title")) {
            title = getIntent().getStringExtra("title");
            setToolbar(title);
        }
        if (getIntent().hasExtra("url")) {
            url = getIntent().getStringExtra("url");
        } else {
            url = "http://www.baidu.com";
        }
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.progress);
    }

    private void initCtrl() {

        WebViewUtil.initWebSetting(webView);
//
//        WebSettings setting = webView.getSettings();
//        setting.setJavaScriptEnabled(true);
//
//        ///
//        setting.setAllowFileAccess(true);
//        setting.setAllowFileAccessFromFileURLs(true);
//        setting.setAllowUniversalAccessFromFileURLs(true);
//        setting.setAppCacheEnabled(true);
//        setting.setDatabaseEnabled(true);
//        setting.setDomStorageEnabled(true);
//        setting.setCacheMode(WebSettings.LOAD_DEFAULT);
//        setting.setAppCachePath(webView.getContext().getCacheDir().getAbsolutePath());
//        setting.setUseWideViewPort(true);
//        setting.setLoadWithOverviewMode(true);
//        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            setting.setAllowFileAccessFromFileURLs(true);
//        }
//        ///

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }

        });
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void initData() {
        webView.loadUrl(url);
    }
}
