package com.inveanst.litka.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.inveanst.litka.game.LibGDXGame
import com.inveanst.litka.game.actors.ABackground
import com.inveanst.litka.game.actors.TmpGroup
import com.inveanst.litka.game.manager.MusicManager
import com.inveanst.litka.game.manager.SoundManager
import com.inveanst.litka.game.manager.SpriteManager
import com.inveanst.litka.game.utils.GColor
import com.inveanst.litka.game.utils.TIME_ANIM
import com.inveanst.litka.game.utils.actor.animHide
import com.inveanst.litka.game.utils.actor.animShow
import com.inveanst.litka.game.utils.advanced.AdvancedScreen
import com.inveanst.litka.game.utils.advanced.AdvancedStage
import com.inveanst.litka.util.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false
    private var isFinishAnim     = false

    private val tmpGroup = TmpGroup(this)
    private val imgBackground by lazy { ABackground(this) }

    private val imgPanel  by lazy { Image(game.splash.panel) }
    private val imgInfo   by lazy { Image(game.splash.proidite_test) }
    private val imgLoader by lazy { Image(game.splash.loader) }

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
        addAndFillActors(imgBackground, tmpGroup)

        tmpGroup.apply {
            addActors(imgPanel, imgInfo, imgLoader)
            imgPanel.setBounds(28f,269f,319f,335f)
            imgInfo.apply {
                setBounds(52f,376f,271f,208f)
                addAction(Actions.forever(Actions.sequence(
                    Actions.moveBy(0f, 7f, 0.35f, Interpolation.sineIn),
                    Actions.moveBy(0f, -7f, 0.35f, Interpolation.sineOut),
                )))
            }
            imgLoader.apply {
                setBounds(162f,295f,51f,51f)
                setOrigin(Align.center)
                addAction(Actions.forever(Actions.rotateBy(-360f, 2f, Interpolation.linear)))
            }

            tmpGroup.addAction(Actions.sequence(
                Actions.delay((10..20).random() / 10f),
                Actions.run {
                    imgInfo.animHide(TIME_ANIM) {
                        imgInfo.drawable = TextureRegionDrawable(game.splash.post_gotovo)
                        imgInfo.animShow(TIME_ANIM)
                    }
                },
                Actions.delay(TIME_ANIM * (3..4).random()),
                Actions.run { isFinishAnim = true }
            ))
        }

    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableAtlasList = mutableListOf(
                SpriteManager.EnumAtlas.SPLASH.data
            )
            loadAtlas()
//            loadableTexturesList = mutableListOf(
//                SpriteManager.EnumTexture.SPLASH_BACKGROUND.data,
//                SpriteManager.EnumTexture.SWEET_TRIO_COMBO.data,
//            )
//            loadTexture()
        }
        game.assetManager.finishLoading()
        game.spriteManager.initAtlas()
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
                    if (progress % 99 == 0) log("progress = $progress%")
//                    runGDX { lblPercent.setText("${progress.toInt()}%") }
                    if (progress == 100) isFinishProgress = true

                    //delay((8..14).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && isFinishAnim && game.isGame) {
            isFinishProgress = false

            log("toGame")
            game.activity.removeWeb()
            toScreen(MenuScreen::class.java.name)
        }
    }

    private fun toScreen(screenName: String) {
        tmpGroup.animHide(TIME_ANIM) { game.navigationManager.navigate(screenName) }
    }

}