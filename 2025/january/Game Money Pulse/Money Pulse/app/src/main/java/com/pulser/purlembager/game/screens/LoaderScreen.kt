package com.pulser.purlembager.game.screens

import com.pulser.purlembager.game.actors.main.AMainLoader
import com.pulser.purlembager.game.GLOBAL_isGame
import com.pulser.purlembager.game.actors.APulse
import com.pulser.purlembager.game.manager.MusicManager
import com.pulser.purlembager.game.manager.SoundManager
import com.pulser.purlembager.game.manager.SpriteManager
import com.pulser.purlembager.game.utils.Block
import com.pulser.purlembager.game.utils.actor.setBounds
import com.pulser.purlembager.game.utils.actor.setBoundsScaled
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.advanced.AdvancedStage
import com.pulser.purlembager.game.utils.gdxGame
import com.pulser.purlembager.game.utils.runGDX
import com.pulser.purlembager.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoaderScreen : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val aPulse      by lazy { APulse(this) }
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

    override fun AdvancedStage.addActorsOnStageBack() {
        addPulse()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    // Actors Back------------------------------------------------------------------------

    private fun AdvancedStage.addPulse() {
        addActor(aPulse)
        aPulse.setBoundsScaled(sizeScalerScreen, -220f, -113f, 1616f, 1148f)
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
                SpriteManager.EnumTexture.L_a.data,
                SpriteManager.EnumTexture.L_b.data,
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