package com.gosinventarytet.debagovich.game.screens

import com.gosinventarytet.debagovich.game.actors.main.AMainMenu
import com.gosinventarytet.debagovich.game.utils.Block
import com.gosinventarytet.debagovich.game.utils.advanced.AdvancedMainScreen
import com.gosinventarytet.debagovich.game.utils.advanced.AdvancedStage

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