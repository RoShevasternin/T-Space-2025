package com.ingiodin.strinvestida.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.ingiodin.strinvestida.game.GLOBAL_isGame
import com.ingiodin.strinvestida.game.LibGDXGame
import com.ingiodin.strinvestida.game.actors.ALoaderProgress
import com.ingiodin.strinvestida.game.manager.MusicManager
import com.ingiodin.strinvestida.game.manager.SoundManager
import com.ingiodin.strinvestida.game.manager.SpriteManager
import com.ingiodin.strinvestida.game.utils.TIME_ANIM
import com.ingiodin.strinvestida.game.utils.actor.animHide
import com.ingiodin.strinvestida.game.utils.advanced.AdvancedScreen
import com.ingiodin.strinvestida.game.utils.advanced.AdvancedStage
import com.ingiodin.strinvestida.game.utils.region
import com.ingiodin.strinvestida.game.utils.runGDX
import com.ingiodin.strinvestida.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoaderScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val imgLogo      by lazy { Image(game.loader.logo) }
    private val imgIniter    by lazy { Image(game.loader.initer) }
    private val actProgress  by lazy { ALoaderProgress(this) }

    override fun show() {
        loadSplashAssets()
        setBackBackground(game.loader.B_Loader.region)
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
        addActors(imgLogo, imgIniter, actProgress)
        imgLogo.setBounds(245f,1966f,521f,101f)
        imgIniter.setBounds(148f,752f,717f,183f)
        actProgress.setBounds(151f,869f,712f,63f)
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableAtlasList = mutableListOf(
                SpriteManager.EnumAtlas.LOADERAS.data
            )
            loadAtlas()
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.loader.data,
                SpriteManager.EnumTexture.maska.data,
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
                    runGDX { actProgress.progressPercentFlow.value = progress.toFloat() }
                    if (progress == 100) isFinishProgress = true

                    //delay((10..18).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && GLOBAL_isGame) {
            isFinishProgress = false

            log("toGame")
            game.activity.hideWebView()
            toScreen(MenuScreen::class.java.name)
        }
    }

    private fun toScreen(screenName: String) {
         stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(screenName) }
    }

}