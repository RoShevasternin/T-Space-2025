package com.tinfenker.capitalnoestroy.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.tinfenker.capitalnoestroy.game.actors.Mask
import com.tinfenker.capitalnoestroy.game.utils.WIDTH_UI
import com.tinfenker.capitalnoestroy.game.utils.advanced.AdvancedGroup
import com.tinfenker.capitalnoestroy.game.utils.advanced.AdvancedScreen
import com.tinfenker.capitalnoestroy.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AYellowProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 516f

    private val assets = screen.game.all

    private val imgBackground   = Image(assets.shkala_back)
    private val imgProgress     = Image(assets.shkala)
    private val mask            = Mask(screen, assets.SHKALA_MASK, alphaWidth = WIDTH_UI.toInt())

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addAndFillActor(imgBackground)
        addMask()

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX {
                    imgProgress.x = percent * onePercentX - LENGTH
                }
            }
        }

        addListener(inputListener())
    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun AdvancedGroup.addMask() {
        addAndFillActor(mask)
        mask.addAndFillActor(imgProgress)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun inputListener() = object : InputListener() {
        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            touchDragged(event, x, y, pointer)
            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            progressPercentFlow.value = when {
                x <= 0 -> 0f
                x >= LENGTH -> 100f
                else -> x / onePercentX
            }

            event?.stop()
        }
    }

}