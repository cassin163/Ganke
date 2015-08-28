package com.github.koooe.ganke.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.koooe.ganke.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/8/29.
 * DataView
 */
public class WebActivity extends ToolbarActivity {
    public static final String EXCAU_URL = "url";
    public static String url;

    @Bind(R.id.webView)
    WebView webView;

    public WebActivity() {
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        parseIntent(getIntent());
        if (TextUtils.isEmpty(url)) {
            finish();
        }
        initViews();
        webView.loadUrl(url);
    }

    public void parseIntent(Intent intent) {
        if (getIntent() != null) {
            url = getIntent().getStringExtra(EXCAU_URL);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initViews() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 关于跳转这是个神奇的问题，为毛重写就好
//                view.loadUrl(url);
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
        }
    }
}
