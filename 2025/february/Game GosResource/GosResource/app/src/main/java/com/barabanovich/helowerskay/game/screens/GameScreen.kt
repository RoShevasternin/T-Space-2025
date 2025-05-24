package com.barabanovich.helowerskay.game.screens

import com.barabanovich.helowerskay.game.actors.main.AMainGame
import com.barabanovich.helowerskay.game.utils.*
import com.barabanovich.helowerskay.game.utils.actor.setSizeScaled
import com.barabanovich.helowerskay.game.utils.advanced.AdvancedMainScreen
import com.barabanovich.helowerskay.game.utils.advanced.AdvancedStage
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image

class GameScreen: AdvancedMainScreen() {

    companion object {
        var WIN_COUNT = 0
    }

    override val aMain = AMainGame(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.background_2.region)
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