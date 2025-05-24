package com.finansoviy.gurochek.game.screens

import com.finansoviy.gurochek.game.actors.main.AMainGame
import com.finansoviy.gurochek.game.utils.*
import com.finansoviy.gurochek.game.utils.actor.setSizeScaled
import com.finansoviy.gurochek.game.utils.advanced.AdvancedMainScreen
import com.finansoviy.gurochek.game.utils.advanced.AdvancedStage
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image

class GameScreen: AdvancedMainScreen() {

    companion object {
        var WIN_COUNT = 0
    }

    override val aMain = AMainGame(this)

    override fun AdvancedStage.addActorsOnStageUI() {
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