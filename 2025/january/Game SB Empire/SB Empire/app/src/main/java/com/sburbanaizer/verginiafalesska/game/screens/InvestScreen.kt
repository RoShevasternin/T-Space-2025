package com.sburbanaizer.verginiafalesska.game.screens

import com.sburbanaizer.verginiafalesska.game.actors.APanel
import com.sburbanaizer.verginiafalesska.game.actors.main.AMainInvest
import com.sburbanaizer.verginiafalesska.game.actors.main.AMainMenu
import com.sburbanaizer.verginiafalesska.game.utils.Block
import com.sburbanaizer.verginiafalesska.game.utils.actor.setSizeScaled
import com.sburbanaizer.verginiafalesska.game.utils.advanced.AdvancedScreen
import com.sburbanaizer.verginiafalesska.game.utils.advanced.AdvancedStage

class InvestScreen: AdvancedScreen() {

    private val aPanel = APanel(this)

    private val aMain = AMainInvest(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun AdvancedStage.addActorsOnStageTopBack() {
        addAPanel()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

    // Actors Top Back------------------------------------------------------------------------

    private fun AdvancedStage.addAPanel() {
        addActor(aPanel)
        aPanel.setSizeScaled(sizeScalerScreen, 713f, 226f)
        aPanel.x = viewportUI.leftGutterWidth.toFloat()
        aPanel.y = (viewportBack.screenHeight - aPanel.height)
    }

}