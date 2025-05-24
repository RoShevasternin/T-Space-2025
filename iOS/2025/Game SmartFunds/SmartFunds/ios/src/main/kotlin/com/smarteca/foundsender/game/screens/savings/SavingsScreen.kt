package com.smarteca.foundsender.game.screens.savings

import com.smarteca.foundsender.game.actors.main.savings.AMainSavings
import com.smarteca.foundsender.game.utils.advanced.AdvancedMainScreen
import com.smarteca.foundsender.game.utils.advanced.AdvancedStage
import com.smarteca.foundsender.game.utils.gdxGame
import com.smarteca.foundsender.game.utils.region

class SavingsScreen: AdvancedMainScreen() {

    override val aMain = AMainSavings(this)

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
