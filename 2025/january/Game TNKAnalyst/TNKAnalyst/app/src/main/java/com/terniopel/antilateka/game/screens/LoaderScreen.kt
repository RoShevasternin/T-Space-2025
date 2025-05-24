package com.terniopel.antilateka.game.screens

import com.terniopel.antilateka.game.GLOABAL_isGame
import com.terniopel.antilateka.game.actors.main.AMainLoader
import com.terniopel.antilateka.game.manager.MusicManager
import com.terniopel.antilateka.game.manager.SoundManager
import com.terniopel.antilateka.game.manager.SpriteManager
import com.terniopel.antilateka.game.utils.Block
import com.terniopel.antilateka.game.utils.advanced.AdvancedScreen
import com.terniopel.antilateka.game.utils.advanced.AdvancedStage
import com.terniopel.antilateka.game.utils.gdxGame
import com.terniopel.antilateka.game.utils.runGDX
import com.terniopel.antilateka.util.log
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
        addPanelLoader()
    }

    // Actors ------------------------------------------------------------------------

    private fun AdvancedStage.addPanelLoader() {
        addAndFillActor(aMainLoader)
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(gdxGame.spriteManager) {
            loadableAtlasList = mutableListOf(SpriteManager.EnumAtlas.LOADER.data)
            loadAtlas()
        }
        gdxGame.assetManager.finishLoading()
        gdxGame.spriteManager.initAtlas()
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
                    //runGDX { aMainLoader.aLoader.setPercent(progress) }
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    delay((15..30).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && GLOABAL_isGame) {
            isFinishProgress = false

            toGame()
        }
    }

    private fun toGame() {
        gdxGame.activity.hideWebView()

//        game.musicUtil.apply { music = sport_puzzle.apply {
//            isLooping = true
//            coff      = 0.15f
//        } }

        hideScreen {
            gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
        }
    }

}