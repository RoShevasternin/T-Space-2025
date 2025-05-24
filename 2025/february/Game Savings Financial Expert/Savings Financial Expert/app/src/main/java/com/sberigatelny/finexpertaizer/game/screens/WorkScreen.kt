package com.sberigatelny.finexpertaizer.game.screens

import com.sberigatelny.finexpertaizer.game.actors.main.AMainWork
import com.sberigatelny.finexpertaizer.game.utils.Block
import com.sberigatelny.finexpertaizer.game.utils.advanced.AdvancedMainScreen
import com.sberigatelny.finexpertaizer.game.utils.advanced.AdvancedStage
import com.sberigatelny.finexpertaizer.game.utils.gdxGame
import com.sberigatelny.finexpertaizer.game.utils.region

class WorkScreen: AdvancedMainScreen() {

    override val aMain = AMainWork(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.back_4.region)
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