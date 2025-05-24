package com.vectorvesta.bronfinteh.game.screens.calculator

import com.vectorvesta.bronfinteh.game.actors.main.calculator.AMainCalculatorDeposit
import com.vectorvesta.bronfinteh.game.actors.main.calculator.AMainCalculatorLoan
import com.vectorvesta.bronfinteh.game.utils.Block
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedMainScreen
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedStage

class CalculatorDepositScreen: AdvancedMainScreen() {

    override val aMain = AMainCalculatorDeposit(this)

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