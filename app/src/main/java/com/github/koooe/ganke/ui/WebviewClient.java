package com.github.koooe.ganke.ui;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by finally_xiaojian@163.com on 2015/8/29.
 *
 * this class is WebClient;
 */
public class WebviewClient extends WebViewClient{
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
