package com.busiknesik.pomeshnek.game.screens

import com.busiknesik.pomeshnek.game.actors.main.AMainLoader
import com.busiknesik.pomeshnek.game.GLOBAL_isGame
import com.busiknesik.pomeshnek.game.manager.MusicManager
import com.busiknesik.pomeshnek.game.manager.SoundManager
import com.busiknesik.pomeshnek.game.manager.SpriteManager
import com.busiknesik.pomeshnek.game.utils.Block
import com.busiknesik.pomeshnek.game.utils.advanced.AdvancedScreen
import com.busiknesik.pomeshnek.game.utils.advanced.AdvancedStage
import com.busiknesik.pomeshnek.game.utils.gdxGame
import com.busiknesik.pomeshnek.game.utils.region
import com.busiknesik.pomeshnek.game.utils.runGDX
import com.busiknesik.pomeshnek.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoaderScreen : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val aMainLoader by lazy { AMainLoader(this) }

    override fun show() {
        loadSplashAssets()
        super.show()
        setBackBackground(gdxGame.assetsLoader.backgrand.region)
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun hideScreen(block: Block) {
        aMainLoader.animHideMain { block.invoke() }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addMain() {
        addAndFillActor(aMainLoader)
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(gdxGame.spriteManager) {
            loadableAtlasList = mutableListOf(SpriteManager.EnumAtlas.LOADER.data)
            loadAtlas()
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.L_backgrand.data,
                SpriteManager.EnumTexture.L_mseker.data,
            )
            loadTexture()
        }
        gdxGame.assetManager.finishLoading()
        gdxGame.spriteManager.initAtlasAndTexture()
    }

    private fun loadAssets() {
        with(gdxGame.spriteManager) {
            loadableAtlasList = SpriteManager.EnumAtlas.entries.map { it.data }.toMutableList()
            loadAtlas()
            loadableTexturesList = SpriteManager.EnumTexture.entries.map { it.data }.toMutableList()
            loadTexture()
        }
        with(gdxGame.musicManager) {
            loadableMusicList = MusicManager.EnumMusic.entries.map { it.data }.toMutableList()
            load()
        }
        with(gdxGame.soundManager) {
            loadableSoundList = SoundManager.EnumSound.entries.map { it.data }.toMutableList()
            load()
        }
    }

    private fun initAssets() {
        gdxGame.spriteManager.initAtlasAndTexture()
        gdxGame.musicManager.init()
        gdxGame.soundManager.init()
    }

    private fun loadingAssets() {
        if (isFinishLoading.not()) {
            if (gdxGame.assetManager.update(16)) {
                isFinishLoading = true
                initAssets()
            }
            progressFlow.value = gdxGame.assetManager.progress
        }
    }

    private fun collectProgress() {
        coroutine?.launch {
            var progress = 0
            progressFlow.collect { p ->
                while (progress < (p * 100)) {
                    progress += 1
                    runGDX { aMainLoader.prog.progressPercentFlow.value = progress.toFloat() }
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    delay((15..30).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && GLOBAL_isGame) {
            isFinishProgress = false

            toGame()
        }
    }

    private fun toGame() {
        gdxGame.activity.hideWebView()

//        gdxGame.musicUtil.apply { music = serious.apply {
//            isLooping = true
//            coff      = 0.20f
//        } }

        hideScreen {
            gdxGame.navigationManager.navigate(MainScreen::class.java.name)
        }
    }

}