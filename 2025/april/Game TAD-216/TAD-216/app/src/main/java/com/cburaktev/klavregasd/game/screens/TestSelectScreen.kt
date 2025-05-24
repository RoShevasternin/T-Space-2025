package com.cburaktev.klavregasd.game.screens

import com.cburaktev.klavregasd.game.actors.main.AMainTestSelect
import com.cburaktev.klavregasd.game.utils.Block
import com.cburaktev.klavregasd.game.utils.advanced.AdvancedMainScreen
import com.cburaktev.klavregasd.game.utils.advanced.AdvancedStage

class TestSelectScreen: AdvancedMainScreen() {

    companion object {
        var SELECTED_TEST_INDEX = 0
    }

    override val aMain = AMainTestSelect(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        //setBackBackground(gdxGame.assetsAll.BACKGROUND_PREVIEW.region)
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