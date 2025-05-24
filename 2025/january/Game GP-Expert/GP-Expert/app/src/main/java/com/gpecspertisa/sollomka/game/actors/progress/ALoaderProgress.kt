package com.gpecspertisa.sollomka.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.gpecspertisa.sollomka.game.actors.Mask
import com.gpecspertisa.sollomka.game.utils.WIDTH_UI
import com.gpecspertisa.sollomka.game.utils.advanced.AdvancedGroup
import com.gpecspertisa.sollomka.game.utils.advanced.AdvancedScreen
import com.gpecspertisa.sollomka.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ALoaderProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 741f

    private val assets = screen.game.loader

    private val imgBackground   = Image(assets.progress)
    private val imgProgress     = Image(assets.bar)
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