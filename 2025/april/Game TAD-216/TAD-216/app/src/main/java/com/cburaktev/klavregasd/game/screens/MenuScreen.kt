package com.cburaktev.klavregasd.game.screens

import com.cburaktev.klavregasd.game.actors.main.AMainMenu
import com.cburaktev.klavregasd.game.utils.Block
import com.cburaktev.klavregasd.game.utils.actor.setSizeScaled
import com.cburaktev.klavregasd.game.utils.advanced.AdvancedMainScreen
import com.cburaktev.klavregasd.game.utils.advanced.AdvancedStage
import com.cburaktev.klavregasd.game.utils.gdxGame
import com.cburaktev.klavregasd.game.utils.region

class MenuScreen: AdvancedMainScreen() {

    override val aMain = AMainMenu(this)

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