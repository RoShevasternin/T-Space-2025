package com.busiknesik.pomeshnek.game.screens

import com.busiknesik.pomeshnek.game.actors.main.AMainMain
import com.busiknesik.pomeshnek.game.utils.Block
import com.busiknesik.pomeshnek.game.utils.advanced.AdvancedScreen
import com.busiknesik.pomeshnek.game.utils.advanced.AdvancedStage

class MainScreen: AdvancedScreen() {

    private val aMain = AMainMain(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

}