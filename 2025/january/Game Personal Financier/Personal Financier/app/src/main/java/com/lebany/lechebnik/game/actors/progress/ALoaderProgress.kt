package com.lebany.lechebnik.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.lebany.lechebnik.game.actors.Mask
import com.lebany.lechebnik.game.utils.WIDTH_UI
import com.lebany.lechebnik.game.utils.advanced.AdvancedGroup
import com.lebany.lechebnik.game.utils.advanced.AdvancedScreen
import com.lebany.lechebnik.game.utils.runGDX
import com.lebany.lechebnik.util.log
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