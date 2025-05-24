package com.bagazkz.klarebadew

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
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import com.bagazkz.klarebadew.databinding.ActivityMainBinding
import com.bagazkz.klarebadew.game.GLOBAL_isPauseGame
import com.bagazkz.klarebadew.util.OneTime
import com.bagazkz.klarebadew.util.log
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    companion object {
        var statusBarHeight = 0
        var navBarHeight    = 0
    }

    private val coroutine = CoroutineScope(Dispatchers.Main)
    private val onceExit  = OneTime()

    private val onceSystemBarHeight = OneTime()

    lateinit var binding : ActivityMainBinding

    val windowInsetsController by lazy { WindowCompat.getInsetsController(window, window.decorView) }

    var blockBack    : () -> Unit = {}
    var blockRedirect: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        initialize()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            onceSystemBarHeight.use {
                statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
                navBarHeight    = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom

                // hide Status or Nav bar (після встановлення їх розмірів)
                windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
                windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }

            if (binding.webView.isVisible) {
                val imeBottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
                val navBottom = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
                val totalBottom = maxOf(imeBottom, navBottom)

                binding.root.setPadding(0, statusBarHeight, 0, totalBottom)
                log("ime = $imeBottom | navBar = $navBarHeight | total = $totalBottom")
            }

            WindowInsetsCompat.CONSUMED
        }

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
        }
    }

    private inner class WebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {

            log("redirect: ${request?.url.toString()}")

            if (request?.url.toString().contains("https://kmfht")) {
                log("contains")
                hideWebView()
                blockRedirect()
                return true
            }

            return false
        }
    }

    // Logic -----------------------------------------------------------------------------------------

    private fun hideNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // API 30 і вище
            window.insetsController?.let {
                it.hide(WindowInsets.Type.navigationBars()) // Ховаємо панель навігації
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            // Для API нижче 30
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }
    }

    fun hideWebView() {
        runOnUiThread {
            windowInsetsController.show(WindowInsetsCompat.Type.statusBars())

            binding.webView.visibility         = View.GONE
            binding.navHostFragment.visibility = View.VISIBLE
            binding.navHostFragment.requestFocus()

            GLOBAL_isPauseGame = false
        }
    }

    private fun showWebView() {
        runOnUiThread {
            windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())

            binding.navHostFragment.visibility = View.GONE
            binding.webView.visibility = View.VISIBLE
            binding.webView.requestFocus()

            GLOBAL_isPauseGame = true
        }
    }

}