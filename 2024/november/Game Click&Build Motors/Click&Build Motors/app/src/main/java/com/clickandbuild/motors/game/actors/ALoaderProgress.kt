package com.clickandbuild.motors.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.clickandbuild.motors.game.utils.WIDTH_UI
import com.clickandbuild.motors.game.utils.advanced.AdvancedGroup
import com.clickandbuild.motors.game.utils.advanced.AdvancedScreen
import com.clickandbuild.motors.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ALoaderProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 666f

    private val assets = screen.game.loader

    private val imgProgress     = Image(assets.loader_progress)
    private val mask            = AMaskGroup(screen, assets.loader_mask)

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addAndFillActor(Image(assets.loader_frame))
        addActor(mask)
        mask.setBounds(17f, 17f, 666f, 102f)
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