package com.finansoviy.gurochek.game.screens

import com.finansoviy.gurochek.game.actors.main.AMainStart
import com.finansoviy.gurochek.game.utils.Block
import com.finansoviy.gurochek.game.utils.GameColor
import com.finansoviy.gurochek.game.utils.advanced.AdvancedMainScreen
import com.finansoviy.gurochek.game.utils.advanced.AdvancedStage
import com.finansoviy.gurochek.game.utils.gdxGame

class StartScreen: AdvancedMainScreen() {

    override val aMain = AMainStart(this)

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