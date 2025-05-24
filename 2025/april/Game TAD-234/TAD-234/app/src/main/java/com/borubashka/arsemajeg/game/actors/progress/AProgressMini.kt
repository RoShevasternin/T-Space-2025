package com.borubashka.arsemajeg.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.borubashka.arsemajeg.game.actors.shader.AMaskGroup
import com.borubashka.arsemajeg.game.utils.advanced.AdvancedGroup
import com.borubashka.arsemajeg.game.utils.advanced.AdvancedScreen
import com.borubashka.arsemajeg.game.utils.gdxGame
import com.borubashka.arsemajeg.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AProgressMini(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 257f

    private val imgProgress = Image(gdxGame.assetsAll.progress_mini)
    private val mask        = AMaskGroup(screen, gdxGame.assetsAll.MASK_MINI)

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