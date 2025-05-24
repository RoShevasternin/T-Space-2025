package com.frusune.abvger.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.frusune.abvger.game.actors.Mask
import com.frusune.abvger.game.utils.WIDTH_UI
import com.frusune.abvger.game.utils.advanced.AdvancedGroup
import com.frusune.abvger.game.utils.advanced.AdvancedScreen
import com.frusune.abvger.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ASplashProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 516f

    private val assets = screen.game.splash

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