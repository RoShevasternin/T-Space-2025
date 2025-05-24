package com.terniopel.antilateka

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.terniopel.antilateka.databinding.ActivityMainBinding
import com.terniopel.antilateka.game.utils.runGDX
import com.terniopel.antilateka.util.OneTime
import com.terniopel.antilateka.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    private val coroutine = CoroutineScope(Dispatchers.Main)
    private val onceExit  = OneTime()

    private lateinit var binding : ActivityMainBinding

    var blockBack    : () -> Unit = {}
    var blockRedirect: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
    }

    override fun exit() {
        onceExit.use {
            log("exit")
            coroutine.launch(Dispatchers.Main) {
                finishAndRemoveTask()
                delay(100)
                exitProcess(0)
            }
        }
    }

    private fun initialize() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // Init Web ----------------------------------------------------------------------------------

    @Suppress("DEPRECATION")
    fun initWeb() {
        coroutine.launch(Dispatchers.Main) {
            binding.webView.apply {
                settings.apply {
                    allowFileAccessFromFileURLs = true
                    allowContentAccess = true
                    javaScriptEnabled = listOf(true).first()
                    javaScriptCanOpenWindowsAutomatically = true
                    allowFileAccess = true
                    mixedContentMode = 0
                    useWideViewPort = true
                    allowUniversalAccessFromFileURLs = true
                    loadWithOverviewMode = true
                    domStorageEnabled = true
                    databaseEnabled = true
                }

                webViewClient = WebClient()

                // Реєструємо зворотний виклик у диспетчері
                onBackPressedDispatcher.addCallback {
                    if (canGoBack()) {
                        goBack()
                    } else {
                        if (isOffer.not() && isVisible) {
                            hideWebView()
                        } else blockBack()
                    }
                }
            }
        }
    }

    private var isOffer = true

    fun showUrl(url: String, isOffer: Boolean = true) {
        coroutine.launch(Dispatchers.Main) {
            log("showUrl: $url | isOffer = $isOffer")

            this@MainActivity.isOffer = isOffer
            binding.webView.loadUrl(url)

            setNavBarColor(R.color.white)
            setStatusBarColor(R.color.black, false)

            showWebView()
        }
    }

    private inner class WebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {

            log("redirect: ${request?.url.toString()}")

            if (request?.url.toString().contains("https://zaqme")) {
                hideWebView()
                log("contains")
                blockRedirect()
                return true
            }

            return false
        }
    }

    // Logic -----------------------------------------------------------------------------------------

    @Suppress("DEPRECATION")
    fun setNavBarColor(colorId: Int) {
        coroutine.launch(Dispatchers.Main) {
            window.navigationBarColor = getColor(colorId)
        }
    }

    @Suppress("DEPRECATION")
    fun setStatusBarColor(colorId: Int, isLight: Boolean = true) {
        coroutine.launch(Dispatchers.Main) {
            window.statusBarColor = getColor(colorId)
            window.decorView.systemUiVisibility = if (isLight) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else 0
        }
    }

    fun hideWebView() {
        coroutine.launch(Dispatchers.Main) {
            binding.webView.visibility         = View.GONE
            binding.navHostFragment.visibility = View.VISIBLE
            binding.navHostFragment.requestFocus()
        }
    }

    private fun showWebView() {
        coroutine.launch(Dispatchers.Main) {
            binding.navHostFragment.visibility = View.GONE
            binding.webView.visibility = View.VISIBLE
            binding.webView.requestFocus()
        }
    }

}