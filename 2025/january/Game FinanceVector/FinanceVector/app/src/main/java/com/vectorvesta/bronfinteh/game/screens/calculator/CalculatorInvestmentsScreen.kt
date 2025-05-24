package com.vectorvesta.bronfinteh.game.screens.calculator

import com.vectorvesta.bronfinteh.game.actors.main.calculator.AMainCalculatorInvestments
import com.vectorvesta.bronfinteh.game.utils.Block
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedMainScreen
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedStage

class CalculatorInvestmentsScreen: AdvancedMainScreen() {

    override val aMain = AMainCalculatorInvestments(this)

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