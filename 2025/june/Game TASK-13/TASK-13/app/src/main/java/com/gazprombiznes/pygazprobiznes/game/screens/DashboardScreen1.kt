package com.gazprombiznes.pygazprobiznes.game.screens

import com.gazprombiznes.pygazprobiznes.game.actors.main.AMainDashboard1
import com.gazprombiznes.pygazprobiznes.game.utils.Block
import com.gazprombiznes.pygazprobiznes.game.utils.advanced.AdvancedMainScreen
import com.gazprombiznes.pygazprobiznes.game.utils.advanced.AdvancedStage
import com.gazprombiznes.pygazprobiznes.game.utils.gdxGame
import com.gazprombiznes.pygazprobiznes.game.utils.region

class DashboardScreen1: AdvancedMainScreen() {

    override val aMain = AMainDashboard1(this)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.BAGA.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
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