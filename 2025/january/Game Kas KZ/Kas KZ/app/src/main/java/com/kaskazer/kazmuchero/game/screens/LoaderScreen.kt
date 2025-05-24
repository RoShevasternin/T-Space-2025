package com.kaskazer.kazmuchero.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.kaskazer.kazmuchero.R
import com.kaskazer.kazmuchero.game.GLOBAL_isGame
import com.kaskazer.kazmuchero.game.LibGDXGame
import com.kaskazer.kazmuchero.game.manager.MusicManager
import com.kaskazer.kazmuchero.game.manager.SoundManager
import com.kaskazer.kazmuchero.game.manager.SpriteManager
import com.kaskazer.kazmuchero.game.utils.TIME_ANIM
import com.kaskazer.kazmuchero.game.utils.actor.animHide
import com.kaskazer.kazmuchero.game.utils.advanced.AdvancedScreen
import com.kaskazer.kazmuchero.game.utils.advanced.AdvancedStage
import com.kaskazer.kazmuchero.game.utils.region
import com.kaskazer.kazmuchero.util.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoaderScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val imgLogo   by lazy { Image(game.loader.logo) }
    private val imgLoader by lazy { Image(game.loader.loader) }
    private val imgIndir  by lazy { Image(game.loader.indir) }

    override fun show() {
        loadSplashAssets()
        game.activity.setNavBarColor(R.color.ocean)
        setBackBackground(game.loader.background_loader.region)
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
        addActors(imgLogo, imgLoader, imgIndir)
        imgLogo.setBounds(144f,1261f,785f,680f)
        imgLoader.setBounds(424f,681f,195f,195f)
        imgIndir.setBounds(286f,527f,471f,110f)

        imgLoader.apply {
            setOrigin(Align.center)
            addAction(Actions.forever(Actions.rotateBy(-360f, 3.5f, Interpolation.linear)))
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
                SpriteManager.EnumTexture.background_loader.data,
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
                    if (progress == 100) isFinishProgress = true

                    //delay((10..15).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && GLOBAL_isGame) {
            isFinishProgress = false

            log("toGame")
            game.activity.hideWebView()
            toScreen(HelloScreen::class.java.name)
        }
    }

    private fun toScreen(screenName: String) {
         stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(screenName) }
    }

}