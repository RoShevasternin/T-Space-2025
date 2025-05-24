package com.sburbanaizer.verginiafalesska.game.screens

import com.sburbanaizer.verginiafalesska.game.actors.main.AMainTutorial
import com.sburbanaizer.verginiafalesska.game.utils.Block
import com.sburbanaizer.verginiafalesska.game.utils.advanced.AdvancedScreen
import com.sburbanaizer.verginiafalesska.game.utils.advanced.AdvancedStage
import com.sburbanaizer.verginiafalesska.game.utils.gdxGame

class TutorialScreen: AdvancedScreen() {

    private val aMain = AMainTutorial(this)

    override fun show() {
        setBackBackground(gdxGame.assetsLoader.Loader)
        super.show()
    }

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