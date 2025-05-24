package com.proccaptald.proffesionalestic.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.proccaptald.proffesionalestic.GameActivity
import com.proccaptald.proffesionalestic.game.LibGDXGame
import com.proccaptald.proffesionalestic.game.actors.AProgress
import com.proccaptald.proffesionalestic.game.manager.MusicManager
import com.proccaptald.proffesionalestic.game.manager.SoundManager
import com.proccaptald.proffesionalestic.game.manager.SpriteManager
import com.proccaptald.proffesionalestic.game.utils.TIME_ANIM
import com.proccaptald.proffesionalestic.game.utils.actor.animHide
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedScreen
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedStage
import com.proccaptald.proffesionalestic.game.utils.region
import com.proccaptald.proffesionalestic.game.utils.runGDX
import com.proccaptald.proffesionalestic.util.isNullOrText
import com.proccaptald.proffesionalestic.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val imgLogo   by lazy { Image(game.splash.logo) }
    private val progGroup by lazy { AProgress(this) }

    override fun show() {
        loadSplashAssets()
        setBackBackground(game.splash.BACKGROUND.region)
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
        addActors(imgLogo, progGroup)
        imgLogo.setBounds(118f, 676f, 421f, 145f)
        progGroup.setBounds(118f, 601f, 420f, 23f)

        imgLogo.addAction(Actions.forever(Actions.sequence(
            Actions.moveBy(0f, 15f, 0.3f, Interpolation.sine),
            Actions.moveBy(0f, -15f, 0.4f, Interpolation.swingOut),
            Actions.delay(0.1f)
        )))
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableAtlasList = mutableListOf(
                SpriteManager.EnumAtlas.SPLASH.data
            )
            loadAtlas()
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.BACKGROUND.data,
                SpriteManager.EnumTexture.BLACK.data,
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
//                    if (progress % 85 == 0) log("progress = $progress%")
                    runGDX { progGroup.progressPercentFlow.value = progress.toFloat() }
                    if (progress == 100) isFinishProgress = true

                    delay((15..20).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress) {
            isFinishProgress = false

            coroutine?.launch {
                game.activity.screenTypeFlow.collect {
                    if (it == GameActivity.ScreenType.Game) toScreen(MainScreen::class.java.name)
                }
            }
        }
    }

    private fun toScreen(screenName: String) {
        stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(screenName) }
    }

}