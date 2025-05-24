package com.rayscaya.nasjajdenye.game.screens

import com.rayscaya.nasjajdenye.game.actors.ATop
import com.rayscaya.nasjajdenye.game.actors.main.AMainMenu
import com.rayscaya.nasjajdenye.game.utils.Block
import com.rayscaya.nasjajdenye.game.utils.GameColor
import com.rayscaya.nasjajdenye.game.utils.actor.setSizeScaled
import com.rayscaya.nasjajdenye.game.utils.advanced.AdvancedMainScreen
import com.rayscaya.nasjajdenye.game.utils.advanced.AdvancedStage
import com.rayscaya.nasjajdenye.game.utils.gdxGame

class MenuScreen: AdvancedMainScreen() {

    private val aTop = ATop(this)

    override val aMain = AMainMenu(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun AdvancedStage.addActorsOnStageTopBack() {
        addATop()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

    // Actors Top Back------------------------------------------------------------------------

    private fun AdvancedStage.addATop() {
        addActor(aTop)
        aTop.setSizeScaled(sizeScalerScreen, 928f, 691f)
        aTop.y = topViewportBack.screenHeight - aTop.height
    }

}