package com.padrestoranom.easypaydonalds.game.screens

import com.padrestoranom.easypaydonalds.game.actors.AMenu
import com.padrestoranom.easypaydonalds.game.actors.main.AMainAdd
import com.padrestoranom.easypaydonalds.game.actors.main.AMainEdit
import com.padrestoranom.easypaydonalds.game.data.DataItem
import com.padrestoranom.easypaydonalds.game.utils.Block
import com.padrestoranom.easypaydonalds.game.utils.WIDTH_UI
import com.padrestoranom.easypaydonalds.game.utils.actor.setSizeScaled
import com.padrestoranom.easypaydonalds.game.utils.advanced.AdvancedMainScreen
import com.padrestoranom.easypaydonalds.game.utils.advanced.AdvancedStage
import com.padrestoranom.easypaydonalds.game.utils.gdxGame
import com.padrestoranom.easypaydonalds.game.utils.region

class EditScreen: AdvancedMainScreen() {

    companion object {
        var EDIT_ITEM: DataItem? = null
    }

    private val aMenu = AMenu(this)

    override val aMain = AMainEdit(this, aMenu)

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