package com.borubashka.arsemajeg.game.screens

import com.borubashka.arsemajeg.game.actors.main.AMainQuiz
import com.borubashka.arsemajeg.game.utils.Block
import com.borubashka.arsemajeg.game.utils.advanced.AdvancedMainScreen
import com.borubashka.arsemajeg.game.utils.advanced.AdvancedStage

class QuizScreen: AdvancedMainScreen() {

    companion object {
        var WIN_COUNT = 0
    }

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