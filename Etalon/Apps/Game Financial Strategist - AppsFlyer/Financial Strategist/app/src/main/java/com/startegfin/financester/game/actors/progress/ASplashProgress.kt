package com.startegfin.financester.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.startegfin.financester.game.actors.Mask
import com.startegfin.financester.game.utils.WIDTH_UI
import com.startegfin.financester.game.utils.advanced.AdvancedGroup
import com.startegfin.financester.game.utils.advanced.AdvancedScreen
import com.startegfin.financester.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ASplashProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 672f

    private val assets = screen.game.splash

    private val imgBackground   = Image(assets.back)
    private val imgProgress     = Image(assets.prog)
    private val imgSeparator    = Image(assets.separator)
    private val mask            = Mask(screen, assets.MASK, alphaWidth = WIDTH_UI.toInt())

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addAndFillActor(imgBackground)
        addMask()
        addActor(imgSeparator)
        imgSeparator.setBounds(0f,0f,14f,21f)

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX {
                    imgProgress.x  = percent * onePercentX - LENGTH
                    imgSeparator.x = imgProgress.x + LENGTH - 5f
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