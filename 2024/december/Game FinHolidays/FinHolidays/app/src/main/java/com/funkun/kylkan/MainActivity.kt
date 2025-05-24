package com.funkun.kylkan

import android.app.DatePickerDialog
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
import com.funkun.kylkan.databinding.ActivityMainBinding
import com.funkun.kylkan.game.utils.runGDX
import com.funkun.kylkan.util.OneTime
import com.funkun.kylkan.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    companion object {
        var statusBarHeight = 0
    }

    private val coroutine = CoroutineScope(Dispatchers.Main)
    private val onceExit  = OneTime()

    private lateinit var binding : ActivityMainBinding

    var blockBack    : () -> Unit = {}
    var blockRedirect: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        hideNavigationBar()

        initialize()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top

            if (binding.webView.isVisible) {
                val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
                binding.root.setPadding(0, 0, 0, imeInsets.bottom)
            }

            insets
        }

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
        runOnUiThread {
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
                        } else exit()
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

            if (request?.url.toString().contains("https://vogon")) {
                hideWebView()
                runGDX {
                    log("contains")
                    blockRedirect()
                }
                return true
            }

            return false
        }
    }

    // Logic -----------------------------------------------------------------------------------------

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
            binding.webView.visibility         = View.GONE
            binding.navHostFragment.visibility = View.VISIBLE
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

    // Dialog -------------------------------------------------------------------------------

    fun showDatePickerDialog(currentDate: String, blockDate: (String) -> Unit, blockCancel: () -> Unit) {
        runOnUiThread {
            // Отримуємо поточну дату
            val calendar   = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            dateFormat.parse(currentDate)?.let { calendar.time = it }

            val year  = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day   = calendar.get(Calendar.DAY_OF_MONTH)

            // Створюємо DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                this@MainActivity,

                R.style.CustomDatePickerDialog,
                null,
                year, month, day
            )

            // Налаштування назв кнопок
            datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "ОК") { dialog, which ->
                // Додатковий код, якщо потрібно обробити натискання на кнопку "ОК"
                val selectedYear = datePickerDialog.datePicker.year
                val selectedMonth = datePickerDialog.datePicker.month
                val selectedDay = datePickerDialog.datePicker.dayOfMonth

                // Обраний день, місяць і рік
                val selectedDate = String.format("%02d.%02d.%d", selectedDay, selectedMonth + 1, selectedYear)
                blockDate(selectedDate) // Показуємо обрану дату
            }

            datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Отменить") { dialog, which ->
                // Додатковий код, якщо потрібно обробити натискання на кнопку "Скасувати"
                blockCancel()
            }

            // Показуємо діалог
            datePickerDialog.show()
            datePickerDialog.setCanceledOnTouchOutside(false)
        }
    }

}