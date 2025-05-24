package com.ekobioznaher.sugertogerra.game.screens

import com.ekobioznaher.sugertogerra.game.actors.main.AMainQuestion
import com.ekobioznaher.sugertogerra.game.utils.Block
import com.ekobioznaher.sugertogerra.game.utils.advanced.AdvancedScreen
import com.ekobioznaher.sugertogerra.game.utils.advanced.AdvancedStage
import com.ekobioznaher.sugertogerra.game.utils.gdxGame
import com.ekobioznaher.sugertogerra.game.utils.region
import com.ekobioznaher.sugertogerra.game.utils.runGDX
import kotlinx.coroutines.launch

class QuestionScreen: AdvancedScreen() {

    private val aMain = AMainQuestion(this)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.Back.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun hideScreen(block: Block) {
        coroutine?.launch {
            aMain.animHideMain {
                runGDX { block.invoke() }
            }
        }
    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

}