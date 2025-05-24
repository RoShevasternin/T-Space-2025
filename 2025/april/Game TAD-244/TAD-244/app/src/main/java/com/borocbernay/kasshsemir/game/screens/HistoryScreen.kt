package com.borocbernay.kasshsemir.game.screens

import com.borocbernay.kasshsemir.game.actors.AMenu
import com.borocbernay.kasshsemir.game.actors.ATop
import com.borocbernay.kasshsemir.game.actors.main.AMainHistory
import com.borocbernay.kasshsemir.game.utils.Block
import com.borocbernay.kasshsemir.game.utils.WIDTH_UI
import com.borocbernay.kasshsemir.game.utils.actor.setSizeScaled
import com.borocbernay.kasshsemir.game.utils.advanced.AdvancedMainScreen
import com.borocbernay.kasshsemir.game.utils.advanced.AdvancedStage

class HistoryScreen: AdvancedMainScreen() {

    private val aTop = ATop(this)
    private val aMenu = AMenu(this)

    override val aMain = AMainHistory(this, aMenu)

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    override fun AdvancedStage.addActorsOnStageTopBack() {
        addATop()
        addAMenu()
    }

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

    // Actors Top Back ------------------------------------------------------------------------

    private fun AdvancedStage.addATop() {
        addActor(aTop)

        aTop.setSizeScaled(sizeScalerScreen, WIDTH_UI, 337f)
        aTop.y = viewportBack.screenHeight - aTop.height
    }

    private fun AdvancedStage.addAMenu() {
        addActor(aMenu)

        aMenu.setSizeScaled(sizeScalerScreen, WIDTH_UI, 185f)
    }
}