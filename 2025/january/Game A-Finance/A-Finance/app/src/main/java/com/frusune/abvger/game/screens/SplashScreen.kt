package com.frusune.abvger.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.frusune.abvger.game.GLOBAL_isGame
import com.frusune.abvger.game.LibGDXGame
import com.frusune.abvger.game.actors.TmpGroup
import com.frusune.abvger.game.actors.progress.ASplashProgress
import com.frusune.abvger.game.manager.MusicManager
import com.frusune.abvger.game.manager.SoundManager
import com.frusune.abvger.game.manager.SpriteManager
import com.frusune.abvger.game.utils.GColor
import com.frusune.abvger.game.utils.TIME_ANIM
import com.frusune.abvger.game.utils.actor.animHide
import com.frusune.abvger.game.utils.advanced.AdvancedScreen
import com.frusune.abvger.game.utils.advanced.AdvancedStage
import com.frusune.abvger.game.utils.font.FontParameter
import com.frusune.abvger.game.utils.region
import com.frusune.abvger.game.utils.runGDX
import com.frusune.abvger.util.log
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
        setBackBackground(game.splash.backg.region)
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
        imgPanel1.setBounds(211f,844f,353f,252f)
        addActor(imgLogo)
        imgLogo.setBounds(263f,893f,249f,152f)

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
                SpriteManager.EnumAtlas.SPLASH.data,
            )
            loadAtlas()
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.SPLASH_MASK.data,
                SpriteManager.EnumTexture.backg.data,
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
        if (isFinishProgress && GLOBAL_isGame) {
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