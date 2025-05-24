package com.gosinventarytet.debagovich.game.utils.advanced

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.gosinventarytet.debagovich.MainActivity
import com.gosinventarytet.debagovich.game.utils.*
import com.gosinventarytet.debagovich.game.utils.font.FontGenerator
import com.gosinventarytet.debagovich.game.utils.font.FontGenerator.Companion.FontPath
import com.gosinventarytet.debagovich.util.cancelCoroutinesAll
import com.gosinventarytet.debagovich.util.log
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

    val inputMultiplexer = InputMultiplexer()
    val disposableSet    = mutableSetOf<Disposable>()

    var coroutine: CoroutineScope? = CoroutineScope(Dispatchers.Default)
        private set

    val backBackgroundImage = Image()

    val drawerUtil = ShapeDrawerUtil(stageUI.batch)

    val fontGenerator_Bold   = FontGenerator(FontPath.Bold)
    val fontGenerator_Medium = FontGenerator(FontPath.Medium)

    val sizeScalerScreen = SizeScaler(SizeScaler.Axis.X, WIDTH)

    override fun resize(width: Int, height: Int) {
        sizeScalerScreen.calculateScale(width.toFloat(), height.toFloat())

        viewportBack.update(width, height, true)
        viewportUI.update(width, height - MainActivity.statusBarHeight, true)
    }

    override fun show() {
        stageBack.addAndFillActor(backBackgroundImage)

        stageBack.addActorsOnStageBack()
        stageUI.addActorsOnStageUI()

        Gdx.input.inputProcessor = inputMultiplexer.apply { addProcessors(this@AdvancedScreen, stageUI, stageBack) }

        gdxGame.activity.blockBack = {
            if (gdxGame.navigationManager.isBackStackEmpty()) gdxGame.navigationManager.exit()
            else hideScreen { gdxGame.navigationManager.back() }
        }
    }

    override fun render(delta: Float) {
        stageBack.render()
        stageUI.render()
        drawerUtil.update()
    }

    override fun dispose() {
        log("dispose AdvancedScreen: ${this::class.simpleName}")
        disposeAll(
            stageBack, stageUI, drawerUtil,
            fontGenerator_Bold,
            fontGenerator_Medium,
        )
        disposableSet.disposeAll()
        inputMultiplexer.clear()
        cancelCoroutinesAll(coroutine)
        coroutine = null
    }

    abstract fun AdvancedStage.addActorsOnStageUI()
    open fun AdvancedStage.addActorsOnStageBack() {}

    abstract fun hideScreen(block: Block = Block {})

    fun setBackBackground(region: TextureRegion) {
        backBackgroundImage.drawable = TextureRegionDrawable(region)
    }

}