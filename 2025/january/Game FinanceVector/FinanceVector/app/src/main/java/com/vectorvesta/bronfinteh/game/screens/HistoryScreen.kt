package com.vectorvesta.bronfinteh.game.screens

import com.vectorvesta.bronfinteh.game.actors.main.AMainHistory
import com.vectorvesta.bronfinteh.game.actors.main.AMainMenu
import com.vectorvesta.bronfinteh.game.utils.Block
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedMainScreen
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedStage

class HistoryScreen: AdvancedMainScreen() {

    override val aMain = AMainHistory(this)

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