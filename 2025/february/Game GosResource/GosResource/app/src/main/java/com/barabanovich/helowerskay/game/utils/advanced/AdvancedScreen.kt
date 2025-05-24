package com.barabanovich.helowerskay.game.utils.advanced

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.barabanovich.helowerskay.MainActivity
import com.barabanovich.helowerskay.game.utils.*
import com.barabanovich.helowerskay.game.utils.font.FontGenerator
import com.barabanovich.helowerskay.game.utils.font.FontGenerator.Companion.FontPath
import com.barabanovich.helowerskay.util.cancelCoroutinesAll
import com.barabanovich.helowerskay.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class AdvancedScreen(
    val WIDTH : Float = WIDTH_UI,
    val HEIGHT: Float = HEIGHT_UI
) : ScreenAdapter(), AdvancedInputProcessor {

    val viewportBack = ScreenViewport()
    val stageBack    = AdvancedStage(viewportBack)

    val viewportUI = FitViewport(WIDTH, HEIGHT)
    val stageUI    = AdvancedStage(viewportUI)

    val topViewportBack = ScreenViewport()
    val topStageBack    = AdvancedStage(viewportBack)

    val topViewportUI = FitViewport(WIDTH, HEIGHT)
    val topStageUI    = AdvancedStage(viewportUI)

    val inputMultiplexer = InputMultiplexer()
    val disposableSet    = mutableSetOf<Disposable>()

    var coroutine: CoroutineScope? = CoroutineScope(Dispatchers.Default)
        private set

    val backBackgroundImage = Image()

    val drawerUtil = ShapeDrawerUtil(stageUI.batch)

    val fontGenerator_Bold    = FontGenerator(FontPath.SemiBold)
    val fontGenerator_Regular = FontGenerator(FontPath.Regular)

    val sizeScalerScreen = SizeScaler(SizeScaler.Axis.X, WIDTH)

    override fun resize(width: Int, height: Int) {
        sizeScalerScreen.calculateScale(width.toFloat(), height.toFloat())

        viewportBack.update(width, height, true)
        viewportUI.update(width, height - MainActivity.statusBarHeight, true)
        topViewportBack.update(width, height, true)
        topViewportUI.update(width, height - MainActivity.statusBarHeight, true)
    }

    override fun show() {
        stageBack.addAndFillActor(backBackgroundImage)

        stageBack.addActorsOnStageBack()
        stageUI.addActorsOnStageUI()
        topStageBack.addActorsOnStageTopBack()
        topStageUI.addActorsOnStageTopUI()

        Gdx.input.inputProcessor = inputMultiplexer.apply { addProcessors(this@AdvancedScreen, topStageUI, topStageBack, stageUI, stageBack) }

        gdxGame.activity.blockBack = {
            if (gdxGame.navigationManager.isBackStackEmpty()) gdxGame.navigationManager.exit()
            else hideScreen { gdxGame.navigationManager.back() }
        }
    }

    override fun render(delta: Float) {
        stageBack.render()
        stageUI.render()
        topStageBack.render()
        topStageUI.render()
        drawerUtil.update()
    }

    override fun dispose() {
        log("dispose AdvancedScreen: ${this::class.simpleName}")
        disposeAll(
            stageBack, stageUI, topStageBack, topStageUI, drawerUtil,
            fontGenerator_Bold,
            fontGenerator_Regular,
        )
        disposableSet.disposeAll()
        inputMultiplexer.clear()
        cancelCoroutinesAll(coroutine)
        coroutine = null
    }

    abstract fun AdvancedStage.addActorsOnStageUI()
    open fun AdvancedStage.addActorsOnStageBack() {}
    open fun AdvancedStage.addActorsOnStageTopBack() {}
    open fun AdvancedStage.addActorsOnStageTopUI() {}

    abstract fun hideScreen(block: Block = Block {})

    fun setBackBackground(region: TextureRegion) {
        backBackgroundImage.drawable = TextureRegionDrawable(region)
    }

}