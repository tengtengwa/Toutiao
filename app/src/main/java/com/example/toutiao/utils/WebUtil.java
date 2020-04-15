package com.example.toutiao.utils;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.databinding.BindingAdapter;

public class WebUtil {
    @BindingAdapter("loadUrl")
    public static void LoadUrl(WebView webView, String url) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);     //将图片调整到适合webview的大小
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
}
