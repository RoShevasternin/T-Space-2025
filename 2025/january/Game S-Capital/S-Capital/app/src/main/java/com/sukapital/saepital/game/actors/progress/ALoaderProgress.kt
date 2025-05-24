package com.sukapital.saepital.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.sukapital.saepital.game.actors.Mask
import com.sukapital.saepital.game.utils.WIDTH_UI
import com.sukapital.saepital.game.utils.advanced.AdvancedGroup
import com.sukapital.saepital.game.utils.advanced.AdvancedScreen
import com.sukapital.saepital.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ALoaderProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 552f

    private val assets = screen.game.loader

    private val imgBackground   = Image(assets.progress_back)
    private val imgProgress     = Image(assets.progress)
    private val mask            = Mask(screen, assets.MASK, alphaWidth = WIDTH_UI.toInt())

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addAndFillActor(imgBackground)
        addMask()

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX {
                    imgProgress.x  = percent * onePercentX - LENGTH
                }
            }
        }

    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun AdvancedGroup.addMask() {
       addAndFillActor(mask)
       mask.addAndFillActor(imgProgress)
    }

}