package com.rayscaya.nasjajdenye.game.screens

import com.rayscaya.nasjajdenye.game.actors.main.AMainTutorial
import com.rayscaya.nasjajdenye.game.utils.Block
import com.rayscaya.nasjajdenye.game.utils.advanced.AdvancedMainScreen
import com.rayscaya.nasjajdenye.game.utils.advanced.AdvancedStage
import com.rayscaya.nasjajdenye.game.utils.gdxGame
import com.rayscaya.nasjajdenye.game.utils.region

class TutorialScreen: AdvancedMainScreen() {

    override val aMain = AMainTutorial(this)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND_2.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun AdvancedStage.addActorsOnStageTopBack() {}

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

}