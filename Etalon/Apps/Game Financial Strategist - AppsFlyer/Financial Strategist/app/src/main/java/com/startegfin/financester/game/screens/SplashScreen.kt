package com.startegfin.financester.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.startegfin.financester.game.LibGDXGame
import com.startegfin.financester.game.actors.progress.ASplashProgress
import com.startegfin.financester.game.global.Balance
import com.startegfin.financester.game.manager.MusicManager
import com.startegfin.financester.game.manager.SoundManager
import com.startegfin.financester.game.manager.SpriteManager
import com.startegfin.financester.game.utils.TIME_ANIM
import com.startegfin.financester.game.utils.actor.animHide
import com.startegfin.financester.game.utils.advanced.AdvancedScreen
import com.startegfin.financester.game.utils.advanced.AdvancedStage
import com.startegfin.financester.game.utils.runGDX
import com.startegfin.financester.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val imgLogo        by lazy { Image(game.splash.logo) }
    private val progressSplash by lazy { ASplashProgress(this) }

    override fun show() {
        game.activity.lottie.hide()

        Balance.balance     = game.dataStoreDohodUtil.dohodListFlow.value.sumOf { it.suma }
        Balance.balanceUsed = game.dataStoreRoshodUtil.roshodListFlow.value.sumOf { it.suma }

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

    override fun AdvancedStage.addActorsOnStageUI() {
        addActors(imgLogo, progressSplash)
        imgLogo.setBounds(249f,679f,256f,271f)
        progressSplash.setBounds(41f,59f,672f,21f)
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableAtlasList = mutableListOf(
                SpriteManager.EnumAtlas.SPLASH.data
            )
            loadAtlas()
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.SPLASH_MASK.data,
            )
            loadTexture()
        }
        game.assetManager.finishLoading()
        game.spriteManager.initAtlas()
        game.spriteManager.initTeture()
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
        game.spriteManager.initAtlas()
        game.spriteManager.initTeture()
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
                    if (progress % 50 == 0) log("progress = $progress%")
                    runGDX { progressSplash.progressPercentFlow.value = progress.toFloat() }
                    if (progress == 100) isFinishProgress = true

                    delay((10..14).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress) {
            isFinishProgress = false

            toScreen(MenuScreen::class.java.name)
        }
    }

    private fun toScreen(screenName: String) {
         stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(screenName) }
    }

}