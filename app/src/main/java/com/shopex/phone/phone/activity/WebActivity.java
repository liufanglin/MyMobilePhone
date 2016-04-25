package com.shopex.phone.phone.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.RootActivity;

/**
 * Created by samsung on 2016/4/25.
 */
public class WebActivity extends RootActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView= (WebView) findViewById(R.id.webview);
        webView.loadUrl("file:///android_asset/fangdao.html");
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}
