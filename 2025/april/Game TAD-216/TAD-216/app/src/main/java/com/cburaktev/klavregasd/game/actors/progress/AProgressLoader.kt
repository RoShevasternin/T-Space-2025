package com.cburaktev.klavregasd.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.cburaktev.klavregasd.game.actors.shader.AMaskGroup
import com.cburaktev.klavregasd.game.utils.advanced.AdvancedGroup
import com.cburaktev.klavregasd.game.utils.advanced.AdvancedScreen
import com.cburaktev.klavregasd.game.utils.gdxGame
import com.cburaktev.klavregasd.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AProgressLoader(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val LENGTH = 558f

    private val imgProgress = Image(gdxGame.assetsLoader.loader)
    private val mask        = AMaskGroup(screen, gdxGame.assetsLoader.BACKGROUND_LOADER)

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