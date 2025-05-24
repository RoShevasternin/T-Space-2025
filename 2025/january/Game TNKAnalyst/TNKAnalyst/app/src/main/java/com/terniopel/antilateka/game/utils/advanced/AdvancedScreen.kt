package com.terniopel.antilateka.game.utils.advanced

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.terniopel.antilateka.game.utils.*
import com.terniopel.antilateka.game.utils.font.FontGenerator
import com.terniopel.antilateka.game.utils.font.FontGenerator.Companion.FontPath
import com.terniopel.antilateka.util.cancelCoroutinesAll
import com.terniopel.antilateka.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class AdvancedScreen(
    val WIDTH : Float = WIDTH_UI,
    val HEIGHT: Float = HEIGHT_UI
) : ScreenAdapter(), AdvancedInputProcessor {
    
    val viewportBack by lazy { ScreenViewport() }
    val stageBack    by lazy { AdvancedStage(viewportBack) }

    val viewportUI by lazy { FitViewport(WIDTH, HEIGHT) }
    val stageUI    by lazy { AdvancedStage(viewportUI) }

    val inputMultiplexer = InputMultiplexer()

    val backBackgroundImage = Image()

    val disposableSet = mutableSetOf<Disposable>()
    var coroutine: CoroutineScope? = CoroutineScope(Dispatchers.Default)
        private set

    val drawerUtil by lazy { ShapeDrawerUtil(stageUI.batch) }

    val fontGenerator_InterBold    = FontGenerator(FontPath.InterBold)
    val fontGenerator_InterRegular = FontGenerator(FontPath.InterRegular)

    override fun resize(width: Int, height: Int) {
        viewportBack.update(width, height, true)
        viewportUI.update(width, height, true)
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
        log("dispose AdvancedScreen: ${this::class.java.name.substringAfterLast('.')}")
        disposeAll(
            stageBack, stageUI, drawerUtil,
            fontGenerator_InterBold,
            fontGenerator_InterRegular,
        )
        disposableSet.disposeAll()
        inputMultiplexer.clear()
        cancelCoroutinesAll(coroutine)
        coroutine = null
    }

    abstract fun AdvancedStage.addActorsOnStageUI()

    open fun AdvancedStage.addActorsOnStageBack() {}

    abstract fun hideScreen(block: Block = Block {  })

    fun setBackBackground(region: TextureRegion) {
        backBackgroundImage.drawable = TextureRegionDrawable(region)
    }

}