package com.fincarable.kapletaloverno.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.fincarable.kapletaloverno.game.GLOBAL_isGame
import com.fincarable.kapletaloverno.game.LibGDXGame
import com.fincarable.kapletaloverno.game.actors.ALoaderProgress
import com.fincarable.kapletaloverno.game.manager.MusicManager
import com.fincarable.kapletaloverno.game.manager.SoundManager
import com.fincarable.kapletaloverno.game.manager.SpriteManager
import com.fincarable.kapletaloverno.game.utils.TIME_ANIM
import com.fincarable.kapletaloverno.game.utils.actor.animHide
import com.fincarable.kapletaloverno.game.utils.advanced.AdvancedScreen
import com.fincarable.kapletaloverno.game.utils.advanced.AdvancedStage
import com.fincarable.kapletaloverno.game.utils.region
import com.fincarable.kapletaloverno.game.utils.runGDX
import com.fincarable.kapletaloverno.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoaderScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val imgLogo    by lazy { Image(game.loader.loader_logo) }
    private val aProgress  by lazy { ALoaderProgress(this) }

    override fun show() {
        loadSplashAssets()
        setBackBackground(game.loader.background_1.region)
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
        addActors(imgLogo, aProgress)
        imgLogo.setBounds(67f,941f,743f,272f)
        aProgress.setBounds(79f,651f,703f,138f)
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableAtlasList = mutableListOf(
                SpriteManager.EnumAtlas.LOADER.data
            )
            loadAtlas()
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.loader_mask.data,
                SpriteManager.EnumTexture.background_1.data,
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
                    if (progress % 100 == 0) log("progress = $progress%")
                    runGDX { aProgress.progressPercentFlow.value = progress.toFloat() }
                    if (progress == 100) isFinishProgress = true

                    delay((10..18).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && GLOBAL_isGame) {
            isFinishProgress = false

            log("toGame")
            game.activity.hideWebView()
            toScreen(Tutorial_1_Screen::class.java.name)
        }
    }

    private fun toScreen(screenName: String) {
         stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(screenName) }

        game.musicUtil.also { mu ->
            mu.music = mu.factory.apply { isLooping = true }
            mu.coff = 0.12f
        }
    }

}