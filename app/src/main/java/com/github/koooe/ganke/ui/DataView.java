package com.github.koooe.ganke.ui;

import android.os.Bundle;
import android.webkit.WebView;

import com.github.koooe.ganke.R;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by finally_xiaojian@163.com on 2015/8/29.
 * need onkeyDown();
 */
public class DataView extends ToolbarActivity{
    public static final String EXTRA_URL="extra_url";
    private String urls;
    @Bind(R.id.webView)
    WebView localview;
    public DataView(){

    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_webview;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        urls=getIntent().getStringExtra(EXTRA_URL);
        if(urls != null){
            init();
        }

    }

    private void init() {
        localview =  new WebView(this);
        try {
            localview.getSettings().setJavaScriptEnabled(true);
            localview.setWebViewClient(new WebviewClient());
            StringBuffer sb=new StringBuffer();
            sb.append("\"").append(urls).append("\"");
            if (sb.toString() != null) {
                localview.loadUrl(sb.toString());
            }else{
                localview.loadUrl("https://github.com/koooe/awesome-android-ui");
            }
        }catch(Exception ec){
            ec.printStackTrace();
        }
    }

}
