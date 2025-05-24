package com.padrestoranom.easypaydonalds.game.screens

import com.padrestoranom.easypaydonalds.game.actors.AMenu
import com.padrestoranom.easypaydonalds.game.actors.ATop
import com.padrestoranom.easypaydonalds.game.actors.main.AMainMenu
import com.padrestoranom.easypaydonalds.game.utils.Block
import com.padrestoranom.easypaydonalds.game.utils.WIDTH_UI
import com.padrestoranom.easypaydonalds.game.utils.actor.setSizeScaled
import com.padrestoranom.easypaydonalds.game.utils.advanced.AdvancedMainScreen
import com.padrestoranom.easypaydonalds.game.utils.advanced.AdvancedStage
import com.padrestoranom.easypaydonalds.game.utils.gdxGame
import com.padrestoranom.easypaydonalds.game.utils.region

class MenuScreen: AdvancedMainScreen() {

    private val aTop  = ATop(this)
    private val aMenu = AMenu(this)

    override val aMain = AMainMenu(this, aMenu, aTop)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsLoader.BACKGROUND_LOADER.region)
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

        aTop.setSizeScaled(sizeScalerScreen, WIDTH_UI, 489f)
        aTop.y = viewportBack.screenHeight - aTop.height
    }

    private fun AdvancedStage.addAMenu() {
        addActor(aMenu)

        aMenu.setSizeScaled(sizeScalerScreen, WIDTH_UI, 178f)
    }
}