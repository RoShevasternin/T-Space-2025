package com.baleru.gamanecpidec.game.screens

import com.baleru.gamanecpidec.game.actors.AMenu
import com.baleru.gamanecpidec.game.actors.main.AMainAdd
import com.baleru.gamanecpidec.game.actors.main.AMainEdit
import com.baleru.gamanecpidec.game.data.DataItem
import com.baleru.gamanecpidec.game.utils.Block
import com.baleru.gamanecpidec.game.utils.WIDTH_UI
import com.baleru.gamanecpidec.game.utils.actor.setSizeScaled
import com.baleru.gamanecpidec.game.utils.advanced.AdvancedMainScreen
import com.baleru.gamanecpidec.game.utils.advanced.AdvancedStage
import com.baleru.gamanecpidec.game.utils.gdxGame
import com.baleru.gamanecpidec.game.utils.region

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