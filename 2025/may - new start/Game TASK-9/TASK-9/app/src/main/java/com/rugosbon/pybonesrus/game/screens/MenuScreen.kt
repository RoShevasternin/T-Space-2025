package com.rugosbon.pybonesrus.game.screens

import com.rugosbon.pybonesrus.game.actors.main.AMainMenu
import com.rugosbon.pybonesrus.game.utils.Block
import com.rugosbon.pybonesrus.game.utils.actor.setSizeScaled
import com.rugosbon.pybonesrus.game.utils.advanced.AdvancedMainScreen
import com.rugosbon.pybonesrus.game.utils.advanced.AdvancedStage
import com.rugosbon.pybonesrus.game.utils.gdxGame
import com.rugosbon.pybonesrus.game.utils.region

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