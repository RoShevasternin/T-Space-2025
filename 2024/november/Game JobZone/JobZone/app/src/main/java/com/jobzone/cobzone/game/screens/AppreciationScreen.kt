package com.jobzone.cobzone.game.screens

import com.jobzone.cobzone.game.GDXGame
import com.jobzone.cobzone.game.actors.APanelTop
import com.jobzone.cobzone.game.actors.main.AMainAppreciation
import com.jobzone.cobzone.game.utils.Block
import com.jobzone.cobzone.game.utils.advanced.AdvancedScreen
import com.jobzone.cobzone.game.utils.advanced.AdvancedStage
import com.jobzone.cobzone.game.utils.runGDX
import kotlinx.coroutines.launch

class AppreciationScreen(override val game: GDXGame): AdvancedScreen() {

    private val aPanelTop = APanelTop(this)
    private val aMain     = AMainAppreciation(this)

    override fun hideScreen(block: Block) {
        coroutine?.launch {
            aMain.animHideMain {
                runGDX { block.invoke() }
            }
        }
    }

    override fun AdvancedStage.addActorsOnStageBack() {
        addPanelTop()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    // Actors Back------------------------------------------------------------------------

    private fun AdvancedStage.addPanelTop() {
        addActor(aPanelTop)

        val w = sizeScaler_Ui_Back.scaled(732f)
        val h = sizeScaler_Ui_Back.scaled(201f)
        val x = (viewportBack.worldWidth / 2) - (w / 2)
        val y = (viewportBack.worldHeight - h)
        aPanelTop.setBounds(x, y, w, h)

        aPanelTop.addImgLogo()
        aPanelTop.addBtnBack()
    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

}