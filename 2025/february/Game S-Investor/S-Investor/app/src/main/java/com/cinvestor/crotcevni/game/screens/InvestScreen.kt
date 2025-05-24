package com.cinvestor.crotcevni.game.screens

import com.cinvestor.crotcevni.game.actors.ATop
import com.cinvestor.crotcevni.game.actors.main.AMainMenu
import com.cinvestor.crotcevni.game.utils.Acts
import com.cinvestor.crotcevni.game.utils.Block
import com.cinvestor.crotcevni.game.utils.GameColor
import com.cinvestor.crotcevni.game.utils.TIME_ANIM_SCREEN
import com.cinvestor.crotcevni.game.utils.actor.setSizeScaled
import com.cinvestor.crotcevni.game.utils.advanced.AdvancedMainScreen
import com.cinvestor.crotcevni.game.utils.advanced.AdvancedStage
import com.cinvestor.crotcevni.game.utils.gdxGame
import com.badlogic.gdx.math.Interpolation
import com.cinvestor.crotcevni.game.actors.main.AMainImprove
import com.cinvestor.crotcevni.game.actors.main.AMainInvest

class InvestScreen: AdvancedMainScreen() {

    val aTop = ATop(this)

    override val aMain = AMainInvest(this)

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
        aTop.setSizeScaled(sizeScalerScreen, 933f, 296f)
        aTop.y = topViewportBack.screenHeight - aTop.height
    }

}