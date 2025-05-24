package com.cinvestor.crotcevni.game.screens

import com.cinvestor.crotcevni.game.actors.ATop
import com.cinvestor.crotcevni.game.actors.main.AMainMenu
import com.cinvestor.crotcevni.game.actors.progress
import com.cinvestor.crotcevni.game.utils.Block
import com.cinvestor.crotcevni.game.utils.actor.setSizeScaled
import com.cinvestor.crotcevni.game.utils.advanced.AdvancedMainScreen
import com.cinvestor.crotcevni.game.utils.advanced.AdvancedStage
import com.cinvestor.crotcevni.game.utils.gdxGame
import com.cinvestor.crotcevni.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

private var is100Progress = true

class MenuScreen: AdvancedMainScreen() {

    val aTop = ATop(this)

    override val aMain = AMainMenu(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun AdvancedStage.addActorsOnStageTopBack() {
        addATop()

        runGDX {
            if (is100Progress) {
                is100Progress = false
                progress?.progressPercentFlow?.value = 100f

                gdxGame.coroutine.launch {
                    while (isActive) {
                        delay(1_000)

                        if (progress!!.progressPercentFlow.value < 100) {
                            progress?.progressPercentFlow?.value += 2 * gdxGame.ds_Level.flow.value
                        }
                    }
                }
            }
        }
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