package com.easyru.track.game.screens

import com.easyru.track.game.actors.main.AMainTest
import com.easyru.track.game.utils.Block
import com.easyru.track.game.utils.advanced.AdvancedScreen
import com.easyru.track.game.utils.advanced.AdvancedStage
import com.easyru.track.game.utils.gdxGame
import com.easyru.track.game.utils.region
import com.easyru.track.game.utils.runGDX
import kotlinx.coroutines.launch

class TestScreen: AdvancedScreen() {

    companion object {
        var ANSWER_COUNT = 0
    }

    private val aMain = AMainTest(this)

    override fun show() {
        ANSWER_COUNT = 0
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