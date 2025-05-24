package com.liberator.wisoliter.game.screens

import com.liberator.wisoliter.game.GLOBAL_isGame
import com.liberator.wisoliter.game.actors.AGrid
import com.liberator.wisoliter.game.actors.main.AMainLoader
import com.liberator.wisoliter.game.manager.MusicManager
import com.liberator.wisoliter.game.manager.SoundManager
import com.liberator.wisoliter.game.manager.SpriteManager
import com.liberator.wisoliter.game.utils.Block
import com.liberator.wisoliter.game.utils.actor.setBoundsScaled
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen
import com.liberator.wisoliter.game.utils.advanced.AdvancedStage
import com.liberator.wisoliter.game.utils.gdxGame
import com.liberator.wisoliter.game.utils.region
import com.liberator.wisoliter.game.utils.runGDX
import com.liberator.wisoliter.util.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoaderScreen : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val grid        by lazy { AGrid(this) }
    private val aMainLoader by lazy { AMainLoader(this) }

    override fun show() {
        loadSplashAssets()
        setBackBackground(gdxGame.assetsLoader.background.region)
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

    override fun AdvancedStage.addActorsOnStageBack() {
        addGrid()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
//        addAndFillActor(Image(drawerUtil.getTexture(Color.CORAL.apply { a = 0.5f })))
        addPanelLoader()
    }

    // Actors Back------------------------------------------------------------------------

    private fun AdvancedStage.addGrid() {
        addActor(grid)
        grid.setBoundsScaled(sizeScalerScreen, -128f, 0f, 904f, 1955f)
        grid.animMove()
    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addPanelLoader() {
        addAndFillActor(aMainLoader)
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(gdxGame.spriteManager) {
            loadableAtlasList = mutableListOf(SpriteManager.EnumAtlas.LOADER.data)
            loadAtlas()
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.L_background.data,
                SpriteManager.EnumTexture.L_grid.data,
                SpriteManager.EnumTexture.L_msek.data,
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
                    runGDX { aMainLoader.progress.progressPercentFlow.value = progress.toFloat() }
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

        gdxGame.musicUtil.apply { music = serious.apply {
            isLooping = true
            coff      = 0.20f
        } }

        hideScreen {
            gdxGame.navigationManager.navigate(TutorialScreen::class.java.name)
        }
    }

}