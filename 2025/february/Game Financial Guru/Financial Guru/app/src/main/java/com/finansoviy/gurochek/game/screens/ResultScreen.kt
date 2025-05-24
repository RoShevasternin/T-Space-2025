package com.finansoviy.gurochek.game.screens

import com.finansoviy.gurochek.game.actors.main.AMainResult
import com.finansoviy.gurochek.game.utils.Block
import com.finansoviy.gurochek.game.utils.advanced.AdvancedMainScreen
import com.finansoviy.gurochek.game.utils.advanced.AdvancedStage

class ResultScreen: AdvancedMainScreen() {

    override val aMain = AMainResult(this)

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