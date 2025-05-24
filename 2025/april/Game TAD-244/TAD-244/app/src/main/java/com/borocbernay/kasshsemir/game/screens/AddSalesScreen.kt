package com.borocbernay.kasshsemir.game.screens

import com.borocbernay.kasshsemir.game.actors.AMenu
import com.borocbernay.kasshsemir.game.actors.main.AMainAddSales
import com.borocbernay.kasshsemir.game.utils.Block
import com.borocbernay.kasshsemir.game.utils.WIDTH_UI
import com.borocbernay.kasshsemir.game.utils.actor.setSizeScaled
import com.borocbernay.kasshsemir.game.utils.advanced.AdvancedMainScreen
import com.borocbernay.kasshsemir.game.utils.advanced.AdvancedStage

class AddSalesScreen: AdvancedMainScreen() {

    //private val aTop = ATop(this)
    private val aMenu = AMenu(this)

    override val aMain = AMainAddSales(this, aMenu)

    override fun AdvancedStage.addActorsOnStageUI() {
        //setBackBackground(gdxGame.assetsLoader.BACKGROUND_LOADER.region)
        addMain()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    override fun AdvancedStage.addActorsOnStageTopBack() {
        //addATop()
        addAMenu()
    }

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

    // Actors Top Back ------------------------------------------------------------------------

//    private fun AdvancedStage.addATop() {
//        addActor(aTop)
//
//        aTop.setSizeScaled(sizeScalerScreen, WIDTH_UI, 418f)
//        aTop.y = viewportBack.screenHeight - aTop.height
//    }

    private fun AdvancedStage.addAMenu() {
        addActor(aMenu)

        aMenu.setSizeScaled(sizeScalerScreen, WIDTH_UI, 185f)
    }

}