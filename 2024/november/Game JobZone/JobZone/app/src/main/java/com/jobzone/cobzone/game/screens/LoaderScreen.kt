package com.jobzone.cobzone.game.screens

import com.jobzone.cobzone.game.GDXGame
import com.jobzone.cobzone.game.actors.APanelTop
import com.jobzone.cobzone.game.actors.main.AMainLoader
import com.jobzone.cobzone.game.manager.MusicManager
import com.jobzone.cobzone.game.manager.SoundManager
import com.jobzone.cobzone.game.manager.SpriteManager
import com.jobzone.cobzone.game.utils.Block
import com.jobzone.cobzone.game.utils.advanced.AdvancedScreen
import com.jobzone.cobzone.game.utils.advanced.AdvancedStage
import com.jobzone.cobzone.game.utils.region
import com.jobzone.cobzone.game.utils.runGDX
import com.jobzone.cobzone.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoaderScreen(override val game: GDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val aPanelTop by lazy { APanelTop(this) }
    private val aMain     by lazy { AMainLoader(this) }

    override fun show() {
        loadSplashAssets()
        setBackBackground(game.assetsLoader.background.region)
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
            aMain.animHideMain {
                runGDX { block.invoke() }
            }
        }
    }


    override fun AdvancedStage.addActorsOnStageBack() {
        addPanelTop()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    // Actors Back------------------------------------------------------------------------

    private fun AdvancedStage.addPanelTop() {
        addActor(aPanelTop)

        val w = sizeScaler_Ui_Back.scaled(732f)
        val h = sizeScaler_Ui_Back.scaled(201f)
        val x = (viewportBack.worldWidth / 2) - (w / 2)
        val y = (viewportBack.worldHeight - h)
        aPanelTop.setBounds(x, y, w, h)

        aPanelTop.addImgLogo()
    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableAtlasList = mutableListOf(
                SpriteManager.EnumAtlas.LOADER.data
            )
            loadAtlas()
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.L_background.data,
                SpriteManager.EnumTexture.L_mask.data,
            )
            loadTexture()
        }
        game.assetManager.finishLoading()
        game.spriteManager.initAtlasAndTexture()
    }

    private fun loadAssets() {
        with(game.spriteManager) {
            loadableAtlasList = SpriteManager.EnumAtlas.entries.map { it.data }.toMutableList()
            loadAtlas()
            loadableTexturesList = SpriteManager.EnumTexture.entries.map { it.data }.toMutableList()
            loadTexture()
        }
        with(game.musicManager) {
            loadableMusicList = MusicManager.EnumMusic.entries.map { it.data }.toMutableList()
            load()
        }
        with(game.soundManager) {
            loadableSoundList = SoundManager.EnumSound.entries.map { it.data }.toMutableList()
            load()
        }
    }

    private fun initAssets() {
        game.spriteManager.initAtlasAndTexture()
        game.musicManager.init()
        game.soundManager.init()
    }

    private fun loadingAssets() {
        if (isFinishLoading.not()) {
            if (game.assetManager.update(16)) {
                isFinishLoading = true
                initAssets()
            }
            progressFlow.value = game.assetManager.progress
        }
    }

    private fun collectProgress() {
        coroutine?.launch {
            var progress = 0
            progressFlow.collect { p ->
                while (progress < (p * 100)) {
                    progress += 1
                    runGDX { aMain.progress.progressPercentFlow.value = progress.toFloat() }
                    if (progress % 100 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    delay((20..45).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && game.isGame) {
            isFinishProgress = false

            toGame()
        }
    }

    private fun toGame() {
        log("toGame")
        game.activity.hideWebView()

//        game.musicUtil.apply { music = aaa.apply {
//            isLooping = true
//            coff      = 0.15f
//        } }

        hideScreen {
            game.navigationManager.navigate(GreetingScreen::class.java.name)
        }
    }

}