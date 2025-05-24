package com.vectorvesta.bronfinteh.game.screens.delete

import com.vectorvesta.bronfinteh.game.actors.main.delete.AMainDeleteLeasing
import com.vectorvesta.bronfinteh.game.utils.Block
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedMainScreen
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedStage

class DeleteLeasingScreen: AdvancedMainScreen() {

    override val aMain = AMainDeleteLeasing(this)

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