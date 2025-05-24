package com.frusune.abvger.game.utils.advanced

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.frusune.abvger.game.LibGDXGame
import com.frusune.abvger.game.utils.*
import com.frusune.abvger.game.utils.actor.animHide
import com.frusune.abvger.game.utils.font.FontGenerator
import com.frusune.abvger.util.cancelCoroutinesAll
import com.frusune.abvger.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class AdvancedScreen(
    val WIDTH : Float = WIDTH_UI,
    val HEIGHT: Float = HEIGHT_UI
) : ScreenAdapter(), AdvancedInputProcessor {

    abstract val game: LibGDXGame

    val viewportBack by lazy { ScreenViewport() }
    val stageBack    by lazy { AdvancedStage(viewportBack) }

    val viewportUI by lazy { FitViewport(WIDTH, HEIGHT) }
    val stageUI    by lazy { AdvancedStage(viewportUI) }

    val inputMultiplexer    = InputMultiplexer()
    val backBackgroundImage = Image()
    val uiBackgroundImage   = Image()
    val disposableSet       = mutableSetOf<Disposable>()
    var coroutine: CoroutineScope? = CoroutineScope(Dispatchers.Default)
        private set

    val drawerUtil by lazy { ShapeDrawerUtil(stageUI.batch) }

    val fontGenerator_Roboto_Bold    = FontGenerator(FontGenerator.Companion.FontPath.Roboto_Bold)
    val fontGenerator_Roboto_Medium  = FontGenerator(FontGenerator.Companion.FontPath.Roboto_Medium)
    val fontGenerator_Roboto_Regular = FontGenerator(FontGenerator.Companion.FontPath.Roboto_Regular)

    override fun resize(width: Int, height: Int) {
        viewportBack.update(width, height, true)
        viewportUI.update(width, height, true)
    }

    override fun show() {
        stageBack.addAndFillActor(backBackgroundImage)
        stageUI.addAndFillActor(uiBackgroundImage)

        stageUI.addActorsOnStageUI()

        Gdx.input.inputProcessor = inputMultiplexer.apply { addProcessors(this@AdvancedScreen, stageUI) }

        game.activity.blockBack = {
            if (game.navigationManager.isBackStackEmpty()) game.navigationManager.exit()
            else stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
        }
    }

    override fun render(delta: Float) {
        stageBack.render()
        stageUI.render()
        drawerUtil.update()
    }

    override fun dispose() {
        log("dispose AdvancedScreen: ${this::class.java.name.substringAfterLast('.')}")
        disposeAll(
            stageBack, stageUI, drawerUtil,

            fontGenerator_Roboto_Bold,
            fontGenerator_Roboto_Medium,
            fontGenerator_Roboto_Regular,
        )
        disposableSet.disposeAll()
        inputMultiplexer.clear()
        cancelCoroutinesAll(coroutine)
        coroutine = null
    }

    open fun AdvancedStage.addActorsOnStageUI() {}


    fun setBackBackground(region: TextureRegion) {
        backBackgroundImage.drawable = TextureRegionDrawable(region)
    }

    fun setUIBackground(texture: TextureRegion) {
        uiBackgroundImage.drawable = TextureRegionDrawable(texture)
    }

    fun setBackgrounds(backRegion: TextureRegion, uiRegion: TextureRegion = backRegion) {
        setBackBackground(backRegion)
        setUIBackground(uiRegion)
    }

}