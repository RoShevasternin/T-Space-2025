package com.baleru.gamanecpidec.game.screens

import com.baleru.gamanecpidec.game.actors.AMenu
import com.baleru.gamanecpidec.game.actors.ATop
import com.baleru.gamanecpidec.game.actors.main.AMainMenu
import com.baleru.gamanecpidec.game.utils.Block
import com.baleru.gamanecpidec.game.utils.WIDTH_UI
import com.baleru.gamanecpidec.game.utils.actor.setSizeScaled
import com.baleru.gamanecpidec.game.utils.advanced.AdvancedMainScreen
import com.baleru.gamanecpidec.game.utils.advanced.AdvancedStage
import com.baleru.gamanecpidec.game.utils.gdxGame
import com.baleru.gamanecpidec.game.utils.region

class MenuScreen: AdvancedMainScreen() {

    private val aTop  = ATop(this)
    private val aMenu = AMenu(this)

    override val aMain = AMainMenu(this, aMenu, aTop)

    override fun AdvancedStage.addActorsOnStageUI() {
        //setBackBackground(gdxGame.assetsLoader.BACKGROUND_LOADER.region)
        //addAndFillActor(Image(drawerUtil.getTexture(Color.BLACK)))
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

        aTop.setSizeScaled(sizeScalerScreen, WIDTH_UI, 533f)
        aTop.y = viewportBack.screenHeight - aTop.height
    }

    private fun AdvancedStage.addAMenu() {
        addActor(aMenu)

        aMenu.setSizeScaled(sizeScalerScreen, WIDTH_UI, 170f)
    }
}