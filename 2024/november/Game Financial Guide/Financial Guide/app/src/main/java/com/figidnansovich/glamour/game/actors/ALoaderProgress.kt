package com.figidnansovich.glamour.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.figidnansovich.glamour.game.utils.WIDTH_UI
import com.figidnansovich.glamour.game.utils.advanced.AdvancedGroup
import com.figidnansovich.glamour.game.utils.advanced.AdvancedScreen
import com.figidnansovich.glamour.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ALoaderProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 712f

    private val assets = screen.game.loader

    private val imgProgress     = Image(assets.loader)
    private val mask            = Mask(screen, assets.maska, alphaWidth = WIDTH_UI.toInt())

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addAndFillActor(mask)
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