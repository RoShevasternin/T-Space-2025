package com.ekobioznaher.sugertogerra.game.screens

import com.ekobioznaher.sugertogerra.game.actors.main.AMainLoader
import com.ekobioznaher.sugertogerra.game.GLOBAL_isGame
import com.ekobioznaher.sugertogerra.game.manager.MusicManager
import com.ekobioznaher.sugertogerra.game.manager.SoundManager
import com.ekobioznaher.sugertogerra.game.manager.SpriteManager
import com.ekobioznaher.sugertogerra.game.utils.Block
import com.ekobioznaher.sugertogerra.game.utils.advanced.AdvancedScreen
import com.ekobioznaher.sugertogerra.game.utils.advanced.AdvancedStage
import com.ekobioznaher.sugertogerra.game.utils.gdxGame
import com.ekobioznaher.sugertogerra.game.utils.region
import com.ekobioznaher.sugertogerra.game.utils.runGDX
import com.ekobioznaher.sugertogerra.util.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoaderScreen : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val aMainLoader by lazy { AMainLoader(this) }

    override fun show() {
        loadSplashAssets()
        //setBackBackground(gdxGame.assetsLoader.background.region)
        super.show()
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun hideScreen(block: Block) {
        coroutine?.launch {
            aMainLoader.animHideMain {
                runGDX { block.invoke() }
            }
        }
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
                SpriteManager.EnumTexture.L_Loader.data,
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
                    //runGDX { aMainLoader.progress.progressPercentFlow.value = progress.toFloat() }
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    //delay((15..30).shuffled().first().toLong())
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
            gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
        }
    }

}