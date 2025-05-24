package com.cryonetpoint.ecsporush.game.screens

import com.cryonetpoint.ecsporush.game.actors.main.AMainMenu
import com.cryonetpoint.ecsporush.game.utils.Block
import com.cryonetpoint.ecsporush.game.utils.advanced.AdvancedMainScreen
import com.cryonetpoint.ecsporush.game.utils.advanced.AdvancedStage

class MenuScreen: AdvancedMainScreen() {

    companion object {
        var SELECTED_LVL_INDEX = 0
    }

    override val aMain = AMainMenu(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        SELECTED_LVL_INDEX = 0
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