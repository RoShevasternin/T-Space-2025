package com.goskapmepdup.dalaylamek.util

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.goskapmepdup.dalaylamek.MenuMainActivity2
import com.goskapmepdup.dalaylamek.R

class Vrum : AppCompatActivity() {
    private lateinit var wend: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vrum)

        val url = intent.getStringExtra("web_url")
        Log.d("MY_TAG","URL: $url")
        wend = findViewById(R.id.gn)

        wend.apply {
            settings.allowFileAccessFromFileURLs = true
            settings.allowContentAccess = true
            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.allowFileAccess = true
            settings.mixedContentMode = 0
            settings.useWideViewPort = true
            settings.allowUniversalAccessFromFileURLs = true
            settings.loadWithOverviewMode = true
            settings.domStorageEnabled = true
            settings.databaseEnabled = true
        }

        wend.webViewClient = WerandaSett(wend)

        if (url != null) {
            wend.loadUrl(url)
        }
    }

    inner class WerandaSett(val weras: WebView) : WebViewClient() {
        override fun onPageStarted(views: WebView?, url: String?, favicon: Bitmap?) {
            if (url != null && url.contains("https://zxtvjob")) {
                val intent = Intent(this@Vrum, MenuMainActivity2::class.java)
                startActivity(intent)
                wend.stopLoading()
            } else {
                weras.layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return false
        }
    }
}
