package com.rayscaya.nasjajdenye.game.screens

import com.rayscaya.nasjajdenye.game.actors.main.AMainEdit
import com.rayscaya.nasjajdenye.game.utils.Block
import com.rayscaya.nasjajdenye.game.utils.actor.setSizeScaled
import com.rayscaya.nasjajdenye.game.utils.advanced.AdvancedMainScreen
import com.rayscaya.nasjajdenye.game.utils.advanced.AdvancedStage

class EditScreen: AdvancedMainScreen() {

    override val aMain = AMainEdit(this)

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