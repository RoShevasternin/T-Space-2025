package com.ogonechek.putinvestor.game.screens

import com.ogonechek.putinvestor.game.actors.main.AMainAll
import com.ogonechek.putinvestor.game.utils.Block
import com.ogonechek.putinvestor.game.utils.advanced.AdvancedScreen
import com.ogonechek.putinvestor.game.utils.advanced.AdvancedStage

class AllScreen: AdvancedScreen() {

    private val aMain = AMainAll(this)

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