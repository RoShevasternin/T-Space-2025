package com.rugosbon.pybonesrus.game.screens

import com.rugosbon.pybonesrus.game.actors.main.AMainNewItem
import com.rugosbon.pybonesrus.game.utils.Block
import com.rugosbon.pybonesrus.game.utils.advanced.AdvancedMainScreen
import com.rugosbon.pybonesrus.game.utils.advanced.AdvancedStage

class NewItemScreen: AdvancedMainScreen() {

    override val aMain = AMainNewItem(this)

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