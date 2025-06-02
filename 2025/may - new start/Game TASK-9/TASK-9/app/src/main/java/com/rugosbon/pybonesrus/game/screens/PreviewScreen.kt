package com.rugosbon.pybonesrus.game.screens

import com.rugosbon.pybonesrus.game.actors.main.AMainPreview
import com.rugosbon.pybonesrus.game.utils.Block
import com.rugosbon.pybonesrus.game.utils.advanced.AdvancedMainScreen
import com.rugosbon.pybonesrus.game.utils.advanced.AdvancedStage
import com.rugosbon.pybonesrus.game.utils.gdxGame
import com.rugosbon.pybonesrus.game.utils.region

class PreviewScreen: AdvancedMainScreen() {

    override val aMain = AMainPreview(this)

    override fun AdvancedStage.addActorsOnStageUI() {
//        setBackBackground(gdxGame.assetsAll.B.region)
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