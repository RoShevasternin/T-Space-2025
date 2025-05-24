package com.ayrym.inperdader.game.screens

import com.ayrym.inperdader.game.actors.main.AMainGame
import com.ayrym.inperdader.game.utils.*
import com.ayrym.inperdader.game.utils.actor.setSizeScaled
import com.ayrym.inperdader.game.utils.advanced.AdvancedMainScreen
import com.ayrym.inperdader.game.utils.advanced.AdvancedStage
import com.badlogic.gdx.scenes.scene2d.ui.Image

class GameScreen: AdvancedMainScreen() {

    private val imgBottom = Image(gdxGame.assetsAll.bottom)

    override val aMain = AMainGame(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        gdxGame.backgroundColor = GameColor.background2
        addMain()
    }

    override fun AdvancedStage.addActorsOnStageBack() {
        addImgBottom()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

    // Actors Top Back------------------------------------------------------------------------

    private fun AdvancedStage.addImgBottom() {
        addActor(imgBottom)
        imgBottom.setSizeScaled(sizeScalerScreen, 803f, 1553f)
    }

}