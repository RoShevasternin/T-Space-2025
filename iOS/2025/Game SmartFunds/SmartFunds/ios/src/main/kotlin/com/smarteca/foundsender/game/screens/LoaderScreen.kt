package com.smarteca.foundsender.game.screens

import com.smarteca.foundsender.ConfigState
import com.smarteca.foundsender.GLOBAL_ConfigState
import com.smarteca.foundsender.game.actors.main.AMainLoader
import com.smarteca.foundsender.game.manager.MusicManager
import com.smarteca.foundsender.game.manager.SoundManager
import com.smarteca.foundsender.game.manager.SpriteManager
import com.smarteca.foundsender.game.utils.*
import com.smarteca.foundsender.game.utils.actor.animHide
import com.smarteca.foundsender.game.utils.advanced.AdvancedScreen
import com.smarteca.foundsender.game.utils.advanced.AdvancedStage
import com.smarteca.foundsender.util.AppsFlyerManager
import com.smarteca.foundsender.util.permission.PermissionATT
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.robovm.apple.apptrackingtransparency.ATTrackingManagerAuthorizationStatus

class LoaderScreen : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    //private val aBackground by lazy { ABackground(this, gdxGame.assetsLoader.BACKGROUND) }
    private val aMain by lazy { AMainLoader(this) }

    override fun show() {
        loadSplashAssets()
        super.show()
        setBackBackground(gdxGame.assetsLoader.BACKGROUND.region)
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun hideScreen(block: Runnable) {
        aMain.animHide(TIME_ANIM_SCREEN) {
            block.run()
        }
    }

    override fun AdvancedStage.addActorsOnStageBack() {
        //addBackground()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    // Actors Back------------------------------------------------------------------------

//    private fun AdvancedStage.addBackground() {
//        addActor(aBackground)
//
//        val screenRatio = viewportBack.screenWidth / viewportBack.screenHeight
//        val imageRatio  = (WIDTH_UI / HEIGHT_UI)
//
//        val scale = if (screenRatio > imageRatio) WIDTH_UI / viewportBack.screenWidth else HEIGHT_UI / viewportBack.screenHeight
//        aBackground.setSize(WIDTH_UI / scale, HEIGHT_UI / scale)
//
//        aBackground.animToNewTexture(gdxGame.assetsLoader.BACKGROUND, TIME_ANIM_SCREEN)
//        gdxGame.currentBackground = gdxGame.assetsLoader.BACKGROUND
//    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(gdxGame.spriteManager) {
            loadableAtlasList = mutableListOf(SpriteManager.EnumAtlas.LOADER.data)
            loadAtlas()
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.LOADER_BACKGROUND.data,
                SpriteManager.EnumTexture.LOADER_MASK.data,
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
                    runGDX { aMain.setPercent(progress.toFloat()) }
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    //delay((20..65).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress) {
            when(GLOBAL_ConfigState) {
                ConfigState.ToGame -> {
                    isFinishProgress = false
                    navTo(DashboardScreen::class.java.name)
                }
                ConfigState.ToAccessOrStartAppsFlyer -> {
                    isFinishProgress = false
                    if (PermissionATT.getATTStatus() == ATTrackingManagerAuthorizationStatus.NotDetermined) {
                        navTo(AccessScreen::class.java.name)
                    } else {
                        AppsFlyerManager.startWithCompletionHandler()
                    }
                } else -> {}
            }

        }
    }

    private fun navTo(screenName: String) {
        hideScreen {
            gdxGame.navigationManager.navigate(screenName)
        }
    }


}
