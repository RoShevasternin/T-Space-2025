package com.vectorvesta.bronfinteh.game.screens.calculator

import com.vectorvesta.bronfinteh.game.actors.main.calculator.AMainCalculatorLeasing
import com.vectorvesta.bronfinteh.game.utils.Block
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedMainScreen
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedStage

class CalculatorLeasingScreen: AdvancedMainScreen() {

    override val aMain = AMainCalculatorLeasing(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

}