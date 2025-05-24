package com.traoya.yatirimya.util

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.core.view.isVisible
import com.traoya.yatirimya.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WebViewHelper(val activity: MainActivity) {

    private val binding = activity.binding
    private val coroutine = activity.coroutine

    var isRedirectToGame = false
        private set

    private var isOffer = true

    var blockBack    : () -> Unit = {}
    var blockRedirect: () -> Unit = {}

   // Init Web ----------------------------------------------------------------------------------

    @Suppress("DEPRECATION")
    fun initWeb() {
        coroutine.launch(Dispatchers.Main) {
            binding.webView.apply {
                settings.apply {
                    allowContentAccess = true
                    javaScriptEnabled = listOf(true).first()
                    javaScriptCanOpenWindowsAutomatically = true
                    allowFileAccess = true
                    mixedContentMode = 0
                    useWideViewPort = true
                    allowFileAccessFromFileURLs = true
                    allowUniversalAccessFromFileURLs = true
                    loadWithOverviewMode = true
                    domStorageEnabled = true
                    databaseEnabled = true
                }

                webViewClient = WebClient()

                // Реєструємо зворотний виклик у диспетчері
                activity.onBackPressedDispatcher.addCallback {
                    if (canGoBack()) {
                        goBack()
                    } else {
                        if (isOffer.not() && isVisible) {
                            activity.hideWebView()
                        } else blockBack()
                    }
                }
            }
        }
    }

    fun loadUrl(url: String, isOffer: Boolean = true) {
        activity.runOnUiThread {
            log("loadUrl: $url | isOffer = $isOffer")

            this.isOffer = isOffer
            binding.webView.loadUrl(url)
        }
    }

    private inner class WebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {

            log("redirect: ${request?.url.toString()}")

            isRedirectToGame = false

            if (request?.url.toString().contains("https://yatirimtraio")) {
                log("contains: CLOSE go to Game")
                isRedirectToGame = true
                blockRedirect()
                return true
            }

            return false
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            log("onPageFinished: url = $url")

            if (url.toString().contains("about:blank").not() && isRedirectToGame.not()) {
                log("onPageFinished showWebView url = $url")
                if (binding.webView.isVisible.not()) {
                    activity.showWebView()
                }
            }
        }
    }

}