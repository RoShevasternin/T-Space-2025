package com.smarteca.foundsender.game.screens

import com.smarteca.foundsender.game.actors.main.AMainGlossary
import com.smarteca.foundsender.game.utils.advanced.AdvancedMainScreen
import com.smarteca.foundsender.game.utils.advanced.AdvancedStage
import com.smarteca.foundsender.game.utils.gdxGame
import com.smarteca.foundsender.game.utils.region

class GlossaryScreen: AdvancedMainScreen() {

    override val aMain = AMainGlossary(this)

    override fun show() {
        setBackBackground(gdxGame.assetsLoader.BACKGROUND.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun hideScreen(block: Runnable) {
        aMain.animHideMain { block.run() }
    }

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

}
