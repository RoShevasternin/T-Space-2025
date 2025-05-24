package com.baleru.gamanecpidec.game.screens

import com.baleru.gamanecpidec.game.actors.AMenu
import com.baleru.gamanecpidec.game.actors.ATop
import com.baleru.gamanecpidec.game.actors.ATopHistory
import com.baleru.gamanecpidec.game.actors.main.AMainHistory
import com.baleru.gamanecpidec.game.actors.main.AMainMenu
import com.baleru.gamanecpidec.game.utils.Block
import com.baleru.gamanecpidec.game.utils.WIDTH_UI
import com.baleru.gamanecpidec.game.utils.actor.setSizeScaled
import com.baleru.gamanecpidec.game.utils.advanced.AdvancedMainScreen
import com.baleru.gamanecpidec.game.utils.advanced.AdvancedStage
import com.baleru.gamanecpidec.game.utils.gdxGame
import com.baleru.gamanecpidec.game.utils.region

class HistoryScreen: AdvancedMainScreen() {

    private val aTop  = ATopHistory(this)
    private val aMenu = AMenu(this)

    override val aMain = AMainHistory(this, aMenu)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsLoader.BACKGROUND_LOADER.region)
        addMain()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    override fun AdvancedStage.addActorsOnStageTopBack() {
        addAMenu()
    }

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

    // Actors Top Back ------------------------------------------------------------------------

    private fun AdvancedStage.addAMenu() {
        addActor(aMenu)

        aMenu.setSizeScaled(sizeScalerScreen, WIDTH_UI, 178f)
    }
}