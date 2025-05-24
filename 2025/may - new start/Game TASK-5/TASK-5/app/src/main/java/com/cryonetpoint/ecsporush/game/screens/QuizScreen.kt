package com.cryonetpoint.ecsporush.game.screens

import com.cryonetpoint.ecsporush.game.actors.main.AMainQuiz
import com.cryonetpoint.ecsporush.game.utils.Block
import com.cryonetpoint.ecsporush.game.utils.advanced.AdvancedMainScreen
import com.cryonetpoint.ecsporush.game.utils.advanced.AdvancedStage

class QuizScreen: AdvancedMainScreen() {

    override val aMain = AMainQuiz(this)

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