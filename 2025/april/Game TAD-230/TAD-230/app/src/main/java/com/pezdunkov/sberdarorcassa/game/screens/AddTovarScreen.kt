package com.pezdunkov.sberdarorcassa.game.screens

import com.pezdunkov.sberdarorcassa.game.actors.ATop
import com.pezdunkov.sberdarorcassa.game.actors.main.AMainAddTovar
import com.pezdunkov.sberdarorcassa.game.utils.Block
import com.pezdunkov.sberdarorcassa.game.utils.WIDTH_UI
import com.pezdunkov.sberdarorcassa.game.utils.actor.setSizeScaled
import com.pezdunkov.sberdarorcassa.game.utils.advanced.AdvancedMainScreen
import com.pezdunkov.sberdarorcassa.game.utils.advanced.AdvancedStage

class AddTovarScreen: AdvancedMainScreen() {

    private val aTop = ATop(this)

    override val aMain = AMainAddTovar(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        //setBackBackground(gdxGame.assetsLoader.BACKGROUND_LOADER.region)
        addMain()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    override fun AdvancedStage.addActorsOnStageTopBack() {
        addATop()
    }

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

    // Actors Top Back ------------------------------------------------------------------------

    private fun AdvancedStage.addATop() {
        addActor(aTop)

        aTop.setSizeScaled(sizeScalerScreen, WIDTH_UI, 418f)
        aTop.y = viewportBack.screenHeight - aTop.height
    }

}