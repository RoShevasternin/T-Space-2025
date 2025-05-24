package com.clickandbuild.motors.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.clickandbuild.motors.game.utils.WIDTH_UI
import com.clickandbuild.motors.game.utils.advanced.AdvancedGroup
import com.clickandbuild.motors.game.utils.advanced.AdvancedScreen
import com.clickandbuild.motors.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AFactoryProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 532f

    private val assets = screen.game.all

    private val imgProgress     = Image(assets.orange_prog)
    private val mask            = AMaskGroup(screen, assets.mask)

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addAndFillActor(Image(assets.blue_prog))
        addActor(mask)
        mask.setBounds(14f, 20f, 532f, 82f)
        mask.addAndFillActor(imgProgress)

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX {
                    imgProgress.x = (percent * onePercentX) - LENGTH
                }
            }
        }

    }

}