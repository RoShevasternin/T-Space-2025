package com.ayrym.inperdader.game.screens

import com.ayrym.inperdader.game.actors.ABottom
import com.ayrym.inperdader.game.actors.ATop
import com.ayrym.inperdader.game.actors.main.AMainMenu
import com.ayrym.inperdader.game.utils.Acts
import com.ayrym.inperdader.game.utils.Block
import com.ayrym.inperdader.game.utils.GameColor
import com.ayrym.inperdader.game.utils.TIME_ANIM_SCREEN
import com.ayrym.inperdader.game.utils.actor.setSizeScaled
import com.ayrym.inperdader.game.utils.advanced.AdvancedMainScreen
import com.ayrym.inperdader.game.utils.advanced.AdvancedStage
import com.ayrym.inperdader.game.utils.gdxGame
import com.badlogic.gdx.math.Interpolation

class MenuScreen: AdvancedMainScreen() {

    companion object {
        var CURRENT_QUIZ_INDEX = 0
    }

    private val aTop    = ATop(this)
    private val aBottom = ABottom(this)

    override val aMain = AMainMenu(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        gdxGame.backgroundColor = GameColor.background1
        addMain()
    }

    override fun AdvancedStage.addActorsOnStageTopBack() {
        addATop()
        addABottom()
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
        aTop.setSizeScaled(sizeScalerScreen, 803f, 238f)
        aTop.y = topViewportBack.screenHeight - aTop.height
    }

    private fun AdvancedStage.addABottom() {
        addActor(aBottom)
        aBottom.setSizeScaled(sizeScalerScreen, 803f, 1464f)
        aBottom.y = -aBottom.height

        aBottom.addAction(Acts.moveTo(0f, 0f, TIME_ANIM_SCREEN, Interpolation.sineOut))
    }

}