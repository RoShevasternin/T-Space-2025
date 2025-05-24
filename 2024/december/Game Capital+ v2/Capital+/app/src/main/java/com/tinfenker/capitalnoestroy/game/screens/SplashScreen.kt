package com.tinfenker.capitalnoestroy.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.tinfenker.capitalnoestroy.game.LibGDXGame
import com.tinfenker.capitalnoestroy.game.actors.TmpGroup
import com.tinfenker.capitalnoestroy.game.actors.progress.ASplashProgress
import com.tinfenker.capitalnoestroy.game.manager.MusicManager
import com.tinfenker.capitalnoestroy.game.manager.SoundManager
import com.tinfenker.capitalnoestroy.game.manager.SpriteManager
import com.tinfenker.capitalnoestroy.game.utils.GColor
import com.tinfenker.capitalnoestroy.game.utils.TIME_ANIM
import com.tinfenker.capitalnoestroy.game.utils.actor.animHide
import com.tinfenker.capitalnoestroy.game.utils.advanced.AdvancedScreen
import com.tinfenker.capitalnoestroy.game.utils.advanced.AdvancedStage
import com.tinfenker.capitalnoestroy.game.utils.font.FontParameter
import com.tinfenker.capitalnoestroy.game.utils.runGDX
import com.tinfenker.capitalnoestroy.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class SplashScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val fontParameter = FontParameter()
    private val font45        = fontGenerator_Roboto_Medium.generateFont(fontParameter.setCharacters("Почти готово.").setSize(45))

    private val ls45 = Label.LabelStyle(font45, GColor.gray)

    private val imgLogo        by lazy { Image(game.splash.logo) }
    private val progressSplash by lazy { ASplashProgress(this) }
    private val imgPanel1      by lazy { Image(game.splash.panel) }
    private val imgPanel2      by lazy { Image(game.splash.panel) }
    private val lblTitle       by lazy { Label("Почти готово", ls45) }

    private val tmpGroup = TmpGroup(this)

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

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(imgPanel1)
        imgPanel1.setBounds(77f,889f,620f,160f)
        addActor(imgLogo)
        imgLogo.setBounds(180f,941f,414f,57f)

        addAndFillActor(tmpGroup)
        tmpGroup.apply {
            addActors(imgPanel2, progressSplash, lblTitle)
            imgPanel2.setBounds(77f,630f,620f,199f)
            progressSplash.setBounds(129f,682f,516f,21f)
            lblTitle.apply {
                setBounds(243f,744f,289f,32f)
                setAlignment(Align.center)
            }
        }

        coroutine?.launch {
            val timeDelay = 150L
            while (isActive) {
                delay(timeDelay * 3)
                runGDX { lblTitle.setText("Почти готово.") }
                delay(timeDelay)
                runGDX { lblTitle.setText("Почти готово..") }
                delay(timeDelay)
                runGDX { lblTitle.setText("Почти готово...") }
                delay(timeDelay * 3)
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

                    delay((10..15).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && game.isGame) {
            isFinishProgress = false

            log("toGame")
            game.activity.hideWebView()
            toScreen(ListScreen::class.java.name)
        }
    }

    private fun toScreen(screenName: String) {
         tmpGroup.animHide(TIME_ANIM) { game.navigationManager.navigate(screenName) }
    }

}