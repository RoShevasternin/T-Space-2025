package com.bobrevsc.tkarierraperbola

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.bobrevsc.tkarierraperbola.databinding.ActivityMainBinding
import com.bobrevsc.tkarierraperbola.game.GLOBAL_isPauseGame
import com.bobrevsc.tkarierraperbola.util.OneTime
import com.bobrevsc.tkarierraperbola.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    companion object {
        var statusBarHeight = 0
    }

    private val coroutine = CoroutineScope(Dispatchers.Main)
    private val onceExit  = OneTime()

    private val onceStatusBarHeight = OneTime()

    lateinit var binding : ActivityMainBinding

    var blockBack    : () -> Unit = {}
    var blockRedirect: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //enableEdgeToEdge()
        //hideNavigationBar()

        initialize()

//        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
//            onceStatusBarHeight.use {
//                statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
//                //hideStatusBar()
//            }
//
//            if (binding.webView.isVisible) {
//                val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
//                binding.root.setPadding(0, 0, 0, imeInsets.bottom)
//            }
//
//            insets
//        }

    }

    override fun exit() {
        onceExit.use {
            log("exit")
            coroutine.launch {
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
        runOnUiThread {
            log("showUrl: $url | isOffer = $isOffer")

            this@MainActivity.isOffer = isOffer
            binding.webView.loadUrl(url)

            showWebView()
            setStatusBarColor(R.color.white)
        }
    }

    private inner class WebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {

            log("redirect: ${request?.url.toString()}")

            if (request?.url.toString().contains("https://yqbmhobj")) {
                log("contains")
                hideWebView()
                blockRedirect()
                return true
            }

            return false
        }
    }

    // Logic -----------------------------------------------------------------------------------------

    @Suppress("DEPRECATION")
    fun setNavBarColor(colorId: Int) {
        runOnUiThread {
            window.navigationBarColor = getColor(colorId)
        }
    }

    @Suppress("DEPRECATION")
    fun setStatusBarColor(colorId: Int, isLightIcon: Boolean = true) {
        runOnUiThread {
            window.statusBarColor = getColor(colorId)
            window.decorView.systemUiVisibility = if (isLightIcon) 0 else View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    fun hideWebView() {
        runOnUiThread {
            binding.webView.visibility         = View.GONE
            binding.navHostFragment.visibility = View.VISIBLE
            binding.navHostFragment.requestFocus()
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            GLOBAL_isPauseGame = false
        }
    }

    private fun showWebView() {
        runOnUiThread {
            binding.navHostFragment.visibility = View.GONE
            binding.webView.visibility = View.VISIBLE
            binding.webView.requestFocus()
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER

            GLOBAL_isPauseGame = true
        }
    }

}