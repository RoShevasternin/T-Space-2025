package com.startegfin.financester.game.actors.progress

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.startegfin.financester.game.actors.Mask
import com.startegfin.financester.game.utils.WIDTH_UI
import com.startegfin.financester.game.utils.advanced.AdvancedGroup
import com.startegfin.financester.game.utils.advanced.AdvancedScreen

class AGreenProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 592f

    private val assets = screen.game.all

    private val imgBackground   = Image(assets.p_back)
    private val imgProgress     = Image(assets.p_prog)
    private val imgSeparator    = Image(assets.p_separator)
    private val mask            = Mask(screen, assets.MASK, alphaWidth = WIDTH_UI.toInt())

    private val onePercentX = LENGTH / 100f


    override fun addActorsOnGroup() {
        addAndFillActor(imgBackground)
        addMask()
        addActor(imgSeparator)
        imgSeparator.setBounds(-5f,0f,14f,20f)
    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun AdvancedGroup.addMask() {
        addAndFillActor(mask)
        mask.addAndFillActor(imgProgress)
        imgProgress.x = -LENGTH
    }


    fun animToProgress(percent: Float) {
        val endX = percent * onePercentX - LENGTH
        imgProgress.addAction(Actions.moveTo(endX, 0f, 1.2f, Interpolation.sine))

        val endX2 = percent * onePercentX - 5f
        imgSeparator.addAction(Actions.moveTo(endX2, 0f, 1.2f, Interpolation.sine))
    }

}