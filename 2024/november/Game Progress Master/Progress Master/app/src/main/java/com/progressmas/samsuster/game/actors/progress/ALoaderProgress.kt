package com.progressmas.samsuster.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.progressmas.samsuster.game.actors.Mask
import com.progressmas.samsuster.game.utils.WIDTH_UI
import com.progressmas.samsuster.game.utils.advanced.AdvancedGroup
import com.progressmas.samsuster.game.utils.advanced.AdvancedScreen
import com.progressmas.samsuster.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ALoaderProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 713f

    private val assets = screen.game.loader

    private val imgBackground   = Image(assets.bar)
    private val imgProgress     = Image(assets.progress)
    private val mask            = Mask(screen, assets.MASK, alphaWidth = WIDTH_UI.toInt())

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addAndFillActors(imgBackground, mask)
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