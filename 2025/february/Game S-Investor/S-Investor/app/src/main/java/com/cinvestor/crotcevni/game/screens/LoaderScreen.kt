package com.cinvestor.crotcevni.game.screens

import com.cinvestor.crotcevni.game.GLOBAL_isGame
import com.cinvestor.crotcevni.game.actors.main.AMainLoader
import com.cinvestor.crotcevni.game.manager.MusicManager
import com.cinvestor.crotcevni.game.manager.SoundManager
import com.cinvestor.crotcevni.game.manager.SpriteManager
import com.cinvestor.crotcevni.game.utils.*
import com.cinvestor.crotcevni.game.utils.actor.animHide
import com.cinvestor.crotcevni.game.utils.advanced.AdvancedScreen
import com.cinvestor.crotcevni.game.utils.advanced.AdvancedStage
import com.cinvestor.crotcevni.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoaderScreen : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val aMain by lazy { AMainLoader(this) }

    override fun show() {
        loadSplashAssets()
        super.show()
        setBackBackground(gdxGame.assetsLoader.BACKER.region)
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun hideScreen(block: Block) {
        aMain.animHide(TIME_ANIM_SCREEN) {
            block.invoke()
        }
    }

    override fun AdvancedStage.addActorsOnStageBack() {
//        val imgLight = Image(gdxGame.assetsLoader.light)
//        addActor(imgLight)
//        imgLight.setBoundsScaled(sizeScalerScreen, 0f, 218f, 803f, 1306f)
//
//        imgLight.setOrigin(Align.center)
//
//        imgLight.addAction(Acts.forever(
//            Acts.sequence(
//                Acts.scaleTo(1.6f, 1.6f, 0.5f),
//                Acts.scaleTo(1f, 1f, 0.4f),
//            )
//        ))
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(gdxGame.spriteManager) {
            loadableAtlasList = mutableListOf(SpriteManager.EnumAtlas.LOADER.data)
            loadAtlas()
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.L_backer.data,
                SpriteManager.EnumTexture.L_msk.data,
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
                    runGDX { aMain.updatePercent(progress) }
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    delay((15..30).shuffled().first().toLong())
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