package com.ogonechek.putinvestor.game.screens

import com.ogonechek.putinvestor.game.actors.main.AMainOnboard
import com.ogonechek.putinvestor.game.utils.Block
import com.ogonechek.putinvestor.game.utils.advanced.AdvancedScreen
import com.ogonechek.putinvestor.game.utils.advanced.AdvancedStage
import com.ogonechek.putinvestor.game.utils.runGDX
import kotlinx.coroutines.launch

class OnboardScreen: AdvancedScreen() {

    private val aMain = AMainOnboard(this)

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