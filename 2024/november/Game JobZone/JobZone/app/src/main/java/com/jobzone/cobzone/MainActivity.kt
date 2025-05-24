package com.jobzone.cobzone

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.badlogic.gdx.utils.Array
import com.jobzone.cobzone.databinding.ActivityMainBinding
import com.jobzone.cobzone.game.utils.advanced.AdvancedGroup.Drawer
import com.jobzone.cobzone.game.utils.runGDX
import com.jobzone.cobzone.util.KeyboardHeightListener
import com.jobzone.cobzone.util.OneTime
import com.jobzone.cobzone.util.log
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

    val blockOpenKeyboard  = Array<(Int) -> Unit>()
    val blockCloseKeyboard = Array<() -> Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
        startKeyboardHeightListener()
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

    fun initWeb() {
        coroutine.launch {
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
        coroutine.launch {
            log("showUrl: $url | isOffer = $isOffer")

            this@MainActivity.isOffer = isOffer
            showWebView()
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER

            binding.webView.apply {
                isVisible = true
                loadUrl(url)
            }
            setNavBarColor(R.color.white)
            setStatusBarColor(R.color.black, false)
        }
    }

    private inner class WebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {

            log("redirect: ${request?.url.toString()}")

            if (request?.url.toString().contains("wehjcjde")) {
                log("contains")
                blockRedirect()
            }

            return false
        }
    }

    // Logic -----------------------------------------------------------------------------------------

    fun setNavBarColor(colorId: Int) {
        coroutine.launch(Dispatchers.Main) {
            window.navigationBarColor = getColor(colorId)
        }
    }

    fun setStatusBarColor(colorId: Int, isLightItems: Boolean = true) {
        coroutine.launch(Dispatchers.Main) {
            window.statusBarColor = getColor(colorId)
            window.decorView.systemUiVisibility = if (isLightItems) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else 0
        }
    }

    fun hideWebView() {
        coroutine.launch {
            binding.webView.visibility         = View.GONE
            binding.navHostFragment.visibility = View.VISIBLE
            binding.navHostFragment.requestFocus()
        }
    }

    fun setSoftInputMode_PAN() {
        runOnUiThread {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        }
    }

    private fun showWebView() {
        binding.navHostFragment.visibility = View.GONE
        binding.webView.visibility         = View.VISIBLE
        binding.webView.requestFocus()
    }

    // Keyboard ----------------------------------------------------------------------------------

    private fun startKeyboardHeightListener() = KeyboardHeightListener(binding.root) { keyboardHeight ->
        if (keyboardHeight > 0) {
            blockOpenKeyboard.onEach { it(keyboardHeight) } // Клавіатура відкрита
        } else {
            blockCloseKeyboard.onEach { it() } // Клавіатура закрита
        }
    }.startListening()

}