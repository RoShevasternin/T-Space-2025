package com.bagazkz.klarebadew.game.screens

import com.bagazkz.klarebadew.game.actors.main.AMainMenu
import com.bagazkz.klarebadew.game.utils.Block
import com.bagazkz.klarebadew.game.utils.advanced.AdvancedMainScreen
import com.bagazkz.klarebadew.game.utils.advanced.AdvancedStage

class MenuScreen: AdvancedMainScreen() {

    override val aMain = AMainMenu(this)

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