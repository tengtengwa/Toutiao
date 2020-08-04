package com.example.toutiao.ui.activity

import android.annotation.SuppressLint
import android.graphics.PixelFormat
import android.os.Bundle
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import com.example.toutiao.R
import kotlinx.android.synthetic.main.layout_web_view_details.*

class DetailsActivity : SwipeCloseActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_web_view_details)
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            setFormat(PixelFormat.TRANSLUCENT)
        }

        web_antip_details.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            setPluginState(WebSettings.PluginState.ON)
            allowFileAccess = true
            loadWithOverviewMode = true
            useWideViewPort = true
            databaseEnabled = true
            layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            defaultTextEncodingName = "UTF-8"
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        web_antip_details.apply {
            webChromeClient = WebChromeClient()
            loadUrl(intent.getStringExtra("url"))
        }
        iv_back.setOnClickListener {
            finish()
        }
    }
}