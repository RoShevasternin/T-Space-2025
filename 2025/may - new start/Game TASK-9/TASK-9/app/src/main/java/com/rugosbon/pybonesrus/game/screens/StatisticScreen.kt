package com.rugosbon.pybonesrus.game.screens

import com.rugosbon.pybonesrus.game.actors.main.AMainStatistic
import com.rugosbon.pybonesrus.game.utils.Block
import com.rugosbon.pybonesrus.game.utils.advanced.AdvancedMainScreen
import com.rugosbon.pybonesrus.game.utils.advanced.AdvancedStage

class StatisticScreen: AdvancedMainScreen() {

    override val aMain = AMainStatistic(this)

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