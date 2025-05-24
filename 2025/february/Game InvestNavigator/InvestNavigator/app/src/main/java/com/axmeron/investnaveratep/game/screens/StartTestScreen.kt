package com.axmeron.investnaveratep.game.screens

import com.axmeron.investnaveratep.game.actors.main.AMainStartTest
import com.axmeron.investnaveratep.game.utils.Block
import com.axmeron.investnaveratep.game.utils.advanced.AdvancedScreen
import com.axmeron.investnaveratep.game.utils.advanced.AdvancedStage
import com.axmeron.investnaveratep.game.utils.gdxGame
import com.axmeron.investnaveratep.game.utils.region
import com.axmeron.investnaveratep.game.utils.runGDX
import kotlinx.coroutines.launch

class StartTestScreen: AdvancedScreen() {

    private val aMain = AMainStartTest(this)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.test.region)
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