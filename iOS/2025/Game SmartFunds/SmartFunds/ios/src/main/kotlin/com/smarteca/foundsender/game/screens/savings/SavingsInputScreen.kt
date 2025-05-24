package com.smarteca.foundsender.game.screens.savings

import com.smarteca.foundsender.game.actors.main.savings.AMainSavingsInput
import com.smarteca.foundsender.game.utils.advanced.AdvancedMainScreen
import com.smarteca.foundsender.game.utils.advanced.AdvancedStage
import com.smarteca.foundsender.game.utils.gdxGame
import com.smarteca.foundsender.game.utils.region

class SavingsInputScreen: AdvancedMainScreen() {

    override val aMain = AMainSavingsInput(this)

    override fun show() {
        setBackBackground(gdxGame.assetsLoader.BACKGROUND.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun hideScreen(block: Runnable) {
        aMain.animHideMain { block.run() }
    }

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

}
