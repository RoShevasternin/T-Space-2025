package com.sberigatelny.finexpertaizer.game.screens

import com.sberigatelny.finexpertaizer.game.actors.main.AMainGame
import com.sberigatelny.finexpertaizer.game.utils.*
import com.sberigatelny.finexpertaizer.game.utils.actor.setSizeScaled
import com.sberigatelny.finexpertaizer.game.utils.advanced.AdvancedMainScreen
import com.sberigatelny.finexpertaizer.game.utils.advanced.AdvancedStage
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image

class GameScreen: AdvancedMainScreen() {

    override val aMain = AMainGame(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.back_2.region)
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