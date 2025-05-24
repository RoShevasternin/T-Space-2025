package com.cburaktev.klavregasd.game.screens

import com.cburaktev.klavregasd.game.actors.main.AMainTestResult
import com.cburaktev.klavregasd.game.actors.main.AMainTestSelect
import com.cburaktev.klavregasd.game.utils.Block
import com.cburaktev.klavregasd.game.utils.advanced.AdvancedMainScreen
import com.cburaktev.klavregasd.game.utils.advanced.AdvancedStage

class TestResultScreen: AdvancedMainScreen() {

    override val aMain = AMainTestResult(this)

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