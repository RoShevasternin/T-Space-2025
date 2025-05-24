package com.startegfin.financester.game

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.startegfin.financester.GameActivity
import com.startegfin.financester.R
import com.startegfin.financester.appContext
import com.startegfin.financester.game.dataStore.DohodUtil
import com.startegfin.financester.game.dataStore.RoshodUtil
import com.startegfin.financester.game.manager.*
import com.startegfin.financester.game.manager.util.MusicUtil
import com.startegfin.financester.game.manager.util.SoundUtil
import com.startegfin.financester.game.manager.util.SpriteUtil
import com.startegfin.financester.game.screens.SplashScreen
import com.startegfin.financester.game.utils.GColor
import com.startegfin.financester.game.utils.advanced.AdvancedGame
import com.startegfin.financester.game.utils.disposeAll
import com.startegfin.financester.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class LibGDXGame(val activity: GameActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val splash by lazy { SpriteUtil.Splash() }
    val all    by lazy { SpriteUtil.All() }

    val musicUtil by lazy { MusicUtil() }
    val soundUtil by lazy { SoundUtil() }

    var backgroundColor = GColor.background
    val disposableSet   = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)

    val listCategoryRoshod = appContext.resources.getStringArray(R.array.roshod_items)
    val listCategoryDohod  = appContext.resources.getStringArray(R.array.dohod_items)

    val dataStoreRoshodUtil = RoshodUtil(coroutine)
    val dataStoreDohodUtil  = DohodUtil(coroutine)

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(SplashScreen::class.java.name)
    }

    val sharedPreferences: SharedPreferences = activity.getSharedPreferences("GAME_DATA", MODE_PRIVATE)

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

}