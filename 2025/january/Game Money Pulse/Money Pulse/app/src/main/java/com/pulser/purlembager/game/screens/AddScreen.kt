package com.pulser.purlembager.game.screens

import com.pulser.purlembager.game.actors.APanel
import com.pulser.purlembager.game.actors.main.AMainAdd
import com.pulser.purlembager.game.actors.main.AMainMenu
import com.pulser.purlembager.game.utils.*
import com.pulser.purlembager.game.utils.actor.*
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.advanced.AdvancedStage
import kotlinx.coroutines.launch

class AddScreen: AdvancedScreen() {

    companion object {
        var ADD_TYPE = AddType.Desire
    }

    enum class AddType {
        Desire, Income, Expense
    }

    private val aPanel = APanel(this, gdxGame.assetsAll.panel_add)

    private val aMain = AMainAdd(this, aPanel)

    override fun AdvancedStage.addActorsOnStageBack() {
        addPanel()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun hideScreen(block: Block) {
        coroutine?.launch {
            aMain.animHideMain {
                runGDX { block.invoke() }
            }
        }
    }

    // Actors Back------------------------------------------------------------------------

    private fun AdvancedStage.addPanel() {
        addActor(aPanel)

        val panelSizeScaler = SizeScaler(SizeScaler.Axis.X, 1176f)
        panelSizeScaler.calculateScale(viewportUI.screenWidth.toFloat(), 0f)

        aPanel.setBoundsScaled(sizeScalerScreen, 0f, 0f, 1176f, 267f)
    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

}