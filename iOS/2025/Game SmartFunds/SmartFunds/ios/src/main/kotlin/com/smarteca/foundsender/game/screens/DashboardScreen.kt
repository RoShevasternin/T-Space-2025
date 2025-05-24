package com.smarteca.foundsender.game.screens

import com.smarteca.foundsender.GLOBAL_isFirstOpenGameScreen
import com.smarteca.foundsender.game.actors.main.AMainDashboard
import com.smarteca.foundsender.game.utils.advanced.AdvancedMainScreen
import com.smarteca.foundsender.game.utils.advanced.AdvancedStage
import com.smarteca.foundsender.game.utils.gdxGame
import com.smarteca.foundsender.game.utils.log
import com.smarteca.foundsender.game.utils.region
import com.smarteca.foundsender.util.AppsFlyerManager
import com.smarteca.foundsender.util.permission.PermissionATT

class DashboardScreen: AdvancedMainScreen() {

    override val aMain = AMainDashboard(this)

    override fun show() {
        GLOBAL_isFirstOpenGameScreen = true
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
