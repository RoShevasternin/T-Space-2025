package com.bobrevsc.tkarierraperbola.game.utils.advanced

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.bobrevsc.tkarierraperbola.MainActivity
import com.bobrevsc.tkarierraperbola.game.LibGDXGame
import com.bobrevsc.tkarierraperbola.game.utils.*
import com.bobrevsc.tkarierraperbola.game.utils.actor.animHide
import com.bobrevsc.tkarierraperbola.game.utils.actor.animShow
import com.bobrevsc.tkarierraperbola.game.utils.font.FontGenerator
import com.bobrevsc.tkarierraperbola.util.cancelCoroutinesAll
import com.bobrevsc.tkarierraperbola.util.log
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

    val topViewportBack by lazy { ScreenViewport() }
    val topStageBack    by lazy { AdvancedStage(viewportBack) }

    val topViewportUI by lazy { FitViewport(WIDTH, HEIGHT) }
    val topStageUI    by lazy { AdvancedStage(viewportUI) }

    val inputMultiplexer    = InputMultiplexer()
    val backBackgroundImage = Image()
    val uiBackgroundImage   = Image()
    val disposableSet       = mutableSetOf<Disposable>()
    var coroutine: CoroutineScope? = CoroutineScope(Dispatchers.Default)
        private set

    val drawerUtil by lazy { ShapeDrawerUtil(stageUI.batch) }

    val fontGenerator_Pusia = FontGenerator(FontGenerator.Companion.FontPath.Pusia)

    val listNavigationGroup = mutableListOf<AdvancedGroup>()
    val scalerY by lazy { viewportUI.worldHeight / viewportBack.worldHeight }

    override fun resize(width: Int, height: Int) {
        viewportBack.update(width, height, true)
        viewportUI.update(width, height - MainActivity.statusBarHeight, true)

        topViewportBack.update(width, height, true)
        topViewportUI.update(width, height - MainActivity.statusBarHeight, true)
    }

    override fun show() {
        stageBack.addAndFillActor(backBackgroundImage)
        stageUI.addAndFillActor(uiBackgroundImage)

        stageUI.addActorsOnStageUI()

        Gdx.input.inputProcessor = inputMultiplexer.apply {
            addProcessors(this@AdvancedScreen, stageUI,

                topStageBack, topStageUI)
        }

        //Gdx.input.setCatchKey(Input.Keys.BACK, true)

        game.activity.blockBack = {
            if (listNavigationGroup.size > 1) {
                backGroup()
            } else {
                if (game.navigationManager.isBackStackEmpty()) game.navigationManager.exit()
                else stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
            }
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
        log("dispose AdvancedScreen: ${this::class.java.name.substringAfterLast('.')}")
        disposeAll(
            stageBack, stageUI, drawerUtil,

            topStageBack, topStageUI,

            fontGenerator_Pusia
        )
        disposableSet.disposeAll()
        inputMultiplexer.clear()
        cancelCoroutinesAll(coroutine)
        coroutine = null
    }

//    override fun keyDown(keycode: Int): Boolean {
//        when(keycode) {
//            Input.Keys.BACK -> {
//                if (listNavigationGroup.size > 1) {
//                    backGroup()
//                } else {
//                    if (game.navigationManager.isBackStackEmpty()) game.navigationManager.exit()
//                    else stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
//                }
//            }
//        }
//        return true
//    }

    fun backGroup() {
        listNavigationGroup.removeAt(listNavigationGroup.lastIndex).apply {
            disable()
            animHide(TIME_ANIM)
        }
        listNavigationGroup.last().apply {
            enable()
            animShow(TIME_ANIM)
        }
    }

    fun navigateToGroup(from: AdvancedGroup, to: AdvancedGroup) {
        from.apply {
            disable()
            animHide(TIME_ANIM)
        }
        to.apply {
            enable()
            animShow(TIME_ANIM)
        }
        listNavigationGroup.add(to)
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