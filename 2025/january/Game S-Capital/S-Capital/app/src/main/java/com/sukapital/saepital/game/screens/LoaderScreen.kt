package com.sukapital.saepital.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.sukapital.saepital.game.G_isGame
import com.sukapital.saepital.game.LibGDXGame
import com.sukapital.saepital.game.actors.progress.ALoaderProgress
import com.sukapital.saepital.game.manager.MusicManager
import com.sukapital.saepital.game.manager.SoundManager
import com.sukapital.saepital.game.manager.SpriteManager
import com.sukapital.saepital.game.utils.GColor
import com.sukapital.saepital.game.utils.TIME_ANIM
import com.sukapital.saepital.game.utils.actor.animHide
import com.sukapital.saepital.game.utils.advanced.AdvancedScreen
import com.sukapital.saepital.game.utils.advanced.AdvancedStage
import com.sukapital.saepital.game.utils.font.FontParameter
import com.sukapital.saepital.game.utils.region
import com.sukapital.saepital.game.utils.runGDX
import com.sukapital.saepital.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class LoaderScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val fontParameter = FontParameter()
    private val font44        = fontGenerator_Roboto_Bold.generateFont(fontParameter.setCharacters("Почти готово.").setSize(44))

    private val ls44 = Label.LabelStyle(font44, GColor.dark)

    private val imgPanel       by lazy { Image(game.loader.panel_9) }
    private val imgLogo        by lazy { Image(game.loader.logo) }
    private val lblTitle       by lazy { Label("Почти готово", ls44) }
    private val progressSplash by lazy { ALoaderProgress(this) }

    override fun show() {
        loadSplashAssets()
        setBackBackground(game.loader.SPLASH_LOADER.region)
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
        addActors(imgPanel, imgLogo, lblTitle, progressSplash)
        imgPanel.setBounds(55f,469f,717f,770f)
        imgLogo.setBounds(222f,890f,385f,101f)
        lblTitle.setBounds(55f,785f,717f,57f)
        lblTitle.setAlignment(Align.center)
        progressSplash.setBounds(138f,721f,552f,23f)

        coroutine?.launch {
            val timeDelay = 120L
            while (isActive) {
                delay(timeDelay * 2)
                runGDX { lblTitle.setText("Почти готово.") }
                delay(timeDelay)
                runGDX { lblTitle.setText("Почти готово..") }
                delay(timeDelay)
                runGDX { lblTitle.setText("Почти готово...") }
                delay(timeDelay * 2)
                runGDX { lblTitle.setText("Почти готово..") }
                delay(timeDelay)
                runGDX { lblTitle.setText("Почти готово.") }
                delay(timeDelay)
                runGDX { lblTitle.setText("Почти готово") }
            }
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
                SpriteManager.EnumTexture.SPLASH_MASK.data,
                SpriteManager.EnumTexture.SPLASH_LOADER.data,
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

                    delay((10..15).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && G_isGame) {
            isFinishProgress = false

            log("toGame")
            game.activity.hideWebView()
            toScreen(MainScreen::class.java.name)
        }
    }

    private fun toScreen(screenName: String) {
         stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(screenName) }
    }

}