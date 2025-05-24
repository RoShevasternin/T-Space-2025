package com.easyru.track.game.screens

import com.easyru.track.game.actors.main.AMainOnboard
import com.easyru.track.game.utils.Block
import com.easyru.track.game.utils.advanced.AdvancedScreen
import com.easyru.track.game.utils.advanced.AdvancedStage
import com.easyru.track.game.utils.gdxGame
import com.easyru.track.game.utils.region
import com.easyru.track.game.utils.runGDX
import kotlinx.coroutines.launch

class OnboardScreen: AdvancedScreen() {

    private val aMain = AMainOnboard(this)

    override fun show() {
        setBackBackground(gdxGame.assetsLoader.gradient.region)
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