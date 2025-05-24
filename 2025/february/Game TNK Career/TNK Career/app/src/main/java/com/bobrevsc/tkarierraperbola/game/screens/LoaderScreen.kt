package com.bobrevsc.tkarierraperbola.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.bobrevsc.tkarierraperbola.game.GLOBAL_isGame
import com.bobrevsc.tkarierraperbola.game.LibGDXGame
import com.bobrevsc.tkarierraperbola.game.actors.progress.ALoaderProgress
import com.bobrevsc.tkarierraperbola.game.manager.MusicManager
import com.bobrevsc.tkarierraperbola.game.manager.SoundManager
import com.bobrevsc.tkarierraperbola.game.manager.SpriteManager
import com.bobrevsc.tkarierraperbola.game.utils.TIME_ANIM
import com.bobrevsc.tkarierraperbola.game.utils.actor.animHide
import com.bobrevsc.tkarierraperbola.game.utils.advanced.AdvancedScreen
import com.bobrevsc.tkarierraperbola.game.utils.advanced.AdvancedStage
import com.bobrevsc.tkarierraperbola.game.utils.region
import com.bobrevsc.tkarierraperbola.game.utils.runGDX
import com.bobrevsc.tkarierraperbola.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoaderScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val imgPanel       by lazy { Image(game.loader.panel) }
    private val imgLogo        by lazy { Image(game.loader.logo) }
    private val progressLoader by lazy { ALoaderProgress(this) }

    override fun show() {
        loadSplashAssets()
        setBackBackground(game.loader.LOADER.region)
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
        addActors(imgPanel, imgLogo, progressLoader)
        imgPanel.setBounds(200f,1144f,714f,250f)
        imgLogo.setBounds(260f,1189f,160f,160f)
        progressLoader.setBounds(200f,1017f,713f,39f)

        imgLogo.apply {
            setOrigin(Align.center)
            addAction(Actions.forever(
                Actions.sequence(
                    Actions.scaleBy(-0.2f, -0.2f, 0.3f, Interpolation.linear),
                    Actions.scaleBy(0.2f, 0.2f, 0.3f, Interpolation.linear),
                )
            ))
        }
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableAtlasList = mutableListOf(
                SpriteManager.EnumAtlas.LOADER.data
            )
            loadAtlas()
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.LOADER.data,
                SpriteManager.EnumTexture.LOADER_MASK.data,
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
                    runGDX { progressLoader.progressPercentFlow.value = progress.toFloat() }
                    if (progress == 100) isFinishProgress = true

                    delay((10..15).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && GLOBAL_isGame) {
            isFinishProgress = false

            log("toGame")
            game.activity.hideWebView()
            toScreen(Work_1_Screen::class.java.name)
        }
    }

    private fun toScreen(screenName: String) {
         stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(screenName) }
    }

}