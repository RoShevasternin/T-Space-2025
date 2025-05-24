package com.sberigatelny.finexpertaizer.game.screens

import com.sberigatelny.finexpertaizer.game.actors.main.AMainStart
import com.sberigatelny.finexpertaizer.game.utils.Block
import com.sberigatelny.finexpertaizer.game.utils.GameColor
import com.sberigatelny.finexpertaizer.game.utils.advanced.AdvancedMainScreen
import com.sberigatelny.finexpertaizer.game.utils.advanced.AdvancedStage
import com.sberigatelny.finexpertaizer.game.utils.gdxGame
import com.sberigatelny.finexpertaizer.game.utils.region

class StartScreen: AdvancedMainScreen() {

    override val aMain = AMainStart(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.back_1.region)
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