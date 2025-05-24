package com.romander.navfenixgater.game.screens

import com.romander.navfenixgater.game.actors.ABottom
import com.romander.navfenixgater.game.actors.ATop
import com.romander.navfenixgater.game.actors.main.AMainCalculator
import com.romander.navfenixgater.game.actors.main.AMainMenu
import com.romander.navfenixgater.game.utils.Block
import com.romander.navfenixgater.game.utils.actor.setSizeScaled
import com.romander.navfenixgater.game.utils.advanced.AdvancedMainScreen
import com.romander.navfenixgater.game.utils.advanced.AdvancedStage

class CalculatorScreen: AdvancedMainScreen() {

    private val aTop    = ATop(this)
    private val aBottom = ABottom(this, ABottom.DefType.Calc)

    override val aMain = AMainCalculator(this, aBottom)

    override fun AdvancedStage.addActorsOnStageTopBack() {
        addATop()
        addABottom()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addATop() {
        addActor(aTop)
        aTop.setSizeScaled(sizeScalerScreen, 692f, 208f)
        aTop.y = viewportBack.screenHeight - aTop.height
    }

    private fun AdvancedStage.addABottom() {
        addActor(aBottom)
        aBottom.setSizeScaled(sizeScalerScreen, 692f, 145f)
    }

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

}