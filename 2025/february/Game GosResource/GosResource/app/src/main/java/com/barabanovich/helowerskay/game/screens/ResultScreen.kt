package com.barabanovich.helowerskay.game.screens

import com.barabanovich.helowerskay.game.actors.main.AMainResult
import com.barabanovich.helowerskay.game.utils.Block
import com.barabanovich.helowerskay.game.utils.advanced.AdvancedMainScreen
import com.barabanovich.helowerskay.game.utils.advanced.AdvancedStage
import com.barabanovich.helowerskay.game.utils.gdxGame
import com.barabanovich.helowerskay.game.utils.region

class ResultScreen: AdvancedMainScreen() {

    override val aMain = AMainResult(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.background_2.region)
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