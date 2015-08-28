package com.github.koooe.ganke.bean;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by finally_xiaojian@163.com on 2015/8/29.
 * ChromrClient
 */
public class WebchromeClient extends WebChromeClient {
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
    }
}
