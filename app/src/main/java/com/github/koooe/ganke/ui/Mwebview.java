package com.github.koooe.ganke.ui;

import android.os.Bundle;
import android.webkit.WebView;

import com.github.koooe.ganke.R;
import com.github.koooe.ganke.bean.WebviewClient;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/8/29.
 * DataView
 */
public class Mwebview extends ToolbarActivity {
    public static final String EXCAU_URL = "url";
    public static String url;
    @Bind(R.id.webView)
    WebView localview;

    public Mwebview() {
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra(EXCAU_URL);
        show();
    }

    public void show() {
        localview = new WebView(this);
        try {
            localview.setWebViewClient(new WebviewClient());
            localview.getSettings().setJavaScriptEnabled(true);
            if (url != null) {
                localview.loadUrl(url);
            } else {
                localview.loadUrl(null);
            }
        } catch (Exception ep) {
            ep.printStackTrace();
        }
    }
}
