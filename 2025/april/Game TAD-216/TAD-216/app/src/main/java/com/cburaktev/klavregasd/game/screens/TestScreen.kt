package com.cburaktev.klavregasd.game.screens

import com.cburaktev.klavregasd.game.actors.main.AMainTest
import com.cburaktev.klavregasd.game.utils.Block
import com.cburaktev.klavregasd.game.utils.advanced.AdvancedMainScreen
import com.cburaktev.klavregasd.game.utils.advanced.AdvancedStage

class TestScreen: AdvancedMainScreen() {

    companion object {
        var WIN_COUNT = 0
    }

    override val aMain = AMainTest(this)

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