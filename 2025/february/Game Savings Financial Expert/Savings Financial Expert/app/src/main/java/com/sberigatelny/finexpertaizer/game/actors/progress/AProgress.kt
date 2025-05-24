package com.sberigatelny.finexpertaizer.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.sberigatelny.finexpertaizer.game.actors.AMaskGroup
import com.sberigatelny.finexpertaizer.game.utils.advanced.AdvancedGroup
import com.sberigatelny.finexpertaizer.game.utils.advanced.AdvancedScreen
import com.sberigatelny.finexpertaizer.game.utils.gdxGame
import com.sberigatelny.finexpertaizer.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class AProgress(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val LENGTH = 557f

    private val imgProgress = Image(gdxGame.assetsLoader.progress)
    private val mask        = AMaskGroup(screen, gdxGame.assetsLoader.mask)

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addMask()

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX {
                    imgProgress.x = (percent * onePercentX) - LENGTH
                }
            }
        }

        //addListener(inputListener())
    }

    // Actors ---------------------------------------------------

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